package com.motomecha.app.Global_classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class InsurancePage extends AppCompatActivity {
FloatingActionButton Fbooknw;
    Context context;
    String servetype,vehicle_type;
    EditText Etn1,Etn2,Etn3,Etn4,Eyear;
    CheckBox Cpickup;
    String year_manu,uid,Stn1,Stn2,Stn3,Stn4,expiration_policy,bookig_id,order_date;
    private int mYear, mMonth, mDay;
TextView Edat;
    ImageView imageVie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        setTitle("");
        context=InsurancePage.this;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        servetype = getIntent().getStringExtra("servicetype");
        vehicle_type = getIntent().getStringExtra("vehicletype");
        String text = "<font color=#ff1545>INSURANCE</font> <font color=#ffffff>RENEWAL</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InsurancePage.this,BasicActivity.class);
                startActivity(intent);
            }
        });
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        uid=user.get("uid");
        Etn1=(EditText) findViewById(R.id.tn1);
        Etn2=(EditText) findViewById(R.id.tn2);
        Etn3=(EditText) findViewById(R.id.tn3);
        Etn4=(EditText) findViewById(R.id.tn4);
        Etn1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Etn3.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Eyear=(EditText) findViewById(R.id.yaer_select);
        Cpickup=(CheckBox) findViewById(R.id.allpick_check);
        Fbooknw=(FloatingActionButton) findViewById(R.id.booknow);
        imageVie=(ImageView) findViewById(R.id.imageView);
Edat=(TextView) findViewById(R.id.textView8);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mMonth=mMonth+1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        Edat.setText(mDay+"-"+mMonth+"-"+mYear);
        getimage_insur();
        Etn1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {

                // TODO Auto-generated method stub
                if(Etn1.getText().toString().length()==2)     //size as per your requirement
                {

                    Etn2.requestFocus();
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
        Etn2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(Etn2.getText().toString().length()==2)     //size as per your requirement
                {

                    Etn3.requestFocus();
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
        Etn3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(Etn3.getText().toString().length()==2)     //size as per your requirement
                {

                    Etn4.requestFocus();
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
        Etn4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(Etn4.getText().toString().length()==4)     //size as per your requirement
                {

                    Fbooknw.requestFocus();
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

        Fbooknw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Cpickup.isChecked())
                {
                    expiration_policy="EXPIRED";
                }
                else {
                    expiration_policy="NOT EXPIRED";

                }
                Stn1=Etn1.getText().toString();
                Stn2=Etn2.getText().toString();
                Stn3=Etn3.getText().toString();
                Stn1=Etn1.getText().toString();
                Stn4=Etn4.getText().toString();
                year_manu=Eyear.getText().toString();
                Random r = new Random();
                int ri = r.nextInt((99999 - 12345)+1) + 1234;
                bookig_id= "MM"+ri;
                order_date= Edat.getText().toString();
                if (Stn1.length()==2&&Stn2.length()==2&&Stn3.length()==2&&Stn4.length()==4)
                {
                    new DroidDialog.Builder(context)
                            .icon(R.drawable.msingletone_logo)
                            .title("PLEASE CONFIRM YOUR BOOKING")
                            .content("MOTOMECHA SHALL CHOOSE THE BEST SERVICE PROVIDER FOR YOUR VEHICLE.")
                            .cancelable(true, true)
                            .positiveButton("CONFIRM YOUR BOOKING", new DroidDialog.onPositiveListener() {
                                @Override
                                public void onPositive(Dialog droidDialog) {
                                    droidDialog.dismiss();

                                    platenumber(Stn1,Stn2,Stn3,Stn4,uid,year_manu,expiration_policy,bookig_id,order_date);


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
                    Toast.makeText(getApplicationContext(),"ENTER VALID VEHICLE NUMBER",Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void getimage_insur() {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, GlobalUrlInit.ALLSERVICE_PICK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject user = jObj.getJSONObject("allservicepick");
                    String image_url = user.getString("image_url");
                    Glide.with(context).load(image_url).into(imageVie);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("service_type", "IR");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void platenumber(final String stn1, final String stn2, final String stn3, final String stn4, final String uid, final String year_manu, final String expiration_policy, final String bookig_id, final String order_date) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalUrlInit.INSURANCE_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new DroidDialog.Builder(context)
                        .icon(R.drawable.msingletone_logo)
                        .title("BOOKING CONFIRMED")
                        .content("YOUR BOOKING HAS BEEN CONFIRMED WE WILL CONTACT YOU SHORTLY!")
                        .cancelable(true, true)
                        .positiveButton("Ok", new DroidDialog.onPositiveListener() {
                            @Override
                            public void onPositive(Dialog droidDialog) {
                                droidDialog.dismiss();
                                Intent intent = new Intent(InsurancePage.this, BasicActivity.class);
                                startActivity(intent);
                            }
                        })
                        .typeface("rama.ttf")
                        .animation(AnimUtils.AnimZoomInOut)
                        .color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white),
                                ContextCompat.getColor(context, R.color.colorRed))
                        .divider(true, ContextCompat.getColor(context, R.color.colorAccent))
                        .show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("stn1",stn1);
                params.put("stn2",stn2);
                params.put("stn3",stn3);
                params.put("stn4",stn4);
                params.put("uid",uid);
                params.put("year_manu",year_manu);
                params.put("expiration_policy",expiration_policy);
                params.put("bookig_id",bookig_id);
                params.put("order_date",order_date);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);



    }

}
