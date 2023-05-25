package com.all.officereader.screens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.all.officereader.R;
import com.all.officereader.helpers.rating.RateStarDialog;
import com.all.officereader.helpers.utils.Utility;
import com.all.officereader.popup.CreateFilesPopup;
import com.all.officereader.screens.dialogs.FeedbackDialog;
import com.all.officereader.screens.fragments.CreateNewFileFragment;
import com.all.officereader.screens.fragments.CreateTemplateFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CreateFileActivity extends FragmentActivity implements View.OnClickListener, DrawerLayout.DrawerListener {
    private int mPosType = 0;
    private long timeClick = 0;
    private File mFile;
    private ImageView imvPopupCreateFileClose;
    private ImageView imvPopupCreateFilePro;
    private ImageView imvPopupCreateFileAds;
    private TextView txvPopupCreateFileTemplate;
    private TextView txvPopupCreateFileNewFile;

    private FragmentManager fragmentManager;
    private CreateNewFileFragment createNewFileFragment;
    private CreateTemplateFragment createTemplateFragment;

    private RelativeLayout rllRateApp;
    private RelativeLayout rllShareApp;
    private RelativeLayout rllFeedBackApp;

    private CardView cvTemplate;
    private CardView cvNewFile;
    private DrawerLayout mDrawerLayout;

    private boolean isClickDrawer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_file);

        initView();
        initData();
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        mPosType = getIntent().getIntExtra(CreateFilesPopup.RESULT, 0);
        checkClickCategory(mPosType);
    }

    protected void initView() {
        fragmentManager = getSupportFragmentManager();

        imvPopupCreateFileClose = findViewById(R.id.imv_drawer_create_fille);
        imvPopupCreateFilePro = findViewById(R.id.imv_activity_create_file__pro);
        imvPopupCreateFileAds = findViewById(R.id.imv_activity_create_file__ads);
        txvPopupCreateFileTemplate = findViewById(R.id.tv_template);
        txvPopupCreateFileNewFile = findViewById(R.id.tv_new_file);
        mDrawerLayout = findViewById(R.id.dl_create_file);

        rllRateApp = findViewById(R.id.rll_rate_app);
        rllShareApp = findViewById(R.id.rll_share_app);
        rllFeedBackApp = findViewById(R.id.rll_feedback_app);

        cvTemplate = findViewById(R.id.cv_template);
        cvNewFile = findViewById(R.id.cv_new_file);

        createNewFileFragment = new CreateNewFileFragment();
        createTemplateFragment = new CreateTemplateFragment();

        fragmentManager
                .beginTransaction()
                .add(R.id.vpg_popup_create_file_pager, createNewFileFragment)
                .add(R.id.vpg_popup_create_file_pager, createTemplateFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
    }

    protected void initData() {
        txvPopupCreateFileNewFile.setOnClickListener(this);
        txvPopupCreateFileTemplate.setOnClickListener(this);
        imvPopupCreateFileClose.setOnClickListener(this);
        imvPopupCreateFilePro.setOnClickListener(this);
        imvPopupCreateFileAds.setOnClickListener(this);
        mDrawerLayout.setDrawerListener(this);
        rllRateApp.setOnClickListener(this);
        rllShareApp.setOnClickListener(this);
        rllFeedBackApp.setOnClickListener(this);
    }

    private void checkClickCategory(int pos) {
        switch (pos) {
            case 0:
                fragmentManager
                        .beginTransaction()
                        .show(createNewFileFragment)
                        .hide(createTemplateFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commitAllowingStateLoss();
                cvNewFile.setCardBackgroundColor(getResources().getColor(R.color.blue));
                txvPopupCreateFileNewFile.setTextColor(getResources().getColor(R.color.colorWhite));

                cvTemplate.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvPopupCreateFileTemplate.setTextColor(getResources().getColor(R.color.colorGray));
                break;

            case 1:
                fragmentManager
                        .beginTransaction()
                        .show(createTemplateFragment)
                        .hide(createNewFileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commitAllowingStateLoss();
                cvNewFile.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                txvPopupCreateFileNewFile.setTextColor(getResources().getColor(R.color.colorGray));

                cvTemplate.setCardBackgroundColor(getResources().getColor(R.color.blue));
                txvPopupCreateFileTemplate.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == txvPopupCreateFileNewFile) {
            checkClickCategory(0);

        } else if (view == txvPopupCreateFileTemplate) {
            checkClickCategory(1);

        } else if (view == imvPopupCreateFileClose) {
            if (isClickDrawer) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
                isClickDrawer = false;
            } else {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                isClickDrawer = true;
            }
        } else if (view == rllRateApp) {
            mDrawerLayout.closeDrawers();
            RateStarDialog rateStarDialog = new RateStarDialog(this);
            Utility.setUpDialog(rateStarDialog, this);
            rateStarDialog.show();

        } else if (view == rllShareApp) {
            mDrawerLayout.closeDrawers();
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Please share app with everyone!";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (view == rllFeedBackApp) {
            mDrawerLayout.closeDrawers();
            FeedbackDialog rateFeedBackDialogs = new FeedbackDialog(this);
            rateFeedBackDialogs.show();

        }
    }

    @Override
    public void onDrawerSlide(@NonNull @NotNull View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(@NonNull @NotNull View drawerView) {
        isClickDrawer = false;
    }

    @Override
    public void onDrawerClosed(@NonNull @NotNull View drawerView) {
        isClickDrawer = true;
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }
}
