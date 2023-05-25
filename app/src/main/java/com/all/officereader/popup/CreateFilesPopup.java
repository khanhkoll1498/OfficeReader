package com.all.officereader.popup;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.all.officereader.R;
import com.all.officereader.screens.activities.CreateFileActivity;
import com.realpacific.clickshrinkeffect.ClickShrinkEffectKt;

public class CreateFilesPopup extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View mRootPopup;
    private RelativeLayout rllCreateFileRoot;
    private LinearLayout lnlImvPopupCreateFileFile;
    private RelativeLayout lnlItemMoreAppCreateFileRoot;
    private LinearLayout lnTemplate;
    private LinearLayout lnNewFile;
    private LinearLayout lnlImvPopupCreateFileAds;
    private ImageView imvItemMoreAppCreateFileIcon;
    private TextView txvItemMoreAppCreateFileName;
    private TextView txvItemMoreAppCreateFileRate;
    private TextView txvItemMoreAppCreateFileDownloaded;
    private TextView btnItemMoreAppCreateFileInstall;
    public static final String RESULT = "CREATE_FILE";

    public CreateFilesPopup(Context context) {
        mContext = context;

        mRootPopup = LayoutInflater.from(context).inflate(R.layout.create_files_popup, null);
        setContentView(mRootPopup);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(false);
        getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        setAnimationStyle(R.style.PopupAnimationBottom);

        rllCreateFileRoot = mRootPopup.findViewById(R.id.rll_create_file_root);
        lnlImvPopupCreateFileFile = mRootPopup.findViewById(R.id.lnl_imv_popup_create_file__file);
        lnlItemMoreAppCreateFileRoot = mRootPopup.findViewById(R.id.lnl_item_more_app_create_file_root);
        lnTemplate = mRootPopup.findViewById(R.id.ln_template_popup);
        lnNewFile = mRootPopup.findViewById(R.id.ln_create_file_popup);
        lnlImvPopupCreateFileAds = mRootPopup.findViewById(R.id.lnl_imv_popup_create_file__ads);
        imvItemMoreAppCreateFileIcon = mRootPopup.findViewById(R.id.imv_item_more_app_create_file_icon);
        txvItemMoreAppCreateFileName = mRootPopup.findViewById(R.id.txv_item_more_app_create_file_name);
        txvItemMoreAppCreateFileRate = mRootPopup.findViewById(R.id.txv_item_more_app_create_file_rate);
        txvItemMoreAppCreateFileDownloaded = mRootPopup.findViewById(R.id.txv_item_more_app_create_file_downloaded);
        btnItemMoreAppCreateFileInstall = mRootPopup.findViewById(R.id.btn_item_more_app_create_file_install);

        lnTemplate.setOnClickListener(this);
        lnNewFile.setOnClickListener(this);
        lnlImvPopupCreateFileAds.setOnClickListener(this);
        lnlItemMoreAppCreateFileRoot.setOnClickListener(this);
        rllCreateFileRoot.setOnClickListener(this);

        ClickShrinkEffectKt.applyClickShrink(lnTemplate);
        ClickShrinkEffectKt.applyClickShrink(lnNewFile);
        ClickShrinkEffectKt.applyClickShrink(lnlImvPopupCreateFileAds);
    }

    @Override
    public void onClick(View v) {
        if (v == lnTemplate) {
            onClickNewDocument(1);

        } else if (v == lnNewFile) {
            onClickNewDocument(0);
        }
        dismiss();
    }

    public void onClickNewDocument(int pos) {
        Intent intent = new Intent(mContext, CreateFileActivity.class);
        intent.putExtra(RESULT, pos);
        mContext.startActivity(intent);
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
        if (touchable) {

        }
    }

}
