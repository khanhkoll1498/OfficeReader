package com.all.officereader.screens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.artifex.sonui.AppNUIActivity;
import com.all.officereader.R;
import com.all.officereader.helpers.utils.StorageUtils;
import com.all.officereader.helpers.utils.ToastUtil;

import java.io.File;

public class ViewEditorActivity extends AppNUIActivity {
    private static final String TAG = ViewEditorActivity.class.getName();
    public static final int REQUEST_CAMERA_PERMISSIONS = 0x11111;
    private ImageView imvActivityEditorBookmark;
    private LottieAnimationView imvActivityEditorAds;
    private ImageView imvBack;
    private boolean isClickBookmark;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        imvActivityEditorBookmark = findViewById(R.id.imv_activity_editor_bookmark);
        imvActivityEditorAds = findViewById(R.id.imv_activity_editor__ads);
        imvBack = findViewById(R.id.imv_back);

        Intent intent = getIntent();
        intent.getData();
        StorageUtils storageUtils = new StorageUtils(this);
        boolean isCheckFavorite = false;
        if (intent.getData() != null && intent.getData().getPath() != null && !intent.getData().getPath().equals("")) {
            if (storageUtils.getBookmark(this) != null && storageUtils.getBookmark(this).size() > 0) {
                for (int i = 0; i < storageUtils.getBookmark(this).size(); i++) {
                    if (storageUtils.getBookmark(this).get(i).getPath().equals(intent.getData().getPath())) {
                        imvActivityEditorBookmark.setImageResource(R.drawable.ic_bookmark_file_selected);
                        isCheckFavorite = true;
                    }
                }
            }
        }

        final boolean[] finalIsCheckFavorite = {isCheckFavorite};

        if (imvActivityEditorBookmark != null)
            imvActivityEditorBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isClickBookmark = true;
                    setResult(RESULT_OK, getIntent());
                    if (intent.getData() != null && intent.getData().getPath() != null && !intent.getData().getPath().equals("")) {
                        StorageUtils storageUtils = new StorageUtils(ViewEditorActivity.this);
                        ToastUtil.showToast(ViewEditorActivity.this, "Add Bookmark!");

                        if (finalIsCheckFavorite[0]) {
                            imvActivityEditorBookmark.setImageResource(R.drawable.ic_bookmark_file_normal);
                            storageUtils.removeBookmark(ViewEditorActivity.this, new File(intent.getData().getPath()));
                            finalIsCheckFavorite[0] = false;
                        } else {
                            imvActivityEditorBookmark.setImageResource(R.drawable.ic_bookmark_file_selected);
                            storageUtils.addBookmark(ViewEditorActivity.this, new File(intent.getData().getPath()));
                            finalIsCheckFavorite[0] = true;
                        }
                    }
                }
            });

        if (imvBack != null)
            imvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        if (imvActivityEditorAds != null) {
            imvActivityEditorAds.setAnimation("newGift.json");
            imvActivityEditorAds.playAnimation();
            try {
                imvActivityEditorAds.loop(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int i, int i1, Intent intent) {
        if (i == REQUEST_CAMERA_PERMISSIONS) {
            if (i1 == RESULT_OK) {
                Toast.makeText(this, "Ready!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(i, i1, intent);
    }
}
