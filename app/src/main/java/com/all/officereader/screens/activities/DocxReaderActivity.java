package com.all.officereader.screens.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.all.officereader.R;
import com.artifex.sonui.AppNUIActivity;
import com.artifex.sonui.interfaces.SaveAndAdsListener;

public class DocxReaderActivity extends AppNUIActivity {
    @SuppressLint("IntentReset")
    public static void start(Context context, String path, Uri data) {
//        Intent starter = new Intent(context, DocReaderActivity.class);
//        starter.putExtra(INTENT_FILED_FILE_PATH, path);
//        starter.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        starter.setDataAndType(data, type);
//        context.startActivity(starter);

//        Uri fromFile = Uri.fromFile(mFile);
        Intent intent = new Intent(context, DocxReaderActivity.class);
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setData(data);
        intent.putExtra("STARTED_FROM_EXPLORER", true);
        intent.putExtra("START_PAGE", 0);
        intent.putExtra("PATH", path);
        ((Activity) context).startActivityForResult(intent, 14);

    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    @Override
    protected void onCreate(Bundle var1) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.saveAndAdsListener = new SaveAndAdsListener() {
            @Override
            public void onDone() {
                DocxReaderActivity.this.getNUIDocView().saveSuccess();
            }

            @Override
            public void onError() {
            }
        };
        super.onCreate(var1);
        String path = getIntent().getStringExtra("PATH");

//        ImageView expand = findViewById(R.id.expand_edit_bar);
//        ViewAnimator viewAnimator = findViewById(R.id.switcher);
        RelativeLayout normalToolbar = findViewById(R.id.normalToolbar);
        ConstraintLayout viewToolbarEdit = findViewById(R.id.viewToolbarEdit);
        findViewById(R.id.cancel_edit).setOnClickListener(v -> {
            normalToolbar.setVisibility(View.VISIBLE);
//            viewAnimator.setVisibility(View.GONE);
            viewToolbarEdit.setVisibility(View.GONE);
        });
    }

    @Override
    public void onNewIntent(Intent var1) {
        super.onNewIntent(var1);
        startActivity(var1);
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
