package com.all.officereader.screens.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.all.officereader.R;

public class ScanFileSuccessDialog extends Dialog {
    private final Activity mContext;
    private Button btn_dialog_scan_success_ok;
    private scanFileSuccessListener mScanFileSuccessListener;

    public ScanFileSuccessDialog(Activity context, scanFileSuccessListener scanFileSuccessListener) {
        super(context);
        this.mContext = context;
        mScanFileSuccessListener = scanFileSuccessListener;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_scan_file_success);

        initWindow();
        initEvents();
    }

    public void initWindow() {
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    private void initEvents() {
        btn_dialog_scan_success_ok = findViewById(R.id.btn_dialog_scan_success_ok);

        btn_dialog_scan_success_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dismiss();
                mScanFileSuccessListener.onScanFileSuccess();
            }
        });
    }

    public interface scanFileSuccessListener{
        void onScanFileSuccess();
    }
}
