package com.ads.control.nativeAds;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ads.control.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class NativeAdsView extends RelativeLayout {

    public static final int VIEW_TYPE_ADS = 0;
    public static final int VIEW_TYPE_ITEM_NORMAL = 1;


    public NativeAdsView(Context context, NativeLoadListener mNativeLoadListener) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.load_item_native, this);
        loadNativeGG(context,mNativeLoadListener);
    }

    public void loadNativeGG(final Context mContext,NativeLoadListener mNativeLoadListener) {
        ShimmerFrameLayout containerShimmer = findViewById(R.id.shimmer_gg_container);
        containerShimmer.setVisibility(View.VISIBLE);
        containerShimmer.startShimmer();
        AdLoader.Builder builder = new AdLoader.Builder(mContext, mContext.getString(R.string.admob_native))
                .forNativeAd(nati -> {
                    setVisibility(VISIBLE);
                    containerShimmer.stopShimmer();
                    containerShimmer.setVisibility(View.GONE);
                    LinearLayout mContainer = findViewById(R.id.banner_container);
                    NativeAdView adView = (NativeAdView) LayoutInflater.from(mContext)
                            .inflate(R.layout.native_admob_ad_item, null);
                    populateUnifiedNativeAdView(nati, adView);
                    mContainer.removeAllViews();
                    mContainer.addView(adView);
                    if (mNativeLoadListener != null)
                        mNativeLoadListener.onLoadSuccess();
                });
        builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
        builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                    containerShimmer.setVisibility(GONE);
                    containerShimmer.startShimmer();
                    setVisibility(GONE);
                    Log.e("SHOWADS", "onAdFailedToLoad: ");
                    if (mNativeLoadListener != null)
                        mNativeLoadListener.onErro();
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("SHOWADS", "onAdLoaded: ");
            }
        }).build().loadAd(new AdRequest.Builder().build());
    }

    private void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));

        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

//        if (nativeAd.getPrice() == null) {
//            adView.getPriceView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getPriceView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
//        }
//
//        if (nativeAd.getStore() == null) {
//            adView.getStoreView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getStoreView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
//        }
//
//        if (nativeAd.getStarRating() == null) {
//            adView.getStarRatingView().setVisibility(View.INVISIBLE);
//        } else {
//            ((RatingBar) adView.getStarRatingView())
//                    .setRating(nativeAd.getStarRating().floatValue());
//            adView.getStarRatingView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getAdvertiser() == null) {
//            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
//        } else {
//            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
//            adView.getAdvertiserView().setVisibility(View.VISIBLE);
//        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);

    }
}
