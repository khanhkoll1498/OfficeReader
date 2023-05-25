package com.all.officereader.helpers.rating;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.all.officereader.R;
import com.all.officereader.helpers.bases.BaseDialog;
import com.all.officereader.helpers.utils.AppUtil;
import com.all.officereader.helpers.utils.SharedPreferencesUtility;
import com.all.officereader.screens.dialogs.FeedbackDialog;

public class RateStarDialog extends BaseDialog implements View.OnClickListener {
    private Activity mActivity;
    private int mRating = 5;
    private FeedbackDialog mFeedbackDialog;

    private CardView cvOneStar;
    private CardView cvTwoStar;
    private CardView cvThreeStar;
    private CardView cvFourStar;
    private CardView cvFiveStar;

    private TextView tvSkip;

    public RateStarDialog(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.dialog_rate_app_main);
        findViews();
    }

    @Override
    protected void initData() {
    }

    private void findViews() {
        cvOneStar = findViewById(R.id.cv_one_star);
        cvTwoStar = findViewById(R.id.cv_two_star);
        cvThreeStar = findViewById(R.id.cv_three_star);
        cvFourStar = findViewById(R.id.cv_four_star);
        cvFiveStar = findViewById(R.id.cv_five_star);
        tvSkip = findViewById(R.id.tv_skip);

        cvOneStar.setOnClickListener(this);
        cvTwoStar.setOnClickListener(this);
        cvThreeStar.setOnClickListener(this);
        cvFourStar.setOnClickListener(this);
        cvFiveStar.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == cvOneStar) {
            mFeedbackDialog = new FeedbackDialog(mActivity);
            //Utility.setUpDialog(mFeedbackDialog, mActivity);
            mFeedbackDialog.show();
            dismiss();
            SharedPreferencesUtility.setShowRate(mActivity, false);

        } else if (v == cvTwoStar) {
            mFeedbackDialog = new FeedbackDialog(mActivity);
            //Utility.setUpDialog(mFeedbackDialog, mActivity);
            mFeedbackDialog.show();
            dismiss();
            SharedPreferencesUtility.setShowRate(mActivity, false);

        } else if (v == cvThreeStar) {
            mFeedbackDialog = new FeedbackDialog(mActivity);
            //Utility.setUpDialog(mFeedbackDialog, mActivity);
            mFeedbackDialog.show();
            dismiss();
            SharedPreferencesUtility.setShowRate(mActivity, false);

        } else if (v == cvFourStar) {
            AppUtil.goToGooglePlay(mActivity, mActivity.getPackageName());
            SharedPreferencesUtility.setShowRate(mActivity, false);
            dismiss();
            SharedPreferencesUtility.setShowRate(mActivity, false);

        } else if (v == cvFiveStar) {
            AppUtil.goToGooglePlay(mActivity, mActivity.getPackageName());
            SharedPreferencesUtility.setShowRate(mActivity, false);
            dismiss();
            SharedPreferencesUtility.setShowRate(mActivity, false);

        } else if (v == tvSkip) {
            SharedPreferencesUtility.setShowRate(mActivity, true);
            dismiss();
        }
    }
}
