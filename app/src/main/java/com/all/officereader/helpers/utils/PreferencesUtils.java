package com.all.officereader.helpers.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

public class PreferencesUtils {
    public static final String PREFS_NAME = "docs";
    public static final String IS_RATE_APP = "isShowCase";
    public static final String IS_VERTICAL = "vertical";
    public static final String DAY_KEEP_APP = "day_lunches";
    public static final String COUNT_TIME_OPEN_APP = "count_open";
    private final static int DAYS_UNTIL_PROMPT = 3;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 3;//Min number of launches
    private final static String DATE_LUNCHES = "date_launches";//Min number of launches

    public PreferencesUtils() {
        super();
    }


    public boolean isShowCase(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        boolean preferencesBoolean = sharedPreferences.getBoolean(IS_RATE_APP, false);
        return preferencesBoolean;
    }

    public void setShowCase(Context context) {
        SharedPreferences.Editor editor;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_RATE_APP, true);
        editor.commit();
    }
    public static void setIsHorizontal(Context context, boolean b) {
        SharedPreferences.Editor editor;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_VERTICAL, b);
        editor.commit();
    }
    public static boolean isHorizontal(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        boolean preferencesBoolean = sharedPreferences.getBoolean(IS_VERTICAL, true);
        return preferencesBoolean;
    }

    private static void setDays(Context context, int day) {
        setCountTimeOpenApp(context);
        SharedPreferences.Editor editor;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        editor = sharedPreferences.edit();
        editor.putInt(DAY_KEEP_APP, day);
        editor.commit();

    }

    private static int getDayKeepApp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        int days = sharedPreferences.getInt(DAY_KEEP_APP, 0);
        return days;
    }
    private static int getCountTimeOpenApp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        int count = sharedPreferences.getInt(COUNT_TIME_OPEN_APP, 0);
        return count;
    }
    private static void setCountTimeOpenApp(Context context) {
        SharedPreferences.Editor editor;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        editor = sharedPreferences.edit();
        int count = getCountTimeOpenApp(context);
        count++;
        editor.putInt(COUNT_TIME_OPEN_APP, count);
        editor.commit();
    }

    public static void FBTracking(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Get date of first launch
        Long date_firstLaunch = sharedPreferences.getLong(DATE_LUNCHES, 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong(DATE_LUNCHES, date_firstLaunch);
            editor.commit();
        }

        if (System.currentTimeMillis() >= (date_firstLaunch + (7 * 24 * 60 * 60 * 1000))) {

            setDays(mContext, 8);
        }
        if (System.currentTimeMillis() >= (date_firstLaunch + (6 * 24 * 60 * 60 * 1000))) {

            setDays(mContext, 7);
        } else if (System.currentTimeMillis() >= (date_firstLaunch + (5 * 24 * 60 * 60 * 1000))) {

            setDays(mContext, 6);
        } else if (System.currentTimeMillis() >= (date_firstLaunch + (4 * 24 * 60 * 60 * 1000))) {

            setDays(mContext, 5);
        } else if (System.currentTimeMillis() >= (date_firstLaunch + (3 * 24 * 60 * 60 * 1000))) {

            setDays(mContext, 4);
        } else if (System.currentTimeMillis() >= (date_firstLaunch + (2 * 24 * 60 * 60 * 1000))) {

            setDays(mContext, 3);
        } else if (System.currentTimeMillis() >= (date_firstLaunch + (24 * 60 * 60 * 1000))) {

            setDays(mContext, 2);
        }
        else if (System.currentTimeMillis() < (date_firstLaunch + (24 * 60 * 60 * 1000))) {

            setDays(mContext, 1);
        }

    }

    public static boolean checkInstalled(String packagename, Context context){
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
