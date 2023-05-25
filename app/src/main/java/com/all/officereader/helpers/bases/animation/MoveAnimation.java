package com.all.officereader.helpers.bases.animation;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;

public class MoveAnimation {

    public static void OpenViewFromBottomAnimation(final View target, int distance, int duration) {
        BaseCreative baseCreative = new BaseCreative();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, BaseObjectAnimator.TRANSLATION_Y, distance, 0);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        baseCreative.addAnimator(objectAnimator);
        baseCreative.addAnimator(ObjectAnimator.ofFloat(target, BaseObjectAnimator.ALPHA, 0, 1));
        baseCreative.setDuration(duration);
        baseCreative.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                target.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        baseCreative.startAnimationTogether();
    }

    public static void closeViewToBottom(View target, int distance, int duration, Animator.AnimatorListener listener) {
        BaseCreative baseCreative = new BaseCreative();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, BaseObjectAnimator.TRANSLATION_Y, 0, distance);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        baseCreative.addAnimator(objectAnimator);
        baseCreative.addAnimator(ObjectAnimator.ofFloat(target, BaseObjectAnimator.ALPHA, 1, 0));
        baseCreative.setDuration(duration);
        baseCreative.addListener(listener);
        baseCreative.startAnimationTogether();
    }

    public static void openViewFromCenter(final View target, int duration){
        BaseCreative baseCreative = new BaseCreative();
        baseCreative.addAnimator(ObjectAnimator.ofFloat(target, BaseObjectAnimator.SCALE_X, 0f, 1f));
        baseCreative.addAnimator(ObjectAnimator.ofFloat(target, BaseObjectAnimator.SCALE_Y, 0f, 1f));
        baseCreative.addAnimator(ObjectAnimator.ofFloat(target, BaseObjectAnimator.ALPHA, 0f, 1f));
        baseCreative.setInterpolator(new OvershootInterpolator(1.1f));
        baseCreative.setDuration(duration);
        baseCreative.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                target.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        baseCreative.startAnimationTogether();
    }

    public static void closeViewToCenter(View target, int duration, Animator.AnimatorListener listener) {
        BaseCreative baseCreative = new BaseCreative();
        baseCreative.addAnimator(ObjectAnimator.ofFloat(target, BaseObjectAnimator.SCALE_X, 1f, 0f));
        baseCreative.addAnimator(ObjectAnimator.ofFloat(target, BaseObjectAnimator.SCALE_Y, 1f, 0f));
        baseCreative.addAnimator(ObjectAnimator.ofFloat(target, BaseObjectAnimator.ALPHA, 1f, 0f));
        baseCreative.setInterpolator(new AnticipateOvershootInterpolator(1.1f));
        baseCreative.setDuration(duration);
        baseCreative.addListener(listener);
        baseCreative.startAnimationTogether();
    }

    public static void openOnlinePager(final View onlinePager, final View localPager, int distance, int duration) {
        BaseCreative baseCreative = new BaseCreative();
        baseCreative.addAnimator(ObjectAnimator.ofFloat(onlinePager, BaseObjectAnimator.TRANSLATION_Y, -distance, 0));
        baseCreative.addAnimator(ObjectAnimator.ofFloat(localPager, BaseObjectAnimator.TRANSLATION_Y, 0, -distance));
        baseCreative.addAnimator(ObjectAnimator.ofFloat(onlinePager, BaseObjectAnimator.ALPHA, 0, 1));
        baseCreative.addAnimator(ObjectAnimator.ofFloat(localPager, BaseObjectAnimator.ALPHA, 1, 0));
        baseCreative.setDuration(duration);
        baseCreative.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                onlinePager.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                localPager.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        baseCreative.startAnimationTogether();
    }

    public static void closeOnlinePager(final View onlinePager, final View localPager, int distance, int duration) {
        BaseCreative baseCreative = new BaseCreative();
        baseCreative.addAnimator(ObjectAnimator.ofFloat(localPager, BaseObjectAnimator.TRANSLATION_Y, -distance, 0));
        baseCreative.addAnimator(ObjectAnimator.ofFloat(onlinePager, BaseObjectAnimator.TRANSLATION_Y, 0, -distance));
        baseCreative.addAnimator(ObjectAnimator.ofFloat(localPager, BaseObjectAnimator.ALPHA, 0, 1));
        baseCreative.addAnimator(ObjectAnimator.ofFloat(onlinePager, BaseObjectAnimator.ALPHA, 1, 0));
        baseCreative.setDuration(duration);
        baseCreative.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                localPager.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                onlinePager.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        baseCreative.startAnimationTogether();
    }
}
