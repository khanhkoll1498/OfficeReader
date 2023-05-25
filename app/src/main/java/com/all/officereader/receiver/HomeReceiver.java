package com.all.officereader.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class HomeReceiver {
    private static Context mContext;
    private static IntentFilter mFilter;
    private static InnerReceiver mReceiver;
    public OnHomePressedListener mListener;

    class InnerReceiver extends BroadcastReceiver {
        private final String SYSTEM_DIALOG_REASON_KEY = "reason";

        InnerReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
                String stringExtra = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (stringExtra != null && HomeReceiver.this.mListener != null) {
                    if (stringExtra.equals("homekey")) {
                        HomeReceiver.this.mListener.onHomePressed();
                    } else if (stringExtra.equals("recentapps")) {
                        HomeReceiver.this.mListener.onHomeLongPressed();
                    }
                }
            }
        }
    }

    public interface OnHomePressedListener {
        void onHomeLongPressed();

        void onHomePressed();
    }

    public HomeReceiver(Context context) {
        mContext = context;
        mFilter = new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS");
    }

    public void setOnHomePressedListener(OnHomePressedListener onHomePressedListener) {
        this.mListener = onHomePressedListener;
        mReceiver = new InnerReceiver();
    }

    public static void startWatch() {
        try {
            if (mReceiver != null) {
                mContext.registerReceiver(mReceiver, mFilter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopWatch() {
        try {
            if (mReceiver != null) {
                mContext.unregisterReceiver(mReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
