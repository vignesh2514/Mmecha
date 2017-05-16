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
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.motomecha.app.R;

public class InsurancePage extends AppCompatActivity {
FloatingActionButton Fbooknw;
    Context context;
    String servetype,vehicle_type;
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
        Fbooknw=(FloatingActionButton) findViewById(R.id.booknow);

        Fbooknw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DroidDialog.Builder(context)
                        .icon(R.drawable.msingletone_logo)
                        .title("PLEASE CONFIRM YOUR BOOKING")
                        .content("MOTOMECHA SHALL CHOOSE THE BEST SERVICE PROVIDER FOR YOUR VEHICLE.")
                        .cancelable(true, true)
                        .positiveButton("CONFIRM YOUR BOOKING", new DroidDialog.onPositiveListener() {
                            @Override
                            public void onPositive(Dialog droidDialog) {
                                droidDialog.dismiss();
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
                        })


                        .typeface("rama.ttf")
                        .animation(AnimUtils.AnimZoomInOut)
                        .color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white),
                                ContextCompat.getColor(context, R.color.colorRed))
                        .divider(true, ContextCompat.getColor(context, R.color.colorAccent))
                        .show();

            }
        });
    }

}
