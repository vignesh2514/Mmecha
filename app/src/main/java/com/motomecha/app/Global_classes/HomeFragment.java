package com.motomecha.app.Global_classes;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.motomecha.app.R;
import com.motomecha.app.bike_module.PickService;
import com.motomecha.app.car_module.Car_service_modes;

public class HomeFragment extends Fragment {
WebView webView;
ImageButton Ioffers,Iblog;
    ImageButton Ibike,Icar;
ImageView imageView;
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
    //    webView = (WebView) v.findViewById(R.id.webView);
        Ioffers=(ImageButton) v.findViewById(R.id.imageButton);
      //  webView.getSettings().setJavaScriptEnabled(true);
Ibike=(ImageButton) v.findViewById(R.id.imageButton2);
        Icar=(ImageButton) v.findViewById(R.id.imageButton3);
//imageView=(ImageView) v.findViewById(R.id.imageview19);
Iblog=(ImageButton) v.findViewById(R.id.blog_basic);
        webView = (WebView) v.findViewById(R.id.webView);
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("http://motomecha.com/mmadmin/slidingme.php");
        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://gettalentsapp.com/vignesh2514/motomecha/mmandroadmin/slidingme.php");
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("http://motomecha.com/mmadmin/slidingmwqe.php");
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {

                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();

                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                    Intent intent = new Intent(getActivity(), GlobalWebPage.class);
                    intent.putExtra("title1","OFFERS PAGE");
                    intent.putExtra("wburl", url);
                    startActivity(intent);
             return true;

            }


        });


        Ibike.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent =new Intent(getActivity(),PickService.class);
        startActivity(intent);
    }
});
        Icar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),Car_service_modes.class);
                startActivity(intent);
            }
        });

        Ioffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),OfferPage.class);
                startActivity(intent);
            }
        });
        Iblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GlobalWebPage.class);
                intent.putExtra("title1","OUR BLOG");
                intent.putExtra("wburl", "http://blog.motomecha.com/");
                startActivity(intent);
            }
        });
        return v;

    }
    @Override
    public void onResume(){
        super.onResume();
        webView.loadUrl("http://gettalentsapp.com/vignesh2514/motomecha/mmandroadmin/slidingme.php");
    }

}
