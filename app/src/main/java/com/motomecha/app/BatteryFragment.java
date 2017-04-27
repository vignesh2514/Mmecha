package com.motomecha.app;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BatteryFragment extends Fragment {

    ImageView main_image;
    TextView Ttitle;
    HtmlTextView Tdescrip;
FloatingActionButton Fbooknow;
    public static BatteryFragment newInstance() {
        BatteryFragment fragment = new BatteryFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Context c = getActivity().getApplicationContext();
        View v= inflater.inflate(R.layout.fragment_tyre, container, false);
        main_image=(ImageView) v.findViewById(R.id.imageView);
        Ttitle=(TextView) v.findViewById(R.id.text_title);
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"fonts/rama.ttf");
        Ttitle.setTypeface(face);
        Tdescrip=(HtmlTextView) v.findViewById(R.id.text_description);
        Tdescrip.setTypeface(face);
        Fbooknow=(FloatingActionButton) v.findViewById(R.id.booknow);
        Animation startAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.blink_view);
        Fbooknow.startAnimation(startAnimation);
        StringRequest stringRequest =new StringRequest(Request.Method.POST, GlobalUrlInit.BATTERY_SERVICE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject user = jObj.getJSONObject("battery_service");
                    String image_url = user.getString("image_url");
                    String title = user.getString("title");
                    String content_des =user.getString("content_des");
                    Glide.with(c).load(image_url).into(main_image);
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
        }){};
        AppController.getInstance().addToRequestQueue(stringRequest);
        return v;
    }

}
