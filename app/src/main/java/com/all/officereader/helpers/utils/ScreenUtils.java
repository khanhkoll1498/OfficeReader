package com.all.officereader.helpers.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;

public class ScreenUtils {
    public static int CURENT_ITEM = 0;

    /**
     * Des: Get The screen size
     *
     * @return int[1] => width screen, int[2] => height screen
     */
    public static int[] getCameraSize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return new int[]{dm.widthPixels, dm.heightPixels};
    }


    @SuppressWarnings("deprecation")
    /**
     * M todo que devuelve el alto de la pantalla
     *
     * @param context Context de la aplicaci n
     *
     * @return int con la altura
     */
    public static int getScreenHeight(Context context) {

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();

    }

    @SuppressWarnings("deprecation")
    /**
     * M todo que devuelve el ancho de la pantalla
     *
     * @param context Context de la aplicaci n
     *
     * @return int con la ancho
     */
    public static int getScreenWidth(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();

    }

    public static void setStatusBarTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        // set flags
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void setStatusBarNoActionbar(Activity activity) {
        //Remove title bar
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    public static int getSoftKeysHeight(Context context) {
        WindowManager paramWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
//        return hasMenuKey;
        int i;
        int j;
        int k;
        int m;
        Display localDisplay = paramWindowManager.getDefaultDisplay();
        DisplayMetrics localDisplayMetrics1 = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            localDisplay.getRealMetrics(localDisplayMetrics1);
        } else {
            return 0;
        }
        i = localDisplayMetrics1.heightPixels;
        j = localDisplayMetrics1.widthPixels;

        DisplayMetrics localDisplayMetrics2 = new DisplayMetrics();
        localDisplay.getMetrics(localDisplayMetrics2);
        k = localDisplayMetrics2.heightPixels;
        m = localDisplayMetrics2.widthPixels;
        int sSoftKeyHeight = i - k;
        if ((j - m > 0) || (i - k > 0)) {
            return sSoftKeyHeight;
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = context.getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    public static int getActionbarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    public static void exitNotification(Context mContext) {
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mContext.sendBroadcast(it);
    }

    // Animation dialog loading
    public static AnimationSet getInAnimation(Context context) {
        AnimationSet in = new AnimationSet(context, null);

        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(90);

        ScaleAnimation scale1 = new ScaleAnimation(0.8f, 1.05f, 0.8f, 1.05f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale1.setDuration(135);

        ScaleAnimation scale2 = new ScaleAnimation(1.05f, 0.95f, 1.05f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale2.setDuration(105);
        scale2.setStartOffset(135);

        ScaleAnimation scale3 = new ScaleAnimation(0.95f, 1f, 0.95f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale3.setDuration(60);
        scale3.setStartOffset(240);

        in.addAnimation(alpha);
        in.addAnimation(scale1);
        in.addAnimation(scale2);
        in.addAnimation(scale3);

        return in;
    }

    public static AnimationSet getOutAnimation(Context context) {
        AnimationSet out = new AnimationSet(context, null);
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(150);
        ScaleAnimation scale = new ScaleAnimation(1.0f, 0.6f, 1.0f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(150);
        out.addAnimation(alpha);
        out.addAnimation(scale);
        return out;
    }
    // End

    // Check network online or off
    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.showSoftInput(view, 0);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

