package com.motomecha.app.Global_classes;

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
import android.widget.Toast;

import com.motomecha.app.R;

public class VehicleMode extends AppCompatActivity {
ImageButton Iselectbike,Iselectcar;
    String service_type;
    ConnectionDetector c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>SELECT</font> <font color=#ffffff>VEHICLE</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VehicleMode.this,BasicActivity.class);
                startActivity(intent);
            }
        });
        Iselectbike=(ImageButton) findViewById(R.id.imageButton7);
        Iselectcar=(ImageButton) findViewById(R.id.imageButton8);
        service_type = getIntent().getStringExtra("servicetype");
        c = new ConnectionDetector(VehicleMode.this);
        if (c.isConnect()) {

            Iselectbike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VehicleMode.this,ListVechicle.class);
                intent.putExtra("servicetype",service_type);
                intent.putExtra("vehicletype","bike");
                startActivity(intent);
            }
        });
        Iselectcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VehicleMode.this,ListVechicle.class);
                intent.putExtra("servicetype",service_type);
                intent.putExtra("vehicletype","car");

                startActivity(intent);

            }
        });
        }
        else
        {
            Toast.makeText(getApplicationContext(),"PLEASE CHECK YOUR INTERNET CONNECTIVITY",Toast.LENGTH_SHORT).show();

        }
    }

}
