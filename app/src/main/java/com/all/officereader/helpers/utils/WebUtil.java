package com.all.officereader.helpers.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import com.all.officereader.R;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class WebUtil {
    public static  void openWebPage(Context context, String query) {
        String escapedQuery = null;
        try {
            escapedQuery = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static void goToGooglePlay(Context context, String pgn) {
        Uri uri = Uri.parse("market://details?id=" + pgn);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + pgn)));
        }
    }

    public static void goToGooglePlay2(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    public static void sendFeedBack(Context context, String email,String content){
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.setType("text/email");
        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
        Email.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.rate_title));
        Email.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(Email, "Send Feedback:"));
    }
    /**
     * Start Intent to launch web browser.
     */
    boolean launchUrlInDefaultBrowser(Context context, String url) {
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        browserIntent.setData(Uri.parse(url));

        // 1: Try to find the default browser and launch the URL with it
        final ResolveInfo defaultResolution = context.getPackageManager().resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (defaultResolution != null) {
            final ActivityInfo activity = defaultResolution.activityInfo;
            if (!activity.name.equals("com.android.internal.app.ResolverActivity")) {
                browserIntent.setClassName(activity.applicationInfo.packageName, activity.name);
                context.startActivity(browserIntent);
                return true;
            }
        }
        // 2: Try to find anything that we can launch the URL with. Pick up the first one that can.
        final List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (!resolveInfoList.isEmpty()) {
            browserIntent.setClassName(resolveInfoList.get(0).activityInfo.packageName, resolveInfoList.get(0).activityInfo.name);
            context.startActivity(browserIntent);
            return true;
        }
        return false;
    }

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            return nInfo != null && nInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
