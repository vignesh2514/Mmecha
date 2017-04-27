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

import com.motomecha.app.BasicActivity;
import com.motomecha.app.R;

public class LastPage extends AppCompatActivity {
ImageButton Ibook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        setTitle("");

        String text = "<font color=#ff1545>BOOKING</font> <font color=#ffffff>CONFIRM</font>";
        tv.setText(Html.fromHtml(text));
        Ibook=(ImageButton) findViewById(R.id.imageButton6);
        Ibook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LastPage.this, BasicActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
