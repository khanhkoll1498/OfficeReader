package com.all.officereader.helpers.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.all.officereader.R;

import java.io.File;

public class AppUtil {
    public static void setStatusBarTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void hideKeyboard(View view) {
        if (null == view) return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void rename(Context context, File file, String newName) {
        try {
            File file2 = new File(file.getParent() + "/" + newName);
            if (file2.exists()) {
                Toast.makeText(context, "file da ton tai", Toast.LENGTH_SHORT).show();
            } else {
                boolean success = file.renameTo(file2);
                if (success)  {
                    Toast.makeText(context,"success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context,"fail", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToGooglePlay(Context context, String pgn) {
        Uri uri = Uri.parse("market://details?id=" + pgn);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK
            );
        }
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + pgn)));
        }
    }

    public static void sendFeedBack(Context context, String email, String content){
        Intent email2 = new Intent(Intent.ACTION_SEND);
        email2.setType("text/email");
        email2.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
        email2.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.rate_title));
        email2.putExtra(Intent.EXTRA_TEXT, content);
        Intent chooserIntent = Intent.createChooser(email2, "Send Feedback:");
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooserIntent);
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            if (context != null && context.getPackageManager()!= null) {
                context.getPackageManager().getApplicationInfo(packageName, 0);
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void launcherMarket(Context context, String packageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }
}
