package com.ads.control.nativeAds;

import androidx.recyclerview.widget.RecyclerView;

public class NativeViewHolder extends RecyclerView.ViewHolder {
    public NativeAdsView rootView;

    public NativeViewHolder(NativeAdsView itemView) {
        super(itemView);
        this.rootView = itemView;
    }
}
