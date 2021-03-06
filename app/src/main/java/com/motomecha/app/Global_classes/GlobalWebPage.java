package com.motomecha.app.Global_classes;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.motomecha.app.R;

public class GlobalWebPage extends AppCompatActivity {
WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_web_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GlobalWebPage.this,BasicActivity.class);
                startActivity(intent);
            }
        });
        webView=(WebView) findViewById(R.id.global_webview);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        assert tv != null;
        tv.setTypeface(custom_font);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title1");
        String title1 = null;
        String title2 = "Page";
        try {
            String[] parts = title.split("\\s+"); // escape .
            title1 = parts[0];
            title2 = parts[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        String wburl = intent.getStringExtra("wburl");
        String text = "<font color=#ff1545>"+title1+"</font> <font color=#ffffff>"+title2+"</font>";
        tv.setText(Html.fromHtml(text));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
         webView.loadUrl(wburl);

    }

}
