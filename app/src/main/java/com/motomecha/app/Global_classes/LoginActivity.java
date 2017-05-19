package com.motomecha.app.Global_classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.motomecha.app.dbhandler.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
EditText Emobile_number;
    ImageButton Ilogin_continue;
    String Smobilenumber;
    Context context=LoginActivity.this;
    private SessionManager session;
    private SQLiteHandler db;
    ConnectionDetector c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
Emobile_number=(EditText) findViewById(R.id.mobile_text);
        TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        Emobile_number.setText(mPhoneNumber);
        Ilogin_continue=(ImageButton) findViewById(R.id.login_continue);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());


        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, BasicActivity.class);
            startActivity(intent);
        }
        CheckEnableGPS();
        c=new ConnectionDetector(LoginActivity.this);
        if (c.isConnect())
        {
            Ilogin_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Smobilenumber=Emobile_number.getText().toString();

                    if (Smobilenumber.length()>9)
                    {
                        new DroidDialog.Builder(context)
                                .icon(R.drawable.msingletone_logo)
                                .title("MOBILE NUMBER VERIFICATION")
                                .content("IS THIS OK,OR WOULD YOU LIKE TO EDIT THE NUMBER?\n\n YOUR MOBILE NUMBER IS +91-"+Smobilenumber)
                                .cancelable(true, true)
                                .negativeButton("EDIT", new DroidDialog.onNegativeListener() {
                                    @Override
                                    public void onNegative(Dialog dialog) {
                                        dialog.dismiss();
                                    }
                                })
                                .positiveButton("OK", new DroidDialog.onPositiveListener() {
                                    @Override
                                    public void onPositive(Dialog droidDialog) {
                                        droidDialog.dismiss();
                                        logincheck(Smobilenumber);
                                    }
                                }).typeface("rama.ttf").animation(AnimUtils.AnimZoomInOut).color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.colorRed)).divider(true, ContextCompat.getColor(context, R.color.colorAccent)).show();

                    }

                    else
                    {
                        Toast.makeText(LoginActivity.this, "ENTER  VALID MOBILE NUMBER", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(),"PLEASE CHECK YOUR INTERNET CONNECTION",Toast.LENGTH_SHORT).show();
        }


    }
    public  void logincheck(final String smobilenumber)
    {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, GlobalUrlInit.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean exits = jObj.getBoolean("exits");
                    if (!exits) {
                        JSONObject user = jObj.getJSONObject("users");
                        String mobile_number = user.getString("mobile_number");
                        String otp = user.getString("otp");
                        String getpass =user.getString("getpass");
                        Toast.makeText(LoginActivity.this, "OTP HAS BEEN SENT TO REGISTERED MOBILE NUMBER.!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                        intent.putExtra("mobile_number",mobile_number);
                        intent.putExtra("otp",otp);
                        intent.putExtra("getpass",getpass);
                        startActivity(intent);

                    }
                    else {
                        JSONObject user = jObj.getJSONObject("users");
                        String mobile_number = user.getString("mobile_number");
                        String otp = user.getString("otp");
                        String getpass =user.getString("getpass");
                        String name =user.getString("name");
                        String email   =user.getString("email");
                        String address =user.getString("address");
                        String uid =user.getString("uid");;
                        String kpincode = user.getString("pincode");
                        String klat = user.getString("slat");
                        String klng = user.getString("slng");
                        Toast.makeText(LoginActivity.this, "OTP HAS BEEN SENT TO REGISTERED MOBILE NUMBER.!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                        intent.putExtra("mobile_number",mobile_number);
                        intent.putExtra("otp",otp);
                        intent.putExtra("getpass",getpass);
                        intent.putExtra("name",name);
                        intent.putExtra("email",email);
                        intent.putExtra("address",address);
                        intent.putExtra("uid",uid);
                        intent.putExtra("pincode",kpincode);
                        intent.putExtra("slat",klat);
                        intent.putExtra("slng",klng);
                        startActivity(intent);


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
                Map<String,String> params=new HashMap<String, String>();
                params.put("mobile_number",smobilenumber);
                return params;

            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest,"CHECKING");
    }
    private void CheckEnableGPS() {
        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.equals("")) {

        } else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
