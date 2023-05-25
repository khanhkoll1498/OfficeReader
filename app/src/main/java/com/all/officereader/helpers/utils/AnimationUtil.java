package com.all.officereader.helpers.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

public class AnimationUtil {
    private static AnimationUtil animationUtilsCustom = null;

    public static AnimationUtil getInstance(){
        if (animationUtilsCustom  == null){
            animationUtilsCustom = new AnimationUtil();
        }
        return animationUtilsCustom;
    }
    public void animationTranslateX (View view ,float fromX ,float toX  ,long duration){
        float[] x = new float[]{fromX,toX};
        ObjectAnimator translate = ObjectAnimator.ofFloat(view , "translationX",x);
        translate.setDuration(duration);
        translate.start();
    }
    public void animationTranslateYVisiable (final View view , float fromY , float toY , long duration){
        float[] y = new float[]{fromY,toY};
        ObjectAnimator translate = ObjectAnimator.ofFloat(view , "translationY",y);
        translate.setDuration(duration);
        view.setVisibility(View.VISIBLE);
        translate.start();
        translate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    public void animationTranslateYGone (final View view , float fromY , float toY , long duration){
        float[] y = new float[]{fromY,toY};
        ObjectAnimator translate = ObjectAnimator.ofFloat(view , "translationY",y);
        translate.setDuration(duration);
        translate.start();
        translate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    public void animationTranslateXGone (final View view , float fromY , float toY , long duration){
        float[] y = new float[]{fromY,toY};
        ObjectAnimator translate = ObjectAnimator.ofFloat(view , "translationX",y);
        translate.setDuration(duration);
        translate.start();
        translate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    public void animationTranslateXVisiable (final View view , float fromX , float toX , long duration){
        float[] y = new float[]{fromX,toX};
        ObjectAnimator translate = ObjectAnimator.ofFloat(view , "translationX",y);
        translate.setDuration(duration);
        view.setVisibility(View.VISIBLE);
        translate.start();
        translate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

}
