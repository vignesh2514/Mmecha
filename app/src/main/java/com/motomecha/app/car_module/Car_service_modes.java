package com.motomecha.app.car_module;

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

public class Car_service_modes extends AppCompatActivity {
    String pick_type="car";
    ImageButton Igencar,Iac_repa,Iroad_side,Iwheel,Iinsur,Ioil,Irepai,Itinke,Interir,Idiag,Ibuy,Icar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_service_modes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        Igencar=(ImageButton) findViewById(R.id.general_car);
        Iac_repa=(ImageButton) findViewById(R.id.ac_repair);
        Iroad_side=(ImageButton) findViewById(R.id.road_side);
        Iwheel=(ImageButton) findViewById(R.id.wheel_alignment);
        Iinsur=(ImageButton) findViewById(R.id.insurance_car);
        Ioil =(ImageButton) findViewById(R.id.oil_change_car);
        Irepai=(ImageButton) findViewById(R.id.repair_jobs_car);
        Itinke=(ImageButton) findViewById(R.id.tinkering_painting);
        Interir=(ImageButton) findViewById(R.id.interior_clearing);
        Idiag=(ImageButton) findViewById(R.id.diagnosis_car);
        Ibuy=(ImageButton) findViewById(R.id.buy_sell_car);
        Icar=(ImageButton) findViewById(R.id.car_detailing);
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");

        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>CAR</font> <font color=#ffffff>SERVICE</font>";
        tv.setText(Html.fromHtml(text));

        Igencar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Iac_repa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Iroad_side.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                startActivity(intent);
            }
        });
        Iwheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                startActivity(intent);
            }
        });
        Iinsur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                startActivity(intent);
            }
        });
        Ioil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                startActivity(intent);
            }
        });
        Irepai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Itinke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                intent.putExtra("vehicletype","car");

                startActivity(intent);
            }
        });
        Interir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                intent.putExtra("vehicletype","car");

                startActivity(intent);
            }
        });
        Idiag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                intent.putExtra("vehicletype","car");

                startActivity(intent);
            }
        });
        Ibuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Icar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("brandtype",pick_type);
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });

    }

}
