package com.all.officereader.screens.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.all.officereader.R;
import com.all.officereader.helpers.utils.ScreenUtils;

public class DialogLoading {
    public static PopupWindow initLoadDialog(final Activity activity){
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_loading,null);
        PopupWindow window = new PopupWindow(view);
        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setFocusable(false);
        window.setOutsideTouchable(false);
        window.setAnimationStyle(R.style.fadeDialogAnim);
        setBackgroundAlpha(0.8f,activity);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f,activity);
            }
        });
        return window;
    }
    public static void setBackgroundAlpha(float bgAlpha, Context context) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }
    public static class LoadingDialog extends Dialog {
        private View mDialogView;
        private TextView mContent;
        private AnimationSet mAnimIn, mAnimOut;
        private CharSequence mContentText;
        private boolean mBackPressed;
        private boolean mIsShowAnim;

        public LoadingDialog(Context context) {
            this(context, 0);
        }

        public LoadingDialog(Context context, int theme) {
            super(context, R.style.loading_dialog_style2);
            init();
        }

        @Override
        public void onBackPressed() {
            if (mBackPressed)
                super.onBackPressed();
        }

        private void callDismiss() {
            super.dismiss();
        }

        private void init() {
            mAnimIn = ScreenUtils.getInAnimation(getContext());
            mAnimOut = ScreenUtils.getOutAnimation(getContext());
            initAnimListener();
        }

        @Override
        public void setTitle(CharSequence title) {

        }

        @Override
        public void setTitle(int titleId) {
            setTitle(getContext().getText(titleId));
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View contentView = View.inflate(getContext(), R.layout.dialog_loading2, null);
            setContentView(contentView);
            mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
            setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onStart() {
            super.onStart();
            startWithAnimation(mIsShowAnim);
        }

        @Override
        public void dismiss() {
            dismissWithAnimation(mIsShowAnim);
        }

        private void startWithAnimation(boolean showInAnimation) {
            if (showInAnimation) {
                mDialogView.startAnimation(mAnimIn);
            }
        }

        private void dismissWithAnimation(boolean showOutAnimation) {
            if (showOutAnimation) {
                mDialogView.startAnimation(mAnimOut);
            } else {
                super.dismiss();
            }
        }

        private void initAnimListener() {
            mAnimOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mDialogView.post(new Runnable() {
                        @Override
                        public void run() {
                            callDismiss();
                        }
                    });

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }

        public LoadingDialog setContentText(CharSequence text) {
            mContentText = text;
            return LoadingDialog.this;
        }


        public void setBackPressed(boolean backPressed) {
            mBackPressed = backPressed;

        }
    }
}
