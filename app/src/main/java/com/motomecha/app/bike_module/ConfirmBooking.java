package com.motomecha.app.bike_module;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.motomecha.app.Global_classes.AppController;
import com.motomecha.app.Global_classes.BasicActivity;
import com.motomecha.app.Global_classes.GlobalUrlInit;
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ConfirmBooking extends AppCompatActivity implements View.OnClickListener{
    ImageButton dateselect;
    private int mYear, mMonth, mDay;
    TextView Edat,Tvehicleno,total_booking;
    EditText Eotherreq,Eaddress;
    LinearLayout Llinerala;
    String kaddress,vehicleno,bookig_id,price,service_description,uid,servetype,merchant_id,pick_u;
    HtmlTextView Tdescrip;
    CheckBox Cpickup;
    CardView cardView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        String text = "<font color=#ff1545>CONFIRM</font> <font color=#ffffff>BOOKING</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConfirmBooking.this,BasicActivity.class);
                startActivity(intent);
            }
        });
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        uid=user.get("uid");
        context=this;
            kaddress = getIntent().getStringExtra("kaddress");
            vehicleno = getIntent().getStringExtra("vehicleno");
        servetype = getIntent().getStringExtra("servetype");
        merchant_id = getIntent().getStringExtra("merchant_id");
        service_description = getIntent().getStringExtra("service_description");
        price = getIntent().getStringExtra("price");
        Eotherreq=(EditText) findViewById(R.id.textView6);
        Tdescrip=(HtmlTextView) findViewById(R.id.textView5);
        cardView=(CardView) findViewById(R.id.linearLayout2);
        Cpickup=(CheckBox) findViewById(R.id.allpick_check);
        Tdescrip.setHtml(service_description, new HtmlHttpImageGetter(Tdescrip));
        total_booking=(TextView) findViewById(R.id.total_booking_price);
        Eaddress=(EditText) findViewById(R.id.address_booking);
        Llinerala=(LinearLayout) findViewById(R.id.bot_lin);
        Tvehicleno=(TextView) findViewById(R.id.textView4);
        Tvehicleno.setText(vehicleno);
        total_booking.setText("ESTIMATE - \u20B9" +price +"*");
        Eaddress.setText(kaddress);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
setTitle("");
        mMonth=mMonth+1;
        Edat=(TextView) findViewById(R.id.textView8);
        Edat.setText(mDay+"-"+mMonth+"-"+mYear);
         dateselect=(ImageButton) findViewById(R.id.imageButton4);
        dateselect.setOnClickListener(this);

        if (merchant_id==null)
        {
            merchant_id="bike";
        }

        Llinerala.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.Yellowlight));
        if(Cpickup.isChecked())
        {
            pick_u="YES";
        }
            else {
            pick_u="NO";

        }
        Random r = new Random();
        int ri = r.nextInt((99999 - 12345)+1) + 1234;
        bookig_id= "MM"+ri;
        String sother_req=Eotherreq.getText().toString();
        String faddress=Eaddress.getText().toString();
        String current_date=mDay+"-"+mMonth+"-"+mYear;
        String scheduled_date=Edat.getText().toString();
        mybookingconfirmed(uid,price,faddress,vehicleno,bookig_id,current_date,scheduled_date,servetype,sother_req,merchant_id,pick_u);

    }
});
    }

    public void mybookingconfirmed(final String uid, final String price, final String faddress, final String vehicleno, final String bookig_id, final String current_date, final String scheduled_date, final String servetype, final String sother_req, final String merchant_id, final String pick_u)
    {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, GlobalUrlInit.FINAL_CONFIRM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean exits = jObj.getBoolean("exits");
if (exits)
{
    JSONObject user = jObj.getJSONObject("users");
    String booking_id = user.getString("booking_id");
    String scheduled_dat = user.getString("scheduled_date");
    Intent intent=new Intent(ConfirmBooking.this, LastPage.class);
    intent.putExtra("booking_id",booking_id);
    intent.putExtra("servicedate",scheduled_dat);
    startActivity(intent);
}
else
{
    Toast.makeText(getApplicationContext(),"BOOKING HAS BEEN CANCELLED.TRY AGAIN AFTER SOMETIME!",Toast.LENGTH_SHORT).show();
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
                params.put("uid",uid);
                params.put("price",price);
                params.put("faddress",faddress);
                params.put("vehicleno",vehicleno);
                params.put("booking_id",bookig_id);
                params.put("current_date",current_date);
                params.put("scheduled_date",scheduled_date);
                params.put("servetype",servetype);
                params.put("other_requirements",sother_req);
                params.put("merchant_id",merchant_id);
                params.put("pick_up",pick_u);
                return params;

            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest,"CHECKING");


    }


    @Override
    public void onClick(View v) {

             DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Edat.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
