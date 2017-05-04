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

import com.motomecha.app.Global_classes.DetailService;
import com.motomecha.app.Global_classes.GlobalWebPage;
import com.motomecha.app.Global_classes.MyVechicle;
import com.motomecha.app.R;

public class Car_service_modes extends AppCompatActivity {
   
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>CAR</font> <font color=#ffffff>SERVICE</font>";
        tv.setText(Html.fromHtml(text));

        Igencar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("servicetype","GS");
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Iac_repa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("servicetype","AR");
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Iroad_side.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,DetailService.class);
                intent.putExtra("servicetype","RSA");
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Iwheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,DetailService.class);
                intent.putExtra("servicetype","WA");
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Iinsur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,DetailService.class);
                intent.putExtra("servicetype","IN");
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Ioil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,DetailService.class);
                intent.putExtra("servicetype","OC");
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Irepai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,MyVechicle.class);
                intent.putExtra("servicetype","RJ");
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Itinke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,DetailService.class);
                intent.putExtra("servicetype","TP");
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Interir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,DetailService.class);
                intent.putExtra("servicetype","IC");
                intent.putExtra("vehicletype","car");

                startActivity(intent);
            }
        });
        Idiag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,DetailService.class);
                intent.putExtra("servicetype","DI");
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });
        Ibuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Car_service_modes.this,GlobalWebPage.class);
                intent.putExtra("title1","USED CARS");
                intent.putExtra("wburl"," http://motomecha.com/");
                startActivity(intent);
            }
        });
        Icar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Car_service_modes.this,DetailService.class);
                intent.putExtra("servicetype","CD");
                intent.putExtra("vehicletype","car");
                startActivity(intent);
            }
        });

    }

}
