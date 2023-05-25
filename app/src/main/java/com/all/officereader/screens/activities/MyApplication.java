package com.all.officereader.screens.activities;

import androidx.appcompat.app.AppCompatDelegate;

import com.artifex.sonui.MainApp;

public class MyApplication extends MainApp {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
