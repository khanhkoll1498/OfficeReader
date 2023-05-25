package com.all.officereader.helpers.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ToastUtil {
    private static Toast myToast;

    public static void showToast(Context context, String text) {
        if (myToast != null) {
            myToast.cancel();
        } else {
            myToast = new Toast(context);
        }
        myToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        myToast.show();
    }

    public static void showToastLong(Context context, String text) {
        if (myToast != null) {
            myToast.cancel();
        } else {
            myToast = new Toast(context);
        }
        myToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        myToast.show();
    }

    public static void showToastErrorWithIcon(Context context, String text) {
        Toasty.error(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void showToastError(Context context, String text) {
        Toasty.error(context, text, Toast.LENGTH_SHORT, false).show();
    }

    public static void showToastSuccessWithIcon(Context context, String text) {
        Toasty.success(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void showToastSuccess(Context context, String text) {
        Toasty.success(context, text, Toast.LENGTH_SHORT, false).show();
    }

    public static void showToastInfoWithIcon(Context context, String text) {
        Toasty.info(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void showToastInfo(Context context, String text) {
        Toasty.info(context, text, Toast.LENGTH_SHORT, false).show();
    }

    public static void showToastWarningWithIcon(Context context, String text) {
        Toasty.warning(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void showToastWarning(Context context, String text) {
        Toasty.warning(context, text, Toast.LENGTH_SHORT, false).show();
    }
}


