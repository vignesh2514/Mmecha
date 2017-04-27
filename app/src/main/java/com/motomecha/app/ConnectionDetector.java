package com.motomecha.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by vignesh2514 on 16/1/17.
 */

public class ConnectionDetector {
    Context context;

    public ConnectionDetector(Context context) {
        this.context = context;
    }
    public boolean isConnect(){
        ConnectivityManager conMgr=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMgr!=null){
            NetworkInfo networkInfo=conMgr.getActiveNetworkInfo();
            if(networkInfo!=null){
                if(networkInfo.getState()==NetworkInfo.State.CONNECTED){
                    return true;

                }
            }
        }
        return false;
    }
}