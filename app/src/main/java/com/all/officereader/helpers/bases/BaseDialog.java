package com.all.officereader.helpers.bases;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

public abstract class BaseDialog extends Dialog {
    private Context mContext;

    public BaseDialog(Context context) {
        super(context);
        mContext = context;
    }

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }
}

