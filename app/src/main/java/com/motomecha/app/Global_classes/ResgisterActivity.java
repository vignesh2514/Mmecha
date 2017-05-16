package com.motomecha.app.Global_classes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;
import com.motomecha.app.dbhandler.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class ResgisterActivity extends AppCompatActivity  {
    String Sname, Semail, Saddress, mobile_number, otp, Spincode,Slat,Slng;
    EditText Ename, Eemail, Eaddress, Epincode;
    TextView Tlatittude,Tlongitude;
    ImageButton Iproceed;
    private SessionManager session;
    private SQLiteHandler db;
    private TrackGPS gps;
    double lat;
    double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);
        Ename = (EditText) findViewById(R.id.name);
        Eemail = (EditText) findViewById(R.id.email);
        Eaddress = (EditText) findViewById(R.id.address);
        Epincode = (EditText) findViewById(R.id.pincode_regs);
        Iproceed = (ImageButton) findViewById(R.id.login_continue);
        mobile_number = getIntent().getStringExtra("mobile_number");
        Tlatittude = (TextView) findViewById(R.id.latitude_user);
        Tlongitude = (TextView) findViewById(R.id.longitude_user);
        otp = getIntent().getStringExtra("otp");

        String email = null;
        Pattern gmailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (gmailPattern.matcher(account.name).matches()) {
                email = account.name;

            }
        }
        Eemail.setText(email);
        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        gps = new TrackGPS(ResgisterActivity.this);


        try {
            if(gps.canGetLocation()){

                Double lat =gps .getLatitude();
                Double lng =  gps.getLongitude();


                List<Address> addresses = null;
                try {
                    Geocoder geo = new Geocoder(ResgisterActivity.this.getApplicationContext(), Locale.getDefault());
                    addresses = geo.getFromLocation(lat, lng, 1);
                    if (addresses.isEmpty()) {
                        Tlatittude.setText("12.8711020");
                        Tlongitude.setText("80.2226490");
                    }
                    else {
                        if (addresses.size() > 0) {
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            String knownName = addresses.get(0).getFeatureName();
                            Eaddress.setText(address + "," + city + "," + state + "," + country);
                            Epincode.setText(postalCode);
                            Tlatittude.setText(String.valueOf(lat));
                            Tlongitude.setText(String.valueOf(lng));
    //                         Eaddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                            //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            else
            {
                gps.showSettingsAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Iproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sname = Ename.getText().toString();
                Semail = Eemail.getText().toString();
                Saddress = Eaddress.getText().toString();
                Spincode = Epincode.getText().toString();
                Slat = Tlatittude.getText().toString();
                Slng = Tlongitude.getText().toString();
                if (Sname.isEmpty() || Semail.isEmpty() || Saddress.isEmpty() || Spincode.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "PLEASE FILL ALL FIELDS", Toast.LENGTH_SHORT).show();
                }
                else if(Spincode.length()<5)
                {
                    Toast.makeText(getApplicationContext(), "PLEASE ENTER CORRECT PINCODE", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerme(Sname, Semail, Saddress, mobile_number, otp, Spincode, Slat, Slng);
                }
            }
        });
    }

    public void registerme(final String sname, final String semail, final String saddress, final String mobile_number, final String otp, final String spincode,final String slat,final String slng) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalUrlInit.SIGN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean success = jObj.getBoolean("exits");
                    if (success) {
                        session.setLogin(true);
                        JSONObject user = jObj.getJSONObject("users");
                        String mobile_number = user.getString("mobile_number");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String uid = user.getString("uid");
                        String kaddre = user.getString("address");
                        String kpincode = user.getString("pincode");
                        String klat = user.getString("slat");
                        String klng = user.getString("slng");
                        db.addUser(name, email, uid, mobile_number,kaddre,kpincode,klat,klng);
                        Intent intent = new Intent(ResgisterActivity.this, BasicActivity.class);
                        startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile_number", mobile_number);
                params.put("otp", otp);
                params.put("name", sname);
                params.put("email", semail);
                params.put("address", saddress);
                params.put("pincode", spincode);
                params.put("slat", slat);
                params.put("slng", slng);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }





}
