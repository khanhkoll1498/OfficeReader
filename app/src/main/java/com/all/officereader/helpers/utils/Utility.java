package com.all.officereader.helpers.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.all.officereader.model.FileListItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Utility {

    public static boolean checkStorageAccessPermissions(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String permission = "android.permission.READ_EXTERNAL_STORAGE";
            int res = context.checkCallingOrSelfPermission(permission);
            return (res == PackageManager.PERMISSION_GRANTED);
        } else {
            return true;
        }
    }

    public static ArrayList<FileListItem> prepareFileListEntries(ArrayList<FileListItem> internalList, File inter, ExtensionFilter filter) {
        try {
            for (File name : inter.listFiles(filter)) {
                if (name.canRead()) {
                    if (!name.getName().startsWith(".")
                            && !name.getName().contains(".apk")
                            && !name.getName().contains(".mp4")
                            && !name.getName().contains(".mp3")
                    ) {
                        FileListItem item = new FileListItem();
                        item.setFilename(name.getName());
                        item.setDirectory(name.isDirectory());
                        item.setLocation(name.getAbsolutePath());
                        item.setTime(name.lastModified());
                        internalList.add(item);
                    }
                }
            }
            Collections.sort(internalList);
        } catch (NullPointerException e) {
            e.printStackTrace();
            internalList = new ArrayList<>();
        }
        return internalList;
    }

    public static void setUpDialog(Dialog dialog, Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int) (displayMetrics.widthPixels * 99.9);
        lp.height = (int) (displayMetrics.heightPixels * .10);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout((int) (displayMetrics.widthPixels * 99.9), (int) (displayMetrics.heightPixels * .10));
    }

}
