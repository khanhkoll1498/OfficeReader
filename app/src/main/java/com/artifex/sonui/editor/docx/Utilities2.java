//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.artifex.sonui.editor.docx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.supportv1.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.webkit.MimeTypeMap;
import android.widget.TextView.BufferType;

import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SODoc;
import com.artifex.solib.k;
import com.artifex.sonui.editor.NUIPKCS7Signer;
import com.artifex.sonui.editor.NUIPKCS7Verifier;
import com.artifex.sonui.editor.ProgressDialogDelayed;
import com.artifex.sonui.editor.R.color;
import com.artifex.sonui.editor.R.drawable;
import com.artifex.sonui.editor.R.id;
import com.artifex.sonui.editor.R.integer;
import com.artifex.sonui.editor.R.layout;
import com.artifex.sonui.editor.R.string;
import com.artifex.sonui.editor.R.style;
import com.artifex.sonui.editor.SODataLeakHandlers;
import com.artifex.sonui.editor.SODocSession;
import com.artifex.sonui.editor.SOEditText;
import com.artifex.sonui.editor.SOPersistentStorage;
import com.artifex.sonui.editor.SOTextView;
import com.artifex.sonui.editor.Utilities;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Utilities2 extends Utilities {
    private static String a;
    private static AlertDialog b;
    private static SODataLeakHandlers c;
    private static SOPersistentStorage d;
    private static SODocSession.SODocSessionLoadListenerCustom e;
    public static Callback editFieldDlpHandler;
    private static final Set<String> f;
    public static final String generalStore = "general";
    public static SigningFactoryListener mSigningFactory;

    static {
        HashSet var0 = new HashSet();
        var0.add("ar");
        var0.add("dv");
        var0.add("fa");
        var0.add("ha");
        var0.add("he");
        var0.add("iw");
        var0.add("ji");
        var0.add("ps");
        var0.add("ur");
        var0.add("yi");
        f = Collections.unmodifiableSet(var0);
        editFieldDlpHandler = new Callback() {
            public boolean onActionItemClicked(ActionMode var1, MenuItem var2) {
                return false;
            }

            public boolean onCreateActionMode(ActionMode var1, Menu var2) {
                return true;
            }

            public void onDestroyActionMode(ActionMode var1) {
            }

            public boolean onPrepareActionMode(ActionMode var1, Menu var2) {
                ConfigOptions var3 = ConfigOptions.a();
                if (!var3.h()) {
                    var2.removeItem(16908341);
                }

                if (!var3.j()) {
                    var2.removeItem(16908320);
                    var2.removeItem(16908321);
                }

                if (!var3.i()) {
                    var2.removeItem(16908322);
                }

                return true;
            }
        };
    }

    public Utilities2() {
    }

    private static Point a(WindowManager var0) {
        Display var6 = var0.getDefaultDisplay();
        int var2;
        int var3;
        DisplayMetrics var1 = new DisplayMetrics();
        var6.getRealMetrics(var1);
        var2 = var1.widthPixels;
        var3 = var1.heightPixels;

        return new Point(var2, var3);
    }

    private static String a(int var0, Context var1) {
        String var3 = Integer.toHexString(var1.getResources().getColor(var0));
        var0 = var3.length();
        if (var0 > 6) {
            var3 = var3.substring(var0 - 6);
            String var2 = "#" + var3;
            var3 = var2;
        } else {
            var3 = "rgba(0, 0, 0, 0);";
        }

        return var3;
    }

    private static String a(String var0) {
        String var1 = com.artifex.solib.a.g(var0);
        if (var1.compareToIgnoreCase("xps") == 0) {
            var0 = "application/vnd.ms-xpsdocument";
        } else {
            var0 = null;
        }

        if (var1.compareToIgnoreCase("cbz") == 0) {
            var0 = "application/x-cbz";
        }

        if (var1.compareToIgnoreCase("svg") == 0) {
            var0 = "image/svg+xml";
        }

        return var0;
    }

    public static int colorForDocExt(Context var0, String var1) {
        byte var2;
        label139:
        {
            switch (var1.hashCode()) {
                case 97669:
                    if (var1.equals("bmp")) {
                        var2 = 22;
                        break label139;
                    }
                    break;
                case 98299:
                    if (var1.equals("cbz")) {
                        var2 = 32;
                        break label139;
                    }
                    break;
                case 98822:
                    if (var1.equals("csv")) {
                        var2 = 20;
                        break label139;
                    }
                    break;
                case 99640:
                    if (var1.equals("doc")) {
                        var2 = 0;
                        break label139;
                    }
                    break;
                case 100542:
                    if (var1.equals("emf")) {
                        var2 = 30;
                        break label139;
                    }
                    break;
                case 101110:
                    if (var1.equals("fb2")) {
                        var2 = 36;
                        break label139;
                    }
                    break;
                case 102340:
                    if (var1.equals("gif")) {
                        var2 = 25;
                        break label139;
                    }
                    break;
                case 103745:
                    if (var1.equals("hwp")) {
                        var2 = 21;
                        break label139;
                    }
                    break;
                case 105441:
                    if (var1.equals("jpg")) {
                        var2 = 23;
                        break label139;
                    }
                    break;
                case 110834:
                    if (var1.equals("pdf")) {
                        var2 = 18;
                        break label139;
                    }
                    break;
                case 111145:
                    if (var1.equals("png")) {
                        var2 = 26;
                        break label139;
                    }
                    break;
                case 111219:
                    if (var1.equals("pps")) {
                        var2 = 11;
                        break label139;
                    }
                    break;
                case 111220:
                    if (var1.equals("ppt")) {
                        var2 = 10;
                        break label139;
                    }
                    break;
                case 114276:
                    if (var1.equals("svg")) {
                        var2 = 31;
                        break label139;
                    }
                    break;
                case 114833:
                    if (var1.equals("tif")) {
                        var2 = 27;
                        break label139;
                    }
                    break;
                case 115312:
                    if (var1.equals("txt")) {
                        var2 = 19;
                        break label139;
                    }
                    break;
                case 117840:
                    if (var1.equals("wmf")) {
                        var2 = 29;
                        break label139;
                    }
                    break;
                case 118783:
                    if (var1.equals("xls")) {
                        var2 = 5;
                        break label139;
                    }
                    break;
                case 118907:
                    if (var1.equals("xps")) {
                        var2 = 34;
                        break label139;
                    }
                    break;
                case 3088949:
                    if (var1.equals("docm")) {
                        var2 = 2;
                        break label139;
                    }
                    break;
                case 3088960:
                    if (var1.equals("docx")) {
                        var2 = 1;
                        break label139;
                    }
                    break;
                case 3089476:
                    if (var1.equals("dotm")) {
                        var2 = 4;
                        break label139;
                    }
                    break;
                case 3089487:
                    if (var1.equals("dotx")) {
                        var2 = 3;
                        break label139;
                    }
                    break;
                case 3120248:
                    if (var1.equals("epub")) {
                        var2 = 33;
                        break label139;
                    }
                    break;
                case 3268712:
                    if (var1.equals("jpeg")) {
                        var2 = 24;
                        break label139;
                    }
                    break;
                case 3446968:
                    if (var1.equals("potm")) {
                        var2 = 15;
                        break label139;
                    }
                    break;
                case 3446979:
                    if (var1.equals("potx")) {
                        var2 = 14;
                        break label139;
                    }
                    break;
                case 3447898:
                    if (var1.equals("ppsm")) {
                        var2 = 17;
                        break label139;
                    }
                    break;
                case 3447909:
                    if (var1.equals("ppsx")) {
                        var2 = 16;
                        break label139;
                    }
                    break;
                case 3447929:
                    if (var1.equals("pptm")) {
                        var2 = 13;
                        break label139;
                    }
                    break;
                case 3447940:
                    if (var1.equals("pptx")) {
                        var2 = 12;
                        break label139;
                    }
                    break;
                case 3559925:
                    if (var1.equals("tiff")) {
                        var2 = 28;
                        break label139;
                    }
                    break;
                case 3682382:
                    if (var1.equals("xlsm")) {
                        var2 = 6;
                        break label139;
                    }
                    break;
                case 3682393:
                    if (var1.equals("xlsx")) {
                        var2 = 7;
                        break label139;
                    }
                    break;
                case 3682413:
                    if (var1.equals("xltm")) {
                        var2 = 8;
                        break label139;
                    }
                    break;
                case 3682424:
                    if (var1.equals("xltx")) {
                        var2 = 9;
                        break label139;
                    }
                    break;
                case 114035747:
                    if (var1.equals("xhtml")) {
                        var2 = 35;
                        break label139;
                    }
            }

            var2 = -1;
        }

        int var3;
        switch (var2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                var3 = color.sodk_editor_header_doc_color;
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                var3 = color.sodk_editor_header_xls_color;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                var3 = color.sodk_editor_header_ppt_color;
                break;
            case 18:
                var3 = color.sodk_editor_header_pdf_color;
                break;
            case 19:
            case 20:
                var3 = color.sodk_editor_header_txt_color;
                break;
            case 21:
                var3 = color.sodk_editor_header_hwp_color;
                break;
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                var3 = color.sodk_editor_header_image_color;
                break;
            case 31:
                var3 = color.sodk_editor_header_svg_color;
                break;
            case 32:
                var3 = color.sodk_editor_header_cbz_color;
                break;
            case 33:
                var3 = color.sodk_editor_header_epub_color;
                break;
            case 34:
                var3 = color.sodk_editor_header_xps_color;
                break;
            case 35:
            case 36:
                var3 = color.sodk_editor_header_fb2_color;
                break;
            default:
                var3 = color.sodk_editor_header_unknown_color;
        }

        return ContextCompat.getColor(var0, var3);
    }

    public static int colorForDocType(Context var0, String var1) {
        return colorForDocExt(var0, com.artifex.solib.a.g(var1));
    }

    public static int convertDpToPixel(float var0) {
        return Math.round(var0 * ((float) Resources.getSystem().getDisplayMetrics().densityDpi / 160.0F));
    }

    public static ProgressDialog createAndShowWaitSpinner(Context var0) {
        ProgressDialog var1 = new ProgressDialog(var0);

        try {
            var1.show();
        } catch (BadTokenException ignored) {
        }

        var1.setCancelable(false);
        var1.setIndeterminate(true);
        var1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        var1.setContentView(layout.sodk_editor_wait_spinner);
        return var1;
    }

    public static void dismissCurrentAlert() {
        AlertDialog var0 = b;
        if (var0 != null) {
            try {
                var0.dismiss();
            } catch (Exception ignored) {
            }

            b = null;
        }

    }

    public static ProgressDialog displayPleaseWaitWithCancel(Context var0, final Runnable var1) {
        ProgressDialogDelayed var2 = new ProgressDialogDelayed(var0, 1000);
        var2.setIndeterminate(true);
        var2.setCancelable(false);
        var2.setTitle(var0.getString(string.sodk_editor_please_wait));
        var2.setMessage(null);
        if (var1 != null) {
            var2.setButton(-2, var0.getString(string.sodk_editor_cancel), new OnClickListener() {
                public void onClick(DialogInterface var1x, int var2) {
                    var1x.dismiss();
                    var1.run();
                }
            });
        }

        var2.show();
        return var2;
    }


    @SuppressLint("DefaultLocale")
    public static String formatFloat(float var0) {
        if (var0 % 1.0F == 0.0F) {
            return String.format("%.0f", var0);
        } else {
            String var1 = Double.toString(Math.abs((double) var0));
            int var2 = var1.indexOf(46);
            int var3 = var1.length();
            String var4 = "%." + (var3 - var2 - 1) + "f";
            return String.format(var4, var0);
        }
    }

    public static Map<String, ?> getAllStringPreferences(Object var0) {
        SOPersistentStorage var1 = d;
        if (var1 != null) {
            return var1.getAllStringPreferences(var0);
        } else {
            Log.d("Utilities", "No implementation of the SOPersistentStorage interface found");
            throw new RuntimeException();
        }
    }

    public static String getApplicationName(Context var0) {
        ApplicationInfo var1 = var0.getApplicationInfo();
        int var2 = var1.labelRes;
        String var3;
        if (var2 == 0) {
            var3 = var1.nonLocalizedLabel.toString();
        } else {
            var3 = var0.getString(var2);
        }

        return var3;
    }

    public static SODataLeakHandlers getDataLeakHandlers() {
        return c;
    }

    public static File getDownloadDirectory(Context var0) {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    public static String getFileStateForPrint() {
        return a;
    }

    public static String getMimeType(String var0) {
        String var1 = com.artifex.solib.a.g(var0);
        if (var1 != null) {
            var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var1);
        } else {
            var1 = null;
        }

        String var2 = var1;
        if (var1 == null) {
            var2 = a(var0);
        }

        return var2;
    }

    public static String getOpenErrorDescription(Context var0, int var1) {
        String var2;
        if (var1 != 5) {
            var2 = String.format(var0.getString(string.sodk_editor_doc_open_error), var1);
        } else {
            var2 = var0.getString(string.sodk_editor_doc_uses_unsupported_enc);
        }

        return var2;
    }

    public static SOPersistentStorage getPersistentStorage() {
        return d;
    }

    public static Object getPreferencesObject(Context var0, String var1) {
        SOPersistentStorage var2 = d;
        if (var2 != null) {
            return var2.getStorageObject(var0, var1);
        } else {
            Log.d("Utilities", "No implementation of the SOPersistentStorage interface found");
            throw new RuntimeException();
        }
    }

    public static Point getRealScreenSize(Activity var0) {
        return a(var0.getWindowManager());
    }

    public static Point getRealScreenSize(Context var0) {
        return a((WindowManager) var0.getSystemService(Context.WINDOW_SERVICE));
    }

    public static File getRootDirectory(Context var0) {
        return Environment.getExternalStorageDirectory();
    }

    public static String getSDCardPath(Context var0) {
        File[] var2 = var0.getExternalFilesDirs(null);
        Log.d("sdcard", String.format("getSDCardPath: there are %d external locations", var2.length));
        String var3;
        if (var2.length > 1 && var2[1] != null) {
            var3 = var2[1].getAbsolutePath();
            Log.d("sdcard", String.format("getSDCardPath: possible sd card path is %s", var3));
            int var1 = var3.toLowerCase().indexOf("/android/");
            if (var1 > 0) {
                var3 = var3.substring(0, var1);
                Log.d("sdcard", String.format("getSDCardPath: SD card is at %s", var3));
                return var3;
            }

            var3 = String.format("getSDCardPath: did not find /Android/ in %s", var3);
        } else {
            var3 = "getSDCardPath: too few external locations";
        }

        Log.d("sdcard", var3);
        return null;
    }

    public static Point getScreenSize(Context var0) {
        WindowManager var1 = (WindowManager) var0.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics var2 = new DisplayMetrics();
        var1.getDefaultDisplay().getMetrics(var2);
        return new Point(var2.widthPixels, var2.heightPixels);
    }

    public static String getSelectionFontName(SODoc var0) {
        String var1 = var0.getSelectionFontName();
        String var2 = var1;
        if (var1 != null) {
            if (!var1.startsWith("-")) {
                var2 = var1;
                if (!var1.startsWith("+")) {
                    return var2;
                }
            }

            var2 = "";
        }

        return var2;
    }

    public static SODocSession.SODocSessionLoadListenerCustom getSessionLoadListener() {
        return e;
    }

    public static NUIPKCS7Signer getSigner(Activity var0) {
        SigningFactoryListener var1 = mSigningFactory;
        return var1 != null ? var1.getSigner(var0) : null;
    }

    public static String getStringPreference(Object var0, String var1, String var2) {
        SOPersistentStorage var3 = d;
        if (var3 != null) {
            return var3.getStringPreference(var0, var1, var2);
        } else {
            Log.d("Utilities", "No implementation of the SOPersistentStorage interface found");
            throw new RuntimeException();
        }
    }

    public static NUIPKCS7Verifier getVerifier(Activity var0) {
        SigningFactoryListener var1 = mSigningFactory;
        return var1 != null ? var1.getVerifier(var0) : null;
    }

    public static void hideKeyboard(Context var0) {
        if (var0 instanceof Activity) {
            Activity var1 = (Activity) var0;
            View var2 = var1.getCurrentFocus();
            if (var2 != null) {
                ((InputMethodManager) var1.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(var2.getWindowToken(), 0);
            }
        }

    }

    public static void hideKeyboard(Context var0, View var1) {
        if (var1 != null) {
            ((InputMethodManager) var0.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(var1.getWindowToken(), 0);
        }

    }

    public static int iconForDocExt(String var0) {
        byte var1;
        label143:
        {
            switch (var0.hashCode()) {
                case 97669:
                    if (var0.equals("bmp")) {
                        var1 = 23;
                        break label143;
                    }
                    break;
                case 98299:
                    if (var0.equals("cbz")) {
                        var1 = 33;
                        break label143;
                    }
                    break;
                case 98822:
                    if (var0.equals("csv")) {
                        var1 = 21;
                        break label143;
                    }
                    break;
                case 99640:
                    if (var0.equals("doc")) {
                        var1 = 0;
                        break label143;
                    }
                    break;
                case 100542:
                    if (var0.equals("emf")) {
                        var1 = 31;
                        break label143;
                    }
                    break;
                case 101110:
                    if (var0.equals("fb2")) {
                        var1 = 36;
                        break label143;
                    }
                    break;
                case 102340:
                    if (var0.equals("gif")) {
                        var1 = 26;
                        break label143;
                    }
                    break;
                case 103745:
                    if (var0.equals("hwp")) {
                        var1 = 22;
                        break label143;
                    }
                    break;
                case 105441:
                    if (var0.equals("jpg")) {
                        var1 = 24;
                        break label143;
                    }
                    break;
                case 110834:
                    if (var0.equals("pdf")) {
                        var1 = 19;
                        break label143;
                    }
                    break;
                case 111145:
                    if (var0.equals("png")) {
                        var1 = 27;
                        break label143;
                    }
                    break;
                case 111219:
                    if (var0.equals("pps")) {
                        var1 = 17;
                        break label143;
                    }
                    break;
                case 111220:
                    if (var0.equals("ppt")) {
                        var1 = 10;
                        break label143;
                    }
                    break;
                case 114276:
                    if (var0.equals("svg")) {
                        var1 = 32;
                        break label143;
                    }
                    break;
                case 114833:
                    if (var0.equals("tif")) {
                        var1 = 28;
                        break label143;
                    }
                    break;
                case 115312:
                    if (var0.equals("txt")) {
                        var1 = 20;
                        break label143;
                    }
                    break;
                case 117840:
                    if (var0.equals("wmf")) {
                        var1 = 30;
                        break label143;
                    }
                    break;
                case 118783:
                    if (var0.equals("xls")) {
                        var1 = 5;
                        break label143;
                    }
                    break;
                case 118907:
                    if (var0.equals("xps")) {
                        var1 = 35;
                        break label143;
                    }
                    break;
                case 3088949:
                    if (var0.equals("docm")) {
                        var1 = 2;
                        break label143;
                    }
                    break;
                case 3088960:
                    if (var0.equals("docx")) {
                        var1 = 1;
                        break label143;
                    }
                    break;
                case 3089476:
                    if (var0.equals("dotm")) {
                        var1 = 4;
                        break label143;
                    }
                    break;
                case 3089487:
                    if (var0.equals("dotx")) {
                        var1 = 3;
                        break label143;
                    }
                    break;
                case 3120248:
                    if (var0.equals("epub")) {
                        var1 = 34;
                        break label143;
                    }
                    break;
                case 3268712:
                    if (var0.equals("jpeg")) {
                        var1 = 25;
                        break label143;
                    }
                    break;
                case 3446968:
                    if (var0.equals("potm")) {
                        var1 = 15;
                        break label143;
                    }
                    break;
                case 3446979:
                    if (var0.equals("potx")) {
                        var1 = 14;
                        break label143;
                    }
                    break;
                case 3447898:
                    if (var0.equals("ppsm")) {
                        var1 = 18;
                        break label143;
                    }
                    break;
                case 3447909:
                    if (var0.equals("ppsx")) {
                        var1 = 16;
                        break label143;
                    }
                    break;
                case 3447929:
                    if (var0.equals("pptm")) {
                        var1 = 13;
                        break label143;
                    }
                    break;
                case 3447935:
                    if (var0.equals("ppts")) {
                        var1 = 11;
                        break label143;
                    }
                    break;
                case 3447940:
                    if (var0.equals("pptx")) {
                        var1 = 12;
                        break label143;
                    }
                    break;
                case 3559925:
                    if (var0.equals("tiff")) {
                        var1 = 29;
                        break label143;
                    }
                    break;
                case 3682382:
                    if (var0.equals("xlsm")) {
                        var1 = 7;
                        break label143;
                    }
                    break;
                case 3682393:
                    if (var0.equals("xlsx")) {
                        var1 = 6;
                        break label143;
                    }
                    break;
                case 3682413:
                    if (var0.equals("xltm")) {
                        var1 = 9;
                        break label143;
                    }
                    break;
                case 3682424:
                    if (var0.equals("xltx")) {
                        var1 = 8;
                        break label143;
                    }
                    break;
                case 114035747:
                    if (var0.equals("xhtml")) {
                        var1 = 37;
                        break label143;
                    }
            }

            var1 = -1;
        }

        switch (var1) {
            case 0:
                return drawable.sodk_editor_icon_doc;
            case 1:
            case 2:
            case 3:
            case 4:
                return drawable.sodk_editor_icon_docx;
            case 5:
                return drawable.sodk_editor_icon_xls;
            case 6:
            case 7:
            case 8:
            case 9:
                return drawable.sodk_editor_icon_xlsx;
            case 10:
            case 11:
                return drawable.sodk_editor_icon_ppt;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                return drawable.sodk_editor_icon_pptx;
            case 19:
                return drawable.sodk_editor_icon_pdf;
            case 20:
            case 21:
                return drawable.sodk_editor_icon_txt;
            case 22:
                return drawable.sodk_editor_icon_hangul;
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                return drawable.sodk_editor_icon_image;
            case 32:
                return drawable.sodk_editor_icon_svg;
            case 33:
                return drawable.sodk_editor_icon_cbz;
            case 34:
                return drawable.sodk_editor_icon_epub;
            case 35:
                return drawable.sodk_editor_icon_xps;
            case 36:
            case 37:
                return drawable.sodk_editor_icon_fb2;
            default:
                return drawable.sodk_editor_icon_any;
        }
    }

    public static int iconForDocType(String var0) {
        return iconForDocExt(com.artifex.solib.a.g(var0));
    }

    public static boolean isChromebook(Context var0) {
        return var0.getPackageManager().hasSystemFeature("org.chromium.arc.device_management");
    }

    public static boolean isDocTypeSupported(Context var0, String var1) {
        return k.b((Activity) var0, var1) ^ true;
    }

    public static boolean isEmulator() {
        if ("goldfish".equals(Build.HARDWARE)) {
            return true;
        } else if ("ranchu".equals(Build.HARDWARE)) {
            return true;
        } else {
            return Build.FINGERPRINT.contains("generic");
        }
    }

    public static boolean isLandscapePhone(Context var0) {
        if (isPhoneDevice(var0)) {
            Point var1 = getRealScreenSize(var0);
            return var1.x > var1.y;
        }

        return false;
    }

    public static boolean isPhoneDevice(Context var0) {
        Configuration var1 = var0.getResources().getConfiguration();
        int var2 = var1.screenWidthDp;
        boolean var3;
        var3 = var1.smallestScreenWidthDp < var0.getResources().getInteger(integer.sodk_editor_minimum_tablet_width);

        return var3;
    }

    public static boolean isRTL(Context var0) {
        InputMethodSubtype var1 = ((InputMethodManager) var0.getSystemService(Context.INPUT_METHOD_SERVICE)).getCurrentInputMethodSubtype();
        if (var1 == null) {
            return false;
        } else {
            String var2;
            if (VERSION.SDK_INT >= 24) {
                var2 = var1.getLanguageTag();
            } else {
                var2 = var1.getLocale();
            }

            return f.contains(var2);
        }
    }

    public static boolean isValidFilename(String var0) {
        if (var0 == null) {
            return false;
        } else if (var0.isEmpty()) {
            return false;
        } else if (var0.startsWith(".")) {
            return false;
        } else if (var0.startsWith(" ")) {
            return false;
        } else {
            return !var0.endsWith(" ");
        }
    }

    public static void passwordDialog(final Activity var0, final passwordDialogListener var1) {
        var0.runOnUiThread(() -> {
            Utilities2.dismissCurrentAlert();
            Builder var1x = new Builder(var0, style.sodk_editor_alert_dialog_style);
            View var2 = LayoutInflater.from(var0).inflate(layout.sodk_editor_password_prompt, null);
            var1x.setCancelable(false);
            final SOEditText var3 = var2.findViewById(id.editTextDialogUserInput);
            var1x.setView(var2);
            var1x.setTitle("");
            var1x.setPositiveButton(var0.getResources().getString(string.sodk_editor_ok), new OnClickListener() {
                public void onClick(DialogInterface var1x, int var2) {
                    Utilities2.hideKeyboard(var0, var3);
                    var1x.dismiss();
                    Utilities2.b = null;
                    if (var1 != null) {
                        String var3x = var3.getText().toString();
                        var1.onOK(var3x);
                    }

                }
            });
            var1x.setNegativeButton(var0.getResources().getString(string.sodk_editor_cancel), new OnClickListener() {
                public void onClick(DialogInterface var1x, int var2) {
                    Utilities2.hideKeyboard(var0, var3);
                    var1x.dismiss();
                    Utilities2.b = null;
                    if (var1 != null) {
                        var1.onCancel();
                    }

                }
            });
            Utilities2.b = var1x.create();
            Utilities2.b.show();
        });
    }

    protected static String preInsertImage(Context param0, String param1) {
        // $FF: Couldn't be decompiled
        return "";
    }

    public static String removeExtension(String var0) {
        File var1 = new File(var0);
        if (var1.isDirectory()) {
            return var0;
        } else {
            String var2 = var1.getName();
            int var3 = var2.lastIndexOf(46);
            return var3 <= 0 ? var0 : (new File(var1.getParent(), var2.substring(0, var3))).getPath();
        }
    }

    public static void removePreference(Object var0, String var1) {
        SOPersistentStorage var2 = d;
        if (var2 != null) {
            var2.removePreference(var0, var1);
        } else {
            Log.d("Utilities", "No implementation of the SOPersistentStorage interface found");
            throw new RuntimeException();
        }
    }

    public static int[] screenToWindow(int[] var0, Context var1) {
        int[] var2 = new int[2];
        ((Activity) var1).getWindow().getDecorView().getLocationOnScreen(var2);
        return new int[]{var0[0] - var2[0], var0[1] - var2[1]};
    }

    public static void setDataLeakHandlers(SODataLeakHandlers var0) {
        c = var0;
    }

    public static void setFileStateForPrint(String var0) {
        a = var0;
    }

    public static void setFilenameText(SOTextView var0, String var1) {
        Context var2 = var0.getContext();
        int var3 = var1.lastIndexOf(".");
        String var4 = a(color.sodk_editor_filename_textcolor, var2);
        String var5 = a(color.sodk_editor_extension_textcolor, var2);
        StringBuilder var6;
        if (var3 >= 0) {
            var6 = new StringBuilder();
            var6.append("<font color='");
            var6.append(var4);
            var6.append("'>");
            var6.append(var1.substring(0, var3));
            var6.append("</font><font color='");
            var6.append(var5);
            var6.append("'>");
            var6.append(var1.substring(var3));
            var6.append("</font>");
            var1 = var6.toString();
        } else {
            var6 = new StringBuilder();
            var6.append("<font color='");
            var6.append(var4);
            var6.append("'>");
            var6.append(var1);
            var6.append("</font>");
            var1 = var6.toString();
        }

        Spanned var7;
        if (VERSION.SDK_INT >= 24) {
            var7 = Html.fromHtml(var1, 0);
        } else {
            var7 = Html.fromHtml(var1);
        }

        var0.setText(var7, BufferType.SPANNABLE);
    }

    public static void setPersistentStorage(SOPersistentStorage var0) {
        d = var0;
    }

    public static void setSessionLoadListener(SODocSession.SODocSessionLoadListenerCustom var0) {
        e = var0;
    }

    public static void setSigningFactoryListener(SigningFactoryListener var0) {
        mSigningFactory = var0;
    }

    public static void setStringPreference(Object var0, String var1, String var2) {
        SOPersistentStorage var3 = d;
        if (var3 != null) {
            var3.setStringPreference(var0, var1, var2);
        } else {
            Log.d("Utilities", "No implementation of the SOPersistentStorage interface found");
            throw new RuntimeException();
        }
    }

    public static void showKeyboard(Context var0) {
        ((InputMethodManager) var0.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(2, 1);
    }


    public static void showMessage(Activity var0, String var1, String var2) {
        showMessage(var0, var1, var2, var0.getResources().getString(string.sodk_editor_ok));
    }

    public static void showMessage(final Activity var0, final String var1, final String var2, final String var3) {
        var0.runOnUiThread(new Runnable() {
            public void run() {
                Utilities2.dismissCurrentAlert();
                Utilities2.b = (new Builder(var0, style.sodk_editor_alert_dialog_style)).setTitle(var1).setMessage(var2).setCancelable(false).setPositiveButton(var3, new OnClickListener() {
                    public void onClick(DialogInterface var1x, int var2x) {
                        var1x.dismiss();
                        Utilities2.b = null;
                    }
                }).create();
                Utilities2.b.show();
            }
        });
    }

    public static void showMessageAndFinish(final Activity var0, final String var1, final String var2) {
        var0.runOnUiThread(new Runnable() {
            public void run() {
                Utilities2.dismissCurrentAlert();
                Utilities2.b = (new Builder(var0, style.sodk_editor_alert_dialog_style)).setTitle(var1).setMessage(var2).setCancelable(false).setPositiveButton(string.sodk_editor_ok, new OnClickListener() {
                    public void onClick(DialogInterface var1x, int var2x) {
                        var1x.dismiss();
                        Utilities2.b = null;
                        var0.finish();
                    }
                }).create();
                Utilities2.b.show();
            }
        });
    }

    public static void showMessageAndWait(final Activity var0, final String var1, final String var2, final Runnable var3) {
        var0.runOnUiThread(new Runnable() {
            public void run() {
                Utilities2.dismissCurrentAlert();
                Utilities2.b = (new Builder(var0, style.sodk_editor_alert_dialog_style)).setTitle(var1).setMessage(var2).setCancelable(false).setPositiveButton(string.sodk_editor_ok, new OnClickListener() {
                    public void onClick(DialogInterface var1x, int var2x) {
                        var1x.dismiss();
                        Utilities2.b = null;
                        var3.run();
                    }
                }).create();
                Utilities2.b.show();
            }
        });
    }

    public static void yesNoMessage(final Activity var0, final String var1, final String var2, final String var3, final String var4, final Runnable var5, final Runnable var6) {
        var0.runOnUiThread(new Runnable() {
            public void run() {
                Utilities2.dismissCurrentAlert();
                Builder var1x = new Builder(var0, style.sodk_editor_alert_dialog_style);
                var1x.setTitle(var1);
                var1x.setMessage(var2);
                var1x.setCancelable(false);
                var1x.setPositiveButton(var3, new OnClickListener() {
                    public void onClick(DialogInterface var1x, int var2x) {
                        var1x.dismiss();
                        Utilities2.b = null;
                        if (var5 != null) {
                            var5.run();
                        }

                    }
                });
                var1x.setNegativeButton(var4, new OnClickListener() {
                    public void onClick(DialogInterface var1x, int var2x) {
                        var1x.dismiss();
                        Utilities2.b = null;
                        if (var6 != null) {
                            var6.run();
                        }

                    }
                });
                Utilities2.b = var1x.create();
                Utilities2.b.show();
            }
        });
    }

    public interface SigningFactoryListener {
        NUIPKCS7Signer getSigner(Activity var1);

        NUIPKCS7Verifier getVerifier(Activity var1);
    }

    public interface passwordDialogListener {
        void onCancel();

        void onOK(String var1);
    }
}
