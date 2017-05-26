package com.motomecha.app.Global_classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class Profile_page extends AppCompatActivity implements OTPListener {
Context context;
    String uid,name,mob_num,email_id,Saddre,Spincod,Sotpnum;
    EditText Ename,Emobi,Eaddres,Epincode,Eemail,Eotp;
    SQLiteHandler db;
    ImageButton Iupdate_profile;
    TextView Tmy_otp;
    ConnectionDetector c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        setTitle("");
        context=Profile_page.this;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>MY</font> <font color=#ffffff>PROFILE</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile_page.this,BasicActivity.class);
                startActivity(intent);
            }
        });
        c = new ConnectionDetector(Profile_page.this);


            Iupdate_profile=(ImageButton) findViewById(R.id.imageButton5);
         db = new SQLiteHandler(getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        Ename=(EditText) findViewById(R.id.name_text);
        Emobi=(EditText) findViewById(R.id.mobile_text);
        Eemail=(EditText) findViewById(R.id.email_text);
        Epincode=(EditText) findViewById(R.id.pincode_text);
        Eaddres=(EditText) findViewById(R.id.address_text);
        Eotp=(EditText) findViewById(R.id.otp_text);
        Tmy_otp=(TextView) findViewById(R.id.my_otp);
        Eotp.setEnabled(false);
        uid=user.get("uid");
        name=user.get("name");
        mob_num=user.get("pnum");
        email_id=user.get("email");
        Saddre=user.get("kaddre");
        Spincod=user.get("kpincode");
            Ename.setText(name);
            Eaddres.setText(Saddre);
            Eemail.setText(email_id);
            Epincode.setText(Spincod);
            Emobi.setText(mob_num);

        Eotp.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {

                // TODO Auto-generated method stub
                if(Eotp.getText().toString().length()==4 && (Eotp.getText().toString().equals(Tmy_otp.getText().toString())))     //size as per your requirement
                {
                  confirm_update_details(name,email_id,mob_num,Saddre,Spincod,uid);
                }

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        if (c.isConnect()) {
            Iupdate_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = Ename.getText().toString();
                    mob_num = Emobi.getText().toString();
                    email_id = Eemail.getText().toString();
                    Saddre = Eaddres.getText().toString();
                    Spincod = Epincode.getText().toString();
                    if (mob_num.length() < 9) {
                        Toast.makeText(getApplicationContext(), "ENTER VALID MOBILE NUMBER", Toast.LENGTH_SHORT).show();
                    } else if (Spincod.length() < 5) {
                        Toast.makeText(getApplicationContext(), "ENTER VALID PINCODE", Toast.LENGTH_SHORT).show();
                    } else {
                        new DroidDialog.Builder(context)
                                .icon(R.drawable.msingletone_logo)
                                .title("CONFIRM MOBILE NUMBER")
                                .content("YOUR INFORMATION WILL BE UPDATED AFTER OTP VERIFICATION")
                                .cancelable(true, true)
                                .positiveButton("OK", new DroidDialog.onPositiveListener() {
                                    @Override
                                    public void onPositive(Dialog droidDialog) {
                                        droidDialog.dismiss();
                                        getmyotp(uid, mob_num);
                                    }
                                })
                                .typeface("rama.ttf")
                                .animation(AnimUtils.AnimZoomInOut)
                                .color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white),
                                        ContextCompat.getColor(context, R.color.colorRed))
                                .divider(true, ContextCompat.getColor(context, R.color.colorAccent))
                                .show();
                    }


                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(),"PLEASE CHECK YOUR INTERNET CONNECTIVITY",Toast.LENGTH_SHORT).show();

        }
        OtpReader.bind(this, "MMECHA");

    }

    public void confirm_update_details(final String name, final String email_id, final String mob_num, final String saddre, final String spincod, final String uid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalUrlInit.CONFIR_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean exits = jObj.getBoolean("exits");
                    if (exits) {
                        db.updateuser(name,email_id,mob_num,saddre,spincod,uid);
                        new DroidDialog.Builder(context)
                                .icon(R.drawable.msingletone_logo)
                                .title("UPDATION SUCCESSFULL")
                                .content("YOUR DETAILS HAS BEEN UPDATED SUCCESSFULLY. ")
                                .cancelable(true, true)
                                .positiveButton("OK", new DroidDialog.onPositiveListener() {
                                    @Override
                                    public void onPositive(Dialog droidDialog) {
                                        droidDialog.dismiss();
                                       Intent inte=new Intent(Profile_page.this,BasicActivity.class);
                                        startActivity(inte);
                                    }
                                })
                                .typeface("rama.ttf")
                                .animation(AnimUtils.AnimZoomInOut)
                                .color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white),
                                        ContextCompat.getColor(context, R.color.colorRed))
                                .divider(true, ContextCompat.getColor(context, R.color.colorAccent))
                                .show();


                    }
                    else {
                        Toast.makeText(getApplicationContext(),"TRY AGAIN AFTER SOMETIME",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",name);
                params.put("email_id",email_id);
                params.put("mob_num",mob_num);
                params.put("saddre",saddre);
                params.put("spincod",spincod);
                params.put("uid",uid);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);


    }

    public void getmyotp(final String uid, final String mob_num) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalUrlInit.GET_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean exits = jObj.getBoolean("exits");
                    if (exits) {
                        Eotp.setEnabled(true);
                        Eotp.setHint("ENTER YOUR OTP");
                        JSONObject user = jObj.getJSONObject("users");
                        String otp = user.getString("otp");
                        Tmy_otp.setText(otp);
                        Iupdate_profile.setVisibility(View.INVISIBLE);

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"TRY AGAIN AFTER SOMETIME",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"TRY AGAIN AFTER SOMETIME",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("uid",uid);
                params.put("mob_num",mob_num);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);


    }




    @Override
    public void otpReceived(String smsText) {
        String[] parts = smsText.split(":"); // escape .
        String part1 = parts[0];
        String part2 = parts[1];
        String[] parts1=part2.split("\\.");
        Sotpnum=parts1[0];
       if (Sotpnum.equals(Tmy_otp.getText().toString()))
       {
           confirm_update_details(name,email_id,mob_num,Saddre,Spincod,uid);

       }

    }

}
