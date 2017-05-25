package com.motomecha.app.Global_classes;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;

import java.util.HashMap;
import java.util.Map;

public class New_Plate_registration extends AppCompatActivity {
    ImageButton Bsubmit;
    EditText Etn1,Etn2,Etn3,Etn4;
    String vehicle_no,uid,Stn1,Stn2,Stn3,Stn4,bike_model;
    TextView Tbikeview;
    LinearLayout Llightbulb;
    ConnectionDetector c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate__regiestration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String text = "<font color=#ff1545>NUMBER</font> <font color=#ffffff>PLATE</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(New_Plate_registration.this,BasicActivity.class);
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
        Llightbulb=(LinearLayout) findViewById(R.id.liner_check);
        Tbikeview=(TextView) findViewById(R.id.model_name);
        Bsubmit=(ImageButton) findViewById(R.id.submit_register);
        Intent intent = getIntent();
        vehicle_no = intent.getStringExtra("vehicle_no");
        String[] parts = vehicle_no.split("-");
        String part1 = parts[0];
        String part2 = parts[1];
        String part3 = parts[2];
        String part4 = parts[3];
        Etn1.setText(part1);
        Etn2.setText(part2);
        Etn3.setText(part3);
        Etn4.setText(part4);
        bike_model = intent.getStringExtra("bike_model");
        Tbikeview.setText(bike_model);
        c = new ConnectionDetector(New_Plate_registration.this);
        if (c.isConnect()) {

            Bsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Stn1 = Etn1.getText().toString();
                    Stn2 = Etn2.getText().toString();
                    Stn3 = Etn3.getText().toString();
                    Stn1 = Etn1.getText().toString();
                    Stn4 = Etn4.getText().toString();

                    if (Stn1.length() == 2 && Stn2.length() == 2 && Stn3.length() <= 2 && Stn4.length() == 4) {
                        platenumber(Stn1, Stn2, Stn3, Stn4, uid, vehicle_no);
                    } else {
                        Toast.makeText(getApplicationContext(), "ENTER VALID VEHICLE NUMBER", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(),"PLEASE CHECK YOUR INTERNET CONNECTIVITY",Toast.LENGTH_SHORT).show();

        }
        Etn1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {

                // TODO Auto-generated method stub
                if(Etn1.getText().toString().length()==2)     //size as per your requirement
                {
                    Llightbulb.setBackground(getResources().getDrawable( R.drawable.plate_one));
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
                    Llightbulb.setBackground(getResources().getDrawable( R.drawable.plate_two));
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
                    Llightbulb.setBackground(getResources().getDrawable( R.drawable.plate_three));
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
                    Llightbulb.setBackground(getResources().getDrawable( R.drawable.plate_four));
                    Bsubmit .requestFocus();
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
    }
    public void platenumber(final String stn1, final String stn2, final String stn3, final String stn4, final String Uid, final String original_number)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalUrlInit.UPDATE_PLATE_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"YOUR VEHICLE HAS BEEN UPDATED",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(New_Plate_registration.this,BasicActivity.class);
                startActivity(intent);
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
                params.put("uid",Uid);
                params.put("original_number",original_number);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
