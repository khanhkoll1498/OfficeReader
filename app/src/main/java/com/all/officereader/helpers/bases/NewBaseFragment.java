package com.all.officereader.helpers.bases;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.all.officereader.helpers.controllers.RemoveAdsController;
import com.all.officereader.screens.activities.MainActivity;

import org.jetbrains.annotations.NotNull;

public abstract class NewBaseFragment extends Fragment implements RemoveAdsController.OnAdsStatusListener {
    protected Context mContext;

    protected abstract void initData();

    protected abstract int getIdLayout();

    protected abstract void initView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        RemoveAdsController.getInstance().registerAdsChangeListener(this);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(getIdLayout(), container, false);
        initView(mView);
        initData();
        return mView;
    }

    public abstract void loadingNativeBanner(MainActivity mainActivity);

    public abstract void checkInternetConnected(MainActivity mainActivity);

    @Override
    public void onDestroy() {
        RemoveAdsController.getInstance().unregisterAdsChangeListener(this);
        super.onDestroy();
    }
}
