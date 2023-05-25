package com.ads.control;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ads.control.funtion.UtilsApp;

public class Rate {
    public static void Show(final Context mContext, int Style, onDismissDialog onDismissDialog) {
        Show(mContext, Style, true, onDismissDialog);
    }

    public static void Show(final Context mContext, int Style, boolean isFinish, onDismissDialog onDismissDialog) {
        try {
            if (UtilsApp.isConnectionAvailable(mContext)) {
                Log.e("TTTTTTTTTTTTTTTT", "isConnectionAvailable: ");
                if (!PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("Show_rate", false)) {
                    Log.e("TTTTTTTTTTTTTTTT", "getDefaultSharedPreferences: ");
                    RateApp rateApp = new RateApp(mContext, mContext.getString(R.string.email_feedback), mContext.getString(R.string.Title_email), Style, isFinish, onDismissDialog);
                    rateApp.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    rateApp.show();
                } else if (isFinish) {
                    Log.e("TTTTTTTTTTTTTTTT", "isFinish_22222222222: ");
                    //((Activity) (mContext)).finish();
                    if (onDismissDialog != null) {
                        onDismissDialog.onDismissed();
                    }
                }

            } else if (isFinish) {
                Log.e("TTTTTTTTTTTTTTTT", "isFinish: ");
                if (onDismissDialog != null) {
                    onDismissDialog.onDismissed();
                }
            }

        } catch (Exception e) {
            Log.e("TTTTTTTTTTTTTTTT", "Show_Exception: " + e.getMessage() );
            e.printStackTrace();
            if (onDismissDialog != null) {
                onDismissDialog.onDismissed();
            }
        }
    }

    public interface onDismissDialog {
        void onDismissed();
    }
}
