package com.all.officereader.helpers.receivers;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class ConnectInternetReceiver extends BroadcastReceiver {

    private static final String TAG =ConnectInternetReceiver.class.getName() ;
    public static ConnectInternetReceiverListener mConnectInternetReceiver;

    public ConnectInternetReceiver() {
    }

    public static void setConnectInternetReceiver(ConnectInternetReceiverListener mConnectInternetReceiver) {
        ConnectInternetReceiver.mConnectInternetReceiver = mConnectInternetReceiver;
    }

    public ConnectInternetReceiver(ConnectInternetReceiverListener connectInternetReceiverListener) {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: co internet");
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        if (mConnectInternetReceiver != null) {
            Log.d(TAG, "onReceive: co internet2");
            mConnectInternetReceiver.onNetworkConnectionChanged(isConnected);
        }
//
//        if (isConnected){
//
//            EventBus.getDefault().post(new EventBusEntity(EventBusEntity.ON_CONNECTED_INTERNET_STORE_THEME));
//        }
    }

    public static boolean isConnected(Application mApplication) {
        ConnectivityManager
                cm = (ConnectivityManager) mApplication.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
    public static boolean isConnected(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean checkInternetConnecting(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
    public interface ConnectInternetReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
