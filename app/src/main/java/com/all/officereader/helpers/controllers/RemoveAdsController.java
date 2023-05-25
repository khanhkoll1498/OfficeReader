package com.all.officereader.helpers.controllers;

import java.util.ArrayList;

public class RemoveAdsController {
    public static RemoveAdsController mInstance;
    private ArrayList<OnAdsStatusListener> mOnAdsStatusListeners = new ArrayList<>();
    private boolean isNoAdsUser;
    private static String publicKey = null;

    public static RemoveAdsController getInstance() {
        if (mInstance == null){
            mInstance = new RemoveAdsController();
        }
        return mInstance;
    }

    public void setNoAdsUser(boolean isNoAdsUser){
        this.isNoAdsUser = isNoAdsUser;
    }

    public boolean isNoAdsUser() {
        return isNoAdsUser;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public static void setPublicKey(String publicKey) {
        RemoveAdsController.publicKey = publicKey;
    }

    public void registerAdsChangeListener(OnAdsStatusListener onAdsStatusListener) {
        mOnAdsStatusListeners.add(onAdsStatusListener);
    }

    public void unregisterAdsChangeListener(OnAdsStatusListener onAdsStatusListener) {
        mOnAdsStatusListeners.remove(onAdsStatusListener);
    }

    public void updateAdsStatus(boolean isShow) {
        for (OnAdsStatusListener onAdsStatusListener : mOnAdsStatusListeners) {
            onAdsStatusListener.updateAds(isShow);
        }
    }

    public interface OnAdsStatusListener {
        void updateAds(boolean isNoAds);
    }
}
