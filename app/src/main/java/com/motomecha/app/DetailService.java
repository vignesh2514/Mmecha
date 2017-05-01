package com.motomecha.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.HashMap;
import java.util.Map;

public class DetailService extends AppCompatActivity {
FloatingActionButton Fbooknow;
    ImageView main_image;
    TextView Ttitle;
    HtmlTextView Tdescrip;
    Context context;
    String serve_type,vehicletype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        context=this;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>CAR</font> <font color=#ffffff>SERVICE</font>";
        tv.setText(Html.fromHtml(text));
        main_image=(ImageView) findViewById(R.id.imageView);
        Ttitle=(TextView) findViewById(R.id.text_title);
        Ttitle.setTypeface(custom_font);
        Tdescrip=(HtmlTextView) findViewById(R.id.text_description);
        Tdescrip.setTypeface(custom_font);
        serve_type = getIntent().getStringExtra("servicetype");
        vehicletype= getIntent().getStringExtra("vehicletype");
        Fbooknow=(FloatingActionButton) findViewById(R.id.booknow);
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_view);
        Fbooknow.startAnimation(startAnimation);
        loadmyservice(serve_type);
        Fbooknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailService.this,ListVechicle.class);
                intent.putExtra("servicetype",serve_type);
                intent.putExtra("vehicletype",vehicletype);
                startActivity(intent);
            }
        });
}
public  void loadmyservice(final String serve_type)
{
    StringRequest stringRequest =new StringRequest(Request.Method.POST, GlobalUrlInit.ALLSERVICE_PICK, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jObj = new JSONObject(response);
                JSONObject user = jObj.getJSONObject("allservicepick");
                String image_url = user.getString("image_url");
                String title = user.getString("title");
                String content_des =user.getString("content_des");
                Glide.with(context).load(image_url).into(main_image);
                Ttitle.setText(title);
                Tdescrip.setHtml(content_des,
                        new HtmlHttpImageGetter(Tdescrip));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("service_type", serve_type);
            return params;
        }
    };
    AppController.getInstance().addToRequestQueue(stringRequest);
}
}
