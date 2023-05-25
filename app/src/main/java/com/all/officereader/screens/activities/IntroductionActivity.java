package com.all.officereader.screens.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.all.officereader.helpers.utils.SharedPreferencesUtility;
import com.all.officereader.screens.adapters.MyViewPagerAdapter;
import com.all.officereader.screens.ocr.general.PermissionUtils;
import com.all.officereader.R;

public class IntroductionActivity extends Activity implements View.OnClickListener {
    private int[] mLayoutList;
    public static final int PERMISSION_EXTERNAL = 0x111111;
    private ViewPager vpgIntroActivityIntro;
    private TextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        if (SharedPreferencesUtility.getCheckPermission(this)) {
            MyViewPagerAdapter.launchMainScreen(IntroductionActivity.this);
        }

        vpgIntroActivityIntro = findViewById(R.id.vpg_intro_activity__intro);
        tvNext = findViewById(R.id.tv_next);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        mLayoutList = new int[]{
                R.layout.intro_slide_1,
                R.layout.intro_slide_2,
                R.layout.intro_slide_3};

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this, mLayoutList);
        vpgIntroActivityIntro.setAdapter(myViewPagerAdapter);
        vpgIntroActivityIntro.setOffscreenPageLimit(3);
        vpgIntroActivityIntro.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        tvNext.setVisibility(View.VISIBLE);
                        break;

                    case 1:
                        tvNext.setVisibility(View.VISIBLE);
                        break;

                    case 2:
                        tvNext.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tvNext.setOnClickListener(this);
        //OverScrollDecoratorHelper.setUpOverScroll(vpgIntroActivityIntro);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_EXTERNAL) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    MyViewPagerAdapter.launchMainScreen(IntroductionActivity.this);
                } else {
                    finish();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_EXTERNAL) {
            if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MyViewPagerAdapter.launchMainScreen(IntroductionActivity.this);
                } else {
                    PermissionUtils.isPermission(PERMISSION_EXTERNAL, IntroductionActivity.this);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openSettingsDevice() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onClick(View v) {
        if (v == tvNext) {
            switch (vpgIntroActivityIntro.getCurrentItem()) {
                case 0:
                    vpgIntroActivityIntro.setCurrentItem(1);
                    break;

                case 1:
                    vpgIntroActivityIntro.setCurrentItem(2);
                    break;
            }
        }
    }
}
