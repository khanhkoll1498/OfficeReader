package com.all.officereader.helpers.bases;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.View;

import com.all.officereader.helpers.controllers.RemoveAdsController;
import com.all.officereader.screens.activities.MainActivity;

public abstract class BaseFragment extends Fragment implements RemoveAdsController.OnAdsStatusListener {
    protected Context mContext;

    protected abstract void initData();

    protected abstract void initView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        RemoveAdsController.getInstance().registerAdsChangeListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    public abstract void loadingNativeBanner(MainActivity mainActivity);

    public abstract void checkInternetConnected(MainActivity mainActivity);

    @Override
    public void onDestroy() {
        RemoveAdsController.getInstance().unregisterAdsChangeListener(this);
        super.onDestroy();
    }
}
