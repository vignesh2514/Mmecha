package com.motomecha.app.bike_module;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.motomecha.app.MyVechicle;
import com.motomecha.app.R;

public class PickService extends AppCompatActivity {

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

        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");

        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>BIKE</font> <font color=#ffffff>SERVICE</font>";
        tv.setText(Html.fromHtml(text));

Igs.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(PickService.this,MyVechicle.class);
        
        intent.putExtra("vehicletype","bike");
        startActivity(intent);
    }
});

        Ioil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");
                startActivity(intent);
            }
        });
        Irep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");
                startActivity(intent);
            }
        });
        Iwat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Ityr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Ibrek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Iveh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Iinsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Ibuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Ipet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Ibike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });
        Icus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickService.this,MyVechicle.class);
              
                intent.putExtra("vehicletype","bike");

                startActivity(intent);
            }
        });


    }

}
