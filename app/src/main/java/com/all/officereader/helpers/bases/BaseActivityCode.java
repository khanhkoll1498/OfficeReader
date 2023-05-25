package com.all.officereader.helpers.bases;


import android.app.Activity;

public abstract class BaseActivityCode extends Activity {
    protected abstract void initView();

    protected abstract void initData();

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(LanguageUtils.onAttach(newBase));
//    }
}
