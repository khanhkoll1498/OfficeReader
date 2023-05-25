package com.all.officereader.helpers.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.all.officereader.model.FileListItem;
import com.all.officereader.screens.activities.MainActivity;
import com.all.officereader.screens.activities.ViewEditorActivity;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FileUtility {
    public static final String TAG = FileUtility.class.getName();

    public static ArrayList<File> mAllFileOffice = new ArrayList<>();
    public static ArrayList<File> mWordFiles = new ArrayList<>();
    public static ArrayList<File> mSearchFile = new ArrayList<>();
    public static ArrayList<File> mPdfFiles = new ArrayList<>();
    public static ArrayList<File> mExcelFiles = new ArrayList<>();
    public static ArrayList<File> mPowerPointFiles = new ArrayList<>();
    public static ArrayList<File> mListTxtFile = new ArrayList<>();

    public static File mDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

    public static String mPath = "cacheDoc/";
    public static File mDirCache = new File(mDir + "/" + mPath);

    /*public static void createFileNoMedia(Context context) {
        String path = mDir + "/" + mPath;
        try {
            File root = new File(path);
            if (!root.exists()) {
                root.mkdirs();
                root.createNewFile();

                FileUtility.copyFile(mAllFileOffice);
                Log.e(TAG, "createFileNoMedia: Suceess");
            } else {
                Log.e(TAG, "createFileNoMedia: fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*public static void copyFile(ArrayList<File> arrFile) {
        String newPath = mDir + "/" + mPath;
        try {
            for (File item : arrFile) {
                String newPathFile = newPath + item.getName();
                File file = new File(newPathFile);
                copyFile(item, file);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*private static void copyFile(File src, File dst) {
        if (dst.exists()) {
            deleteFile(dst);
        }
        try {
            try (InputStream in = new FileInputStream(src)) {
                OutputStream out = new FileOutputStream(dst);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                } finally {
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*public static void deleteFile(File file) {
        try {
            if (file != null) {
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*public static void deleteAllFile(String path) {
        try {
            File dir = new File(path);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (String child : children) {
                    new File(dir, child).delete();
                }
            }
            dir.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static Observable getAllFileLocal() {
        return Observable.create((ObservableOnSubscribe<ArrayList<File>>) emitter -> {
            mAllFileOffice.clear();
            mWordFiles.clear();
            mPdfFiles.clear();
            mExcelFiles.clear();
            mPowerPointFiles.clear();
            mListTxtFile.clear();
            emitter.onNext(getAllFile(mDir));
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable searchFile(final File dir, final String text) {
        return Observable.create(new ObservableOnSubscribe<ArrayList<File>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<File>> emitter) {
                if (mSearchFile != null && mSearchFile.size() > 0) {
                    mSearchFile.clear();
                }
                emitter.onNext(search(dir, text));
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static ArrayList<File> getAllFile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    Log.e(TAG, "getAllFile: " + i + listFile[i].getName());
                    getAllFile(listFile[i]);

                } else {
                    String name = listFile[i].getName();
                    if (name.endsWith(".docb") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".dotx") ||
                            name.endsWith(".xls") || name.endsWith(".xlsx") || name.endsWith(".xlt") || name.endsWith(".xlm") || name.endsWith(".xlsb") ||
                            name.endsWith(".ppt") || name.endsWith(".pptx") || name.endsWith(".pdf")
                            || name.endsWith(".txt")) {
                        mAllFileOffice.add(listFile[i]);

                        if (listFile[i].getName().endsWith(".pdf")) {
                            mPdfFiles.add(listFile[i]);

                        } else if (listFile[i].getName().endsWith(".xls") || listFile[i].getName().endsWith(".xlsx")) {
                            mExcelFiles.add(listFile[i]);

                        } else if (listFile[i].getName().endsWith(".ppt") || listFile[i].getName().endsWith(".pptx")) {
                            mPowerPointFiles.add(listFile[i]);

                        } else if (listFile[i].getName().endsWith(".docb") || listFile[i].getName().endsWith(".docx")
                                || listFile[i].getName().endsWith(".doc") || listFile[i].getName().endsWith(".dotx")) {
                            mWordFiles.add(listFile[i]);

                        } else if (listFile[i].getName().endsWith(".txt")) {
                            mListTxtFile.add(listFile[i]);
                        }
                    }
                }
            }
        }
        return mAllFileOffice;
    }

    public static ArrayList<File> search(File dir, String text) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    search(listFile[i], text.toLowerCase());

                } else {
                    if (listFile[i].getName().toLowerCase().contains(text.toLowerCase())) {
                        String name = listFile[i].getName();
                        if (name.endsWith(".docb") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".dotx") ||
                                name.endsWith(".xls") || name.endsWith(".xlsx") || name.endsWith(".xlt") || name.endsWith(".xlm") || name.endsWith(".xlsb") ||
                                name.endsWith(".ppt") || name.endsWith(".pptx") || name.endsWith(".pdf") || name.endsWith(".txt")) {
                            mSearchFile.add(listFile[i]);
                        }
                    }
                }
            }
        }
        return mSearchFile;
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static int fileType(File file) {
        if (file.isDirectory()) {
            return 1;
        } else {
            String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            int type;
            switch (suffix) {
                case "mp3":
                    type = 2;
                    break;
                case "wav":
                    type = 2;
                    break;
                case "flac":
                    type = 2;
                    break;
                case "ape":
                    type = 2;
                    break;

                case "java":
                    type = 4;
                    break;
                case "html":
                    type = 4;
                    break;
                case "js":
                    type = 4;
                    break;
                case "css":
                    type = 4;
                    break;
                case "json":
                    type = 4;
                    break;
                case "xml":
                    type = 4;
                    break;

                case "xlsx":
                    type = 5;
                    break;
                case "xls":
                    type = 5;
                    break;

                case "png":
                    type = 6;
                    break;
                case "jpg":
                    type = 6;
                    break;
                case "gif":
                    type = 6;
                    break;
                case "bmp":
                    type = 6;
                    break;

                case "pdf":
                    type = 7;
                    break;

                case "ppt":
                    type = 8;
                    break;
                case "pptx":
                    type = 8;
                    break;

                case "txt":
                    type = 9;
                    break;

                case "mp4":
                    type = 10;
                    break;
                case "flv":
                    type = 10;
                    break;
                case "avi":
                    type = 10;
                    break;
                case "3gp":
                    type = 10;
                    break;
                case "mkv":
                    type = 10;
                    break;
                case "rmvb":
                    type = 10;
                    break;
                case "wmv":
                    type = 10;
                    break;

                case "doc":
                    type = 11;
                    break;
                case "docx":
                    type = 11;
                    break;

                case "rar":
                    type = 12;
                    break;
                case "zip":
                    type = 12;
                    break;

                default:
                    type = 3;
                    break;
            }
            return type;
        }
    }


    public static int fileType(FileListItem file) {
        if (file.isDirectory()) {
            return 1;
        } else {
            String suffix = file.getFilename().substring(file.getFilename().lastIndexOf(".") + 1);
            int type;
            switch (suffix) {
                case "mp3":
                    type = 2;
                    break;
                case "wav":
                    type = 2;
                    break;
                case "flac":
                    type = 2;
                    break;
                case "ape":
                    type = 2;
                    break;

                case "java":
                    type = 4;
                    break;
                case "html":
                    type = 4;
                    break;
                case "js":
                    type = 4;
                    break;
                case "css":
                    type = 4;
                    break;
                case "json":
                    type = 4;
                    break;
                case "xml":
                    type = 4;
                    break;

                case "xlsx":
                    type = 5;
                    break;
                case "xls":
                    type = 5;
                    break;

                case "png":
                    type = 6;
                    break;
                case "jpg":
                    type = 6;
                    break;
                case "gif":
                    type = 6;
                    break;
                case "bmp":
                    type = 6;
                    break;

                case "pdf":
                    type = 7;
                    break;

                case "ppt":
                    type = 8;
                    break;
                case "pptx":
                    type = 8;
                    break;

                case "txt":
                    type = 9;
                    break;

                case "mp4":
                    type = 10;
                    break;
                case "flv":
                    type = 10;
                    break;
                case "avi":
                    type = 10;
                    break;
                case "3gp":
                    type = 10;
                    break;
                case "mkv":
                    type = 10;
                    break;
                case "rmvb":
                    type = 10;
                    break;
                case "wmv":
                    type = 10;
                    break;

                case "doc":
                    type = 11;
                    break;
                case "docx":
                    type = 11;
                    break;

                case "rar":
                    type = 12;
                    break;
                case "zip":
                    type = 12;
                    break;

                default:
                    type = 3;
                    break;
            }
            return type;
        }
    }

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    public static void closeKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static long getAllFileSize(String Path) {
        long size = 0;
        ArrayList<FileListItem> arrFile = null;
        try {
            arrFile = new ArrayList<>();
            File file = new File(Path);
            File[] files = file.listFiles();
            try {
                for (File f : files) {
                    String name = f.getName();

                    if (name.charAt(0) == '.' || name.replace(" ", "").equals("")) {
                        continue;
                    }
                    size += f.length();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
        DecimalFormat fileDecimalFormat = new DecimalFormat("#0.#");
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString;
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "MB";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "GB";
        }
        return fileSizeString;
    }

    public static void openFile(Activity activity, File mFile, int pageNumber) {
        StorageUtils storageUtils = new StorageUtils(activity);
        storageUtils.addRecent(activity, mFile);

        Uri fromFile = Uri.fromFile(mFile);
        Intent intent = new Intent(activity, ViewEditorActivity.class);
        intent.setAction("android.intent.action.VIEW");
        intent.setData(fromFile);
        intent.putExtra("STARTED_FROM_EXPLORER", true);
        intent.putExtra("START_PAGE", pageNumber);
        activity.startActivityForResult(intent, MainActivity.CODE_RESULT_BOOKMARK);
    }
}
