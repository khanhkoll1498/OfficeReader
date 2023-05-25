package com.all.officereader.helpers.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * Created by NguyenDuc on 03/2015.
 */
public class SystemUtil {

    private static final String TAG = "SystemUtil";

    /**
     * Opens the specified address in the browser
     *
     * @param ctx
     * @param url Website url
     */
    public static void openPage(Context ctx, String url) {
        try {
            if (null != url && !(url.startsWith("http://") || url.startsWith("https://"))) {
                url = "http://" + url;
            }
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * Open an APP security
     *
     * @param ctx
     * @param intent
     */
    public static void startActivitySafely(Context ctx, Intent intent) {
        if (ctx == null) return;
        if (intent == null) {
            //makeShortToast(ctx, R.string.dockbar_null_intent);
            return;
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            ctx.startActivity(intent);
        } catch (ActivityNotFoundException e) {
        //    makeShortToast(ctx, R.string.activity_not_found);
           // Log.e(BaseConfig.TAG, "Unable to launch. intent=" + intent, e);
        } catch (SecurityException e) {
          //  makeShortToast(ctx, R.string.activity_not_found);
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Open an APP
     *
     * @param ctx
     * @param intent
     *//*
    public static void startActivity(Context ctx, Intent intent) {
        if (intent == null) {
            makeShortToast(ctx, R.string.dockbar_null_intent);
            return;
        }

        try {
            ctx.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            makeShortToast(ctx, R.string.activity_not_found);
            Log.e(BaseConfig.TAG, "Unable to launch. intent=" + intent, e);
        } catch (SecurityException e) {
            makeShortToast(ctx, R.string.activity_not_found);
            Log.e(TAG, e.getMessage());
        } catch (Exception e) {
            makeShortToast(ctx, R.string.activity_not_found);
            e.printStackTrace();
        }
    }*/

 /*   *//**
     * Activity receive returns results
     *
     * @param ctx
     * @param intent
     * @param requestCode
     *//*
    public static void startActivityForResultSafely(Activity ctx, Intent intent, int requestCode) {
        try {
            ctx.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ctx, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(ctx, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
            Log.e(BaseConfig.TAG, e.getMessage());
        } catch (Exception e) {
            Toast.makeText(ctx, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/

    /**
     * Display the soft keyboard
     *
     * @param view
     */
    public static void showKeyboard(View view) {
        if (null == view) return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Hide soft keyboard
     *
     * @param view
     */
    public static void hideKeyboard(View view) {
        if (null == view) return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Hide soft keyboard
     *
     * @param ctx
     */
    public static void createHideInputMethod(Activity ctx) {
        final InputMethodManager manager = (InputMethodManager) ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
        ctx.getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (manager.isActive()) {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });
    }

    /**
     * Create a toast
     *
     * @param ctx
     * @param resId
     */
    public static void makeShortToast(Context ctx, int resId) {
        if (resId == 0) return;
        Toast.makeText(ctx, ctx.getText(resId), Toast.LENGTH_SHORT).show();
    }



    /**
     * <br>Description:Get the resource ID
     *
     * @param ctx
     * @param key
     * @param type
     * @return
     */
    public static int getResourceId(Context ctx, String key, String type) {
        return ctx.getResources().getIdentifier(key, type, ctx.getPackageName());
    }

    /**
     * Is not a system application
     *
     * @param appInfo
     * @return boolean
     */
    public static boolean isSystemApplication(ApplicationInfo appInfo) {
        if (appInfo == null) return false;

        return isSystemApplication(appInfo.flags);
    }

    public static boolean isSystemApplication(int flags) {
        if ((flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) return true;
        else if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) return true;

        return false;
    }

  /*  *//**
     * Determine whether the root, and apply Root privileges The default timeout is 30 seconds
     *
     * @param context
     * @return boolean
     *//*
    public static boolean openSuperShell(Context context) {
        return OpenRootUtil.openSuperShell(context, 30 * 1000);
        //		return false;
    }*/

    /**
     * Diagnosis models (or firmware versions) will support the Google voice recognition function
     *
     * @Return supported returns true, otherwise it returns false
     */
    public static boolean isVoiceRecognitionEnable(Context context) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) return true;
        else return false;
    }

    /**
     * Gets the current process name
     *
     * @param context
     * @return String
     */
    public static String getCurProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static int getVersionApp(Activity activity) {
        PackageInfo pInfo = null;
        try {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int currentAppVersionCode = 0;
        if (pInfo != null) {
            currentAppVersionCode = pInfo.versionCode;
        }
       return currentAppVersionCode;
    }

    public static String getLocalLanguageDevice(){
        String lang = Locale.getDefault().getLanguage();

        if (!lang.equals("")){
            return lang;
        }else {
            return "en";
        }

    }

    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static String getCountryParse() {

        String value="";
//        try {
//            value = new ContentTask().execute().get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        return value;
    }


}