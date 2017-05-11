package com.motomecha.app.car_module;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.freshdesk.hotline.Hotline;
import com.freshdesk.hotline.HotlineConfig;
import com.freshdesk.hotline.HotlineUser;
import com.motomecha.app.R;
import com.motomecha.app.bike_module.ConfirmBooking;
import com.motomecha.app.dbhandler.SQLiteHandler;

import java.util.HashMap;

public class CarServiceProvidersDetail extends AppCompatActivity {
String Saddress,Sdisplay_name,Sid,Sprice,Slikes,Sservice_description,Smerchant_image,name,email,mobile_number,kaddress,vehicleno;
    TextView Taddress,Tdisplay_name,Tprice,Tlikes;
    ImageView Imerchant_image;
    ImageButton Ibooknw,Icallnw,Ichatnw;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_service_providers_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        context=CarServiceProvidersDetail.this;
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        assert tv != null;
        tv.setTypeface(custom_font);
        setTitle("");
        String text = "<font color=#ff1545>SERVICE PROVIDER</font> <font color=#ffffff>INFO</font>";
        tv.setText(Html.fromHtml(text));
        Taddress=(TextView) findViewById(R.id.car_service_addres);
        Tlikes=(TextView) findViewById(R.id.likes_db);
        Tprice=(TextView) findViewById(R.id.price_car_ser);
        Tdisplay_name=(TextView) findViewById(R.id.car_shop_name);
        Imerchant_image=(ImageView) findViewById(R.id.car_sp_image);
        Ibooknw=(ImageButton) findViewById(R.id.booknow_map);
        Icallnw=(ImageButton) findViewById(R.id.call_now_map);
        Ichatnw=(ImageButton) findViewById(R.id.chat_map);
        Saddress = getIntent().getStringExtra("address");
        Sdisplay_name = getIntent().getStringExtra("display_name");
        Sid = getIntent().getStringExtra("id");
        Sprice = getIntent().getStringExtra("price");
        Slikes = getIntent().getStringExtra("likes");
        Smerchant_image = getIntent().getStringExtra("merchant_image");
        Sservice_description = getIntent().getStringExtra("service_description");
        vehicleno = getIntent().getStringExtra("vehicleno");
        Glide.with(context).load(Smerchant_image).into(Imerchant_image);
        Tdisplay_name.setText(Sdisplay_name);
        Taddress.setText(Saddress);
        Tlikes.setText(Slikes);
        Tprice.setText("\u20B9"+Sprice);
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        name=user.get("name");
        email=user.get("email");
        mobile_number=user.get("pnum");
        kaddress=user.get("kaddre");
        HotlineConfig hotlineConfig = new HotlineConfig("a77f9cdb-24d2-441a-a950-c6a2ee3a97da", "79e50529-54c3-4bd3-a6ff-add1bcfafbb8");
        hotlineConfig.setVoiceMessagingEnabled(true);
        hotlineConfig.setCameraCaptureEnabled(true);
        hotlineConfig.setPictureMessagingEnabled(true);
        Hotline.getInstance(getApplicationContext()).init(hotlineConfig);

        //Update user information
        HotlineUser user1 = Hotline.getInstance(getApplicationContext()).getUser();
        user1.setName(name).setEmail(email).setPhone("+91", mobile_number);;
        Hotline.getInstance(getApplicationContext()).updateUser(user1);

        Ibooknw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DroidDialog.Builder(context)
                        .icon(R.drawable.msingletone_logo)
                        .title("GENERAL SERVICE")
                        .content("Our regular service offering intends to do away with the need of visiting a garage for your general maintenance needs. We perform maintenance tasks like oil change, air filter cleaning, spark plug cleaning, brake cleaning, chain adjustment, and valve clearance check at your doorstep. Keep your bike fit and road ready!\nWe would love to, but it’s not possible for us. For tasks like engine repair (engine making noise?), mag wheel repair, shocker repair, body repair, clutch plate replacement etc., we suggest you take the bike to an authorized service center. They have the right set of tools needed for the job and the quality won’t be compromised!")
                        .cancelable(true, true)
                        .positiveButton("BOOK NOW", new DroidDialog.onPositiveListener() {
                            @Override
                            public void onPositive(Dialog droidDialog) {
                                droidDialog.dismiss();
                                Intent intent = new Intent(CarServiceProvidersDetail.this, ConfirmBooking.class);
                                intent.putExtra("kaddress",kaddress);
                                intent.putExtra("vechicletype","CAR");
                                intent.putExtra("vehicleno",vehicleno);
                                startActivity(intent);
                            }
                        }).typeface("rama.ttf").animation(AnimUtils.AnimZoomInOut).color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.colorRed)).divider(true, ContextCompat.getColor(context, R.color.colorAccent)).show();
            }
        });
        Icallnw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone ="+919840297628";
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", phone, null));
                startActivity(phoneIntent);
            }
        });
        Ichatnw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

}
