package com.all.officereader.helpers.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.all.officereader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StorageUtils {
    private final String PREFS_NAME = "docreader";
    private Context context;
    public static String fileTypeWord = "word";
    public static String fileTypePowerPoin = "powerpoin";
    public static String RECENT = "last_open";
    public static String BOOKMARK = "bookmark";

    public StorageUtils(Context context) {
        this.context = context;
    }

    public void saveRecent(Context context, List<File> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(RECENT, jsonFavorites);

        editor.commit();
    }

    public void addRecent(Context context, File product) {
        boolean isExistFile = false;
        List<File> favorites = new ArrayList<>();
        favorites = getRecent(context);
        if (favorites == null) {
            favorites = new ArrayList<File>();
            favorites.add(product);
            saveRecent(context, favorites);
        } else if (favorites.size() == 0) {
            favorites.add(product);
            saveRecent(context, favorites);
        } else if (favorites.size() > 0) {
            for (int i = 0; i < favorites.size(); i++) {
                if (product.getAbsolutePath().equals(favorites.get(i).getAbsolutePath())) {
                    isExistFile = true;
                }
            }
            if (!isExistFile) {
                favorites.add(0, product);
                saveRecent(context, favorites);
                Log.e("xxx", "addRecent: ");
            } else {
                favorites.remove(product);
                favorites.add(0, product);
                saveRecent(context, favorites);
                Log.e("xxx", "addRecent:exist ");
            }
        }

    }

    public void removeRecent(Context context, File product) {
        ArrayList<File> favorites = getRecent(context);
        if (favorites != null) {
            for (int i = 0; i < favorites.size(); i++) {
                if (product.getAbsolutePath().equals(favorites.get(i).getAbsolutePath())) {
                    favorites.remove(i);
                }
            }

            saveRecent(context, favorites);
        }

    }

    public ArrayList<File> getRecent(Context context) {
        SharedPreferences settings;
        List<File> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(RECENT)) {
            String jsonFavorites = settings.getString(RECENT, null);
            Gson gson = new Gson();
            File[] favoriteItems = gson.fromJson(jsonFavorites,
                    File[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<File>(favorites);
        } else
            return null;

        return (ArrayList<File>) favorites;
    }

    public void saveBookmark(Context context, List<File> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(BOOKMARK, jsonFavorites);

        editor.commit();
    }

    public void addBookmark(Context context, File product) {

        boolean isExistFile = false;
        List<File> favorites = new ArrayList<>();
        favorites = getBookmark(context);
        if (favorites == null) {
            favorites = new ArrayList<File>();
            favorites.add(product);
            saveBookmark(context, favorites);
            Toast.makeText(context, context.getResources().getString(R.string.toast_added_bookmark), Toast.LENGTH_SHORT).show();
        } else if (favorites.size() == 0) {
            favorites.add(product);
            saveBookmark(context, favorites);
            Toast.makeText(context, context.getResources().getString(R.string.toast_added_bookmark), Toast.LENGTH_SHORT).show();
        } else if (favorites.size() > 0) {
            for (int i = 0; i < favorites.size(); i++) {
                if (product.getAbsolutePath().equals(favorites.get(i).getAbsolutePath())) {
                    isExistFile = true;
                }
            }
            if (!isExistFile) {
                favorites.add(0, product);
                saveBookmark(context, favorites);
                Log.e("xxx", "addBookmark: ");
                Toast.makeText(context, context.getResources().getString(R.string.toast_added_bookmark), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.toast_file_added), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void removeBookmark(Context context, File product) {
        ArrayList<File> favorites = getBookmark(context);
        if (favorites != null) {
            for (int i = 0; i < favorites.size(); i++) {
                if (product.getAbsolutePath().equals(favorites.get(i).getAbsolutePath())) {
                    favorites.remove(i);
                    Toast.makeText(context, context.getResources().getString(R.string.toast_removed_file), Toast.LENGTH_SHORT).show();
                }
            }

            saveBookmark(context, favorites);
        }

    }

    public ArrayList<File> getBookmark(Context context) {
        SharedPreferences settings;
        List<File> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(BOOKMARK)) {
            String jsonFavorites = settings.getString(BOOKMARK, null);
            Gson gson = new Gson();
            File[] favoriteItems = gson.fromJson(jsonFavorites,
                    File[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<File>(favorites);
        } else
            return null;

        return (ArrayList<File>) favorites;
    }

}


