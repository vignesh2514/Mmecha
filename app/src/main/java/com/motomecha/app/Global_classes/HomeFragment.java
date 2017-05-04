package com.motomecha.app.Global_classes;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.motomecha.app.R;
import com.motomecha.app.bike_module.PickService;
import com.motomecha.app.car_module.Car_service_modes;

public class HomeFragment extends Fragment {
WebView webView;
ImageButton Ioffers,Iblog;
    ImageButton Ibike,Icar;

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

Iblog=(ImageButton) v.findViewById(R.id.blog_basic);
//        webView.loadUrl("http://motomecha.com/mmadmin/slidingme.php");
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//
////                imageView.setVisibility(View.VISIBLE);
////                webView.setVisibility(View.INVISIBLE);
//            }
//            public void onProgressChanged(WebView view, int progress)
//            {
//                if(progress < 100 && webView.getVisibility() == ProgressBar.GONE){
//                    Pbar.setVisibility(ProgressBar.VISIBLE);
//                }
//
//                if(progress == 100) {
//                    Pbar.setVisibility(ProgressBar.GONE);
//                }
//            }
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                if (url.contains("http") ) {
//                    final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
//                    startActivity(intent);
//                    return true;
//                } else {
//
//                    return true;
//                }
//
//            }
//        });

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

}
