package com.all.officereader.screens.ocr.general;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.SparseArray;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.all.officereader.screens.ocr.EditOCRActivity;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.isseiaoki.simplecropview.util.Logger;
import com.all.officereader.R;
import com.all.officereader.screens.activities.MainActivity;
import com.all.officereader.screens.ocr.popups.ExportDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public class ControlUtils {

    private static final String SubscriptionUtil = "SubscriptionUtil";
    private static final String CONSTANT_REMOVE_ADS = "CONSTANT_REMOVE_ADS";

    public static int isBuyRMAds(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences(SubscriptionUtil, Context.MODE_PRIVATE);
        return preferences.getInt(CONSTANT_REMOVE_ADS, -1);
    }

    public static void setBuyRMAds(Context mContext, int isPurchase) {
        SharedPreferences preferences = mContext.getSharedPreferences(SubscriptionUtil, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CONSTANT_REMOVE_ADS, isPurchase);
        editor.apply();
    }

    public static String saveImage(Activity activity, byte[] bArr) {
        String baseImageFolder = getBaseImageFolder(activity);
        File file = new File(baseImageFolder, System.currentTimeMillis() + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public static String getBaseImageFolder(Context context) {
        StringBuilder sb = new StringBuilder();
        String str = null;
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            str = externalFilesDir.getPath();
        }
        sb.append(str);
        sb.append(File.separator);
        sb.append("images");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void closeKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void shareText(Context context, String text) {
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
            sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(sharingIntent, "Share to"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyToClipboard(Activity context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
    }

    public static void funcCreateFileOCR(Activity context, String fileName, ArrayList<String> listPareOCR) {
        StringBuilder stringBuilder = new StringBuilder();
        ExportDialog exportDialog = new ExportDialog(context, fileName);
        exportDialog.setOnExportListener(new ExportDialog.onExportListener() {
            @Override
            public void onExport() {
                for (int i = 0; i < listPareOCR.size(); i++) {
                    stringBuilder.append(listPareOCR.get(i));
                }
                if (exportDialog.edtNameFile != null) {
                    generateNoteOnSD(context, exportDialog.edtNameFile.getText().toString(), stringBuilder.toString());
                } else {
                    generateNoteOnSD(context, fileName, stringBuilder.toString());
                }
            }
        });
        exportDialog.show();
    }

    public static void generateNoteOnSD(Activity context, String sFileName, String sBody) {
        try {
            File root;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "documents");

            } else {
                String path = context.getExternalFilesDir(null).getPath() + File.separator + "documents";
                root = new File(path);
            }

            if (!root.exists()) {
                root.mkdirs();
            }
            File fileWrite = new File(root, sFileName);
            FileWriter writer = new FileWriter(fileWrite);
            writer.append(sBody);
            writer.flush();
            writer.close();
            if (EditOCRActivity.getInstance() != null) {
                EditOCRActivity.getInstance().sendBroadcast(new Intent("CREATE_TXT"));
                Toast.makeText(EditOCRActivity.getInstance(), "Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditOCRActivity.getInstance(), MainActivity.class);
                intent.putExtra("CREATE_TXT", "true");
                EditOCRActivity.getInstance().startActivity(intent);
                EditOCRActivity.getInstance().finish();
            }
        } catch (IOException e) {
            Toast.makeText(context, "Create File Error!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static String inspectFromBitmap(Context context, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        TextRecognizer build = new TextRecognizer.Builder(context).build();
        if (bitmap == null) {
            String string = context.getString(R.string.cannot_reg_text);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.cannot_reg_text)");
            return string;
        }
        try {
            if (!build.isOperational()) {
                return "Text recognizer could not be set up on your device";
            }
            SparseArray<TextBlock> detect = build.detect(new Frame.Builder().setBitmap(bitmap).build());
            List<TextBlock> arrayList = new ArrayList<>();
            int i = 0;
            int size = detect.size();
            if (size > 0) {
                while (true) {
                    int i2 = i + 1;
                    TextBlock valueAt = detect.valueAt(i);
                    Intrinsics.checkNotNullExpressionValue(valueAt, "textBlock");
                    arrayList.add(valueAt);
                    if (i2 >= size) {
                        break;
                    }
                    i = i2;
                }
            }
            //Collections.sort(arrayList);
            StringBuilder sb = new StringBuilder();
            for (TextBlock value : arrayList) {
                sb.append(value.getValue());
                sb.append("\n");
            }
            build.release();
            String sb2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(sb2, "detectedText.toString()");
            return sb2;
        } catch (Exception e) {
            build.release();
            e.printStackTrace();
            String string2 = context.getString(R.string.cannot_reg_text);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.cannot_reg_text)");
            return string2;
        }
    }

    public static Uri createNewUri(Context context, Bitmap.CompressFormat format) {
        long currentTimeMillis = System.currentTimeMillis();
        Date today = new Date(currentTimeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String title = dateFormat.format(today);
        String dirPath = getDirPath();
        String fileName = "scv" + title + "." + getMimeType(format);
        String path = dirPath + "/" + fileName;
        File file = new File(path);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + getMimeType(format));
        values.put(MediaStore.Images.Media.DATA, path);
        long time = currentTimeMillis / 1000;
        values.put(MediaStore.MediaColumns.DATE_ADDED, time);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, time);
        if (file.exists()) {
            values.put(MediaStore.Images.Media.SIZE, file.length());
        }

        ContentResolver resolver = context.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Logger.i("SaveUri = " + uri);
        return uri;
    }

    public static String getDirPath() {
        String dirPath = "";
        File imageDir = null;
        File extStorageDir = Environment.getExternalStorageDirectory();
        if (extStorageDir.canWrite()) {
            imageDir = new File(extStorageDir.getPath() + "/Offices");
        }
        if (imageDir != null) {
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
            if (imageDir.canWrite()) {
                dirPath = imageDir.getPath();
            }
        }
        return dirPath;
    }

    public static String getMimeType(Bitmap.CompressFormat format) {
        switch (format) {
            case JPEG:
                return "jpeg";
            case PNG:
                return "png";
        }
        return "png";
    }
}
