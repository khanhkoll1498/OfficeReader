package com.all.officereader.screens.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.github.ybq.android.spinkit.style.Circle;
import com.all.officereader.R;


public class LoadingRewardDialog extends Dialog {
    public LoadingRewardDialog mInstance;
    private Context mContext;
    private ViewGroup mViewGroup;
    private ProgressBar pgbLoadingStart;
    private LinearLayout lnlLoadingRootview;

    public LoadingRewardDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog_style2);
        mContext = context;
    }

    public LoadingRewardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loading_reward);
        setCanceledOnTouchOutside(false);

        findViews();
        initData();
    }

    private void findViews() {
        pgbLoadingStart = findViewById(R.id.pgb_loading__start1);

    }

    private void initData() {
        Circle circle = new Circle();
        pgbLoadingStart.setIndeterminateDrawable(circle);

    }
}