package com.all.officereader.screens.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.all.officereader.R;


public class EnableInternetDialog extends Dialog implements View.OnClickListener {
    public static final String TAG = EnableInternetDialog.class.getName();
    public EnableInternetDialog mInstance;
    private Context mContext;
    private TextView txvDialogInternetTitle;
    private TextView txvDialogInternetContent;
    private TextView btnDialogInternetCancel;
    private TextView btnDialogInternetSetting;
    private enableInternetListener mEnableInternetListener;

    public EnableInternetDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }
    public EnableInternetDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    public void setEnableInternetListener(enableInternetListener enableInternetListener) {
        mEnableInternetListener = enableInternetListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.dialog_enable_internet);

        findViews();
    }

    private void findViews() {
        txvDialogInternetTitle = findViewById( R.id.txv_dialog_internet_title );
        txvDialogInternetContent = findViewById( R.id.txv_dialog_internet_content );
        btnDialogInternetCancel = findViewById( R.id.btn_dialog_internet_cancel );
        btnDialogInternetSetting = findViewById( R.id.btn_dialog_internet_setting );

        btnDialogInternetCancel.setOnClickListener( this );
        btnDialogInternetSetting.setOnClickListener( this );
    }

    public static void showDialog(Context context){
        EnableInternetDialog enableInternetDialog = new EnableInternetDialog(context);
        enableInternetDialog.show();
    }


    @Override
    public void onClick(View v) {
        if ( v == btnDialogInternetCancel ) {
            // Handle clicks for btnDialogInternetCancel
            dismiss();
            if (mEnableInternetListener != null) {
                mEnableInternetListener.onDismiss();
            }
        } else if ( v == btnDialogInternetSetting ) {
            //Tracking
            dismiss();
            //end tracking
            if (mContext != null) {
                mContext.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        }
    }

    public interface enableInternetListener{
        void onDismiss();
    }
}