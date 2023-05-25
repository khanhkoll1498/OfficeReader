package com.all.officereader.screens.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ads.control.AdmobHelp;
import com.ads.control.AdsApplication;
import com.ads.control.AppOpenManager;
import com.all.officereader.R;
import com.all.officereader.helpers.utils.Constant;
import com.all.officereader.helpers.utils.FileUtility;
import com.all.officereader.helpers.utils.SharedPreferencesUtility;
import com.all.officereader.helpers.utils.StorageUtils;

import java.io.File;

public class SplashActivity extends Activity {
    private File mFile;
    private StorageUtils mStorageUtils;
    private int pageNum = -1;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        SharedPreferencesUtility.setTimeRateCurrent(this, System.currentTimeMillis());

        AdsApplication.getInstance().appOpenManager.setOpenAdsListener(new AppOpenManager.OpenAdsListener() {
            @Override
            public void onLoaded() {
                Log.e("AAAAAAAAA", "onLoaded: ");
                AdsApplication.getInstance().appOpenManager.showAdIfAvailable();
            }

            @Override
            public void onError() {
                Log.e("AAAAAAAAA", "onError: ");
                AdmobHelp.getInstance().showInterstitialAd(SplashActivity.this, () -> {
                    launchMain();
                });
            }

            @Override
            public void onClosed() {
                Log.e("AAAAAAAAA", "onClosed: ");
                launchMain();
            }
        });
    }

    private void initView() {
        AdmobHelp.getInstance().init(SplashActivity.this);
    }

    private void launchMain() {
        if (getIntent() != null && getIntent().getStringExtra(Constant.SHORT_CUT_FILE_NAME) != null && !getIntent().getStringExtra(Constant.SHORT_CUT_FILE_NAME).equals("")) {
            try {
                String filepath = getIntent().getStringExtra(Constant.SHORT_CUT_FILE_NAME);
                mFile = new File(filepath);
                mStorageUtils = new StorageUtils(this);
                pageNum = getIntent().getIntExtra(Constant.SHORT_CUT_PAGE_NUM, -1);
                FileUtility.openFile(this, mFile, 0);
                finish();
            } catch (Exception e) {
                finish();
            }

        } else {
            if (!SharedPreferencesUtility.getCheckPermission(this)) {
                Intent mIntent = new Intent(this, IntroductionActivity.class);
                startActivity(mIntent);

            } else {
                Intent mIntent = new Intent(this, MainActivity.class);
                startActivity(mIntent);
            }
            finish();
        }
    }

}
