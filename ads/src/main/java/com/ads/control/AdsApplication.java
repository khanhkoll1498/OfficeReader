package com.ads.control;

import androidx.multidex.MultiDexApplication;

import com.google.android.gms.ads.MobileAds;

public class AdsApplication extends MultiDexApplication {

    public static AppOpenManager appOpenManager;

    private static AdsApplication instance;

    public static AdsApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null)
            instance = this;
        MobileAds.initialize(
                this,
                initializationStatus -> {
                });
        appOpenManager = new AppOpenManager(this);
    }
}
