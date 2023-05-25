package com.all.officereader.screens.dialogs;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.all.officereader.R;
import com.all.officereader.helpers.bases.BaseDialog;
import com.all.officereader.helpers.utils.AppUtil;

public class FeedbackDialog extends BaseDialog implements View.OnClickListener {
    private Context mContext;
    private TextView txvDialogRateFeedbackTitle;
    private TextView txvDialogRateContent;
    private EditText edtDialogRateFeedbackTitle;
    private TextView btnDialogFeedbackSend;
    private TextView tvCancelDialog;

    public FeedbackDialog(Context context) {
        super(context);
        mContext = context.getApplicationContext();
    }

    @Override
    protected void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.dialog_rate_feedback);
        funcStyle();
        findViews();
    }

    private void funcStyle() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setWindowAnimations(R.style.anim_open_dialog);
        setCancelable(true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected void initData() {
    }

    private void findViews() {
        txvDialogRateFeedbackTitle = findViewById(R.id.txv_dialog_rate_feedback_title);
        txvDialogRateContent = findViewById(R.id.txv_dialog_rate_content);
        edtDialogRateFeedbackTitle = findViewById(R.id.edt_dialog_rate_feedback_title);
        tvCancelDialog = findViewById(R.id.tv_cancel);
        btnDialogFeedbackSend = findViewById(R.id.btn_dialog_feedback_send);

        btnDialogFeedbackSend.setOnClickListener(this);
        tvCancelDialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDialogFeedbackSend) {
            if (edtDialogRateFeedbackTitle.getText().toString().isEmpty()) {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.fill_feedback), Toast.LENGTH_LONG).show();
            } else {
                PackageInfo pInfo = null;
                try {
                    pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                int currentAppVersionCode = 0;
                if (pInfo != null) {
                    currentAppVersionCode = pInfo.versionCode;
                }
                AppUtil.sendFeedBack(mContext, "@gmail.com", edtDialogRateFeedbackTitle.getText().toString() + mContext.getResources().getString(R.string.rate_content_sign) + currentAppVersionCode);
                Toast.makeText(mContext, mContext.getResources().getString(R.string.thank_share), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        } else if (v == tvCancelDialog) {
            dismiss();
        }
    }

}
