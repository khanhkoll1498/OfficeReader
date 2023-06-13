package com.artifex.sonui.dialog;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.all.officereader.R;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.interfaces.OnActionCallback;

import java.util.ArrayList;
import java.util.List;

public class SaveEditDialog extends BaseActivity {

    LinearLayout bigLayout;
    TextView textViewDone;
    TextView textViewCancel;
    TextView continueEdit;
    private static OnActionCallback callback;
    private List<String> titleAndText;


    public static void start(Context context, OnActionCallback onActionCallback) {
        callback = onActionCallback;
        Intent starter = new Intent(context, SaveEditDialog.class);
        context.startActivity(starter);
    }
    public static void start(Context context, OnActionCallback onActionCallback, List<String> titleAndText) {
        callback = onActionCallback;

        Intent starter = new Intent(context, SaveEditDialog.class);
        starter.putStringArrayListExtra("titleAndText", (ArrayList<String>) titleAndText);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_save_edit);
        initView();
        addEvent();
    }

    private void addEvent() {
        textViewDone.setOnClickListener(v -> {
            if (callback != null) callback.callback("Done");
            finish();
        });
        textViewCancel.setOnClickListener(v ->{
            if (callback != null){
                callback.callback("Cancel");
            }
            finish();
        });
        continueEdit.setOnClickListener(v -> {
            if (callback != null){
                callback.callback("Continue");
            }
            finish();
        });
        bigLayout.setOnClickListener(v -> {
            finish();
        });
    }

    private void initView() {
        titleAndText = getIntent().getStringArrayListExtra("titleAndText");
        bigLayout = findViewById(R.id.big_layout);
        textViewDone = findViewById(R.id.tv_done);
        textViewCancel = findViewById(R.id.tv_cancel);
        continueEdit = findViewById(R.id.continueEdit);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        callback = null;
    }
}
