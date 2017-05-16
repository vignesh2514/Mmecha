package com.motomecha.app.bike_module;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.freshdesk.hotline.Hotline;
import com.freshdesk.hotline.HotlineConfig;
import com.freshdesk.hotline.HotlineUser;
import com.motomecha.app.Global_classes.BasicActivity;
import com.motomecha.app.Global_classes.GlobalWebPage;
import com.motomecha.app.Global_classes.InsurancePage;
import com.motomecha.app.Global_classes.MyVechicle;
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;

import java.util.HashMap;

public class PickService extends AppCompatActivity {
String name,email,mobile_number;
    ImageButton Igs,Ioil,Irep,Iwat,Ityr,Ibrek,Iveh,Iinsu,Ibuy,Ipet,Ibike,Icus;
    TextView Tservicetype,Tbrandtype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        Tservicetype=(TextView) findViewById(R.id.servicetype);
        Igs=(ImageButton)  findViewById(R.id.general_service);
        Ioil=(ImageButton) findViewById(R.id.oil_change);
        Irep=(ImageButton) findViewById(R.id.repair_jobs);
        Iwat=(ImageButton) findViewById(R.id.water_wash);
        Ityr=(ImageButton) findViewById(R.id.tyre_puncture);
        Ibrek=(ImageButton) findViewById(R.id.breakdown_assit);
        Iveh=(ImageButton) findViewById(R.id.vehicle_diag);
        Iinsu=(ImageButton) findViewById(R.id.insurance_ren);
        Ibuy=(ImageButton) findViewById(R.id.buy_sell_mod);
        Ipet=(ImageButton) findViewById(R.id.nearest_petrol);
        Ibike=(ImageButton) findViewById(R.id.bike_stickern);
        Icus=(ImageButton) findViewById(R.id.cuatomer_supp);
        Tbrandtype=(TextView) findViewById(R.id.brandtype);
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        name=user.get("name");
        email=user.get("email");
mobile_number=user.get("pnum");
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
        String text = "<font color=#ff1545>BIKE</font> <font color=#ffffff>SERVICE</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,BasicActivity.class);
                startActivity(intent);
            }
        });
        HotlineConfig hotlineConfig = new HotlineConfig("a77f9cdb-24d2-441a-a950-c6a2ee3a97da", "79e50529-54c3-4bd3-a6ff-add1bcfafbb8");
        hotlineConfig.setVoiceMessagingEnabled(true);
        hotlineConfig.setCameraCaptureEnabled(true);
        hotlineConfig.setPictureMessagingEnabled(true);
        Hotline.getInstance(getApplicationContext()).init(hotlineConfig);

        //Update user information
        HotlineUser user1 = Hotline.getInstance(getApplicationContext()).getUser();
        user1.setName(name).setEmail(email).setPhone("+91", mobile_number);;
        Hotline.getInstance(getApplicationContext()).updateUser(user1);
Igs.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(PickService.this,MyVechicle.class);
        intent.putExtra("servicetype","GS");
        intent.putExtra("vehicletype","bike");
        startActivity(intent);
    }
});

        Ioil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
                intent.putExtra("servicetype","OC");
                intent.putExtra("vehicletype","bike");
                startActivity(intent);
            }
        });
        Irep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
                intent.putExtra("servicetype","RJ");
                intent.putExtra("vehicletype","bike");
                startActivity(intent);
            }
        });
        Iwat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
                intent.putExtra("servicetype","WW");
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Ityr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
                intent.putExtra("servicetype","TP");
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Ibrek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
                intent.putExtra("servicetype","BA");
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Iveh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
                intent.putExtra("servicetype","VD");
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Iinsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,InsurancePage.class);
                intent.putExtra("servicetype","IR");
//                intent.putExtra("vehicletype","bike");
                startActivity(intent);
            }
        });
        Ibuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,GlobalWebPage.class);
                intent.putExtra("title1","BUY SELL");
                intent.putExtra("wburl"," http://motomecha.com/");
                startActivity(intent);
            }
        });
        Ipet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Ibike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
                intent.putExtra("servicetype","BSG");
                intent.putExtra("vehicletype","bike");
                startActivity(intent);
            }
        });
        Icus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hotline.showConversations(PickService.this);
            }
        });


    }

}
