package com.all.officereader.helpers.bases.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

public class TranslateAnimator {

    // Animation X
    public static void showViewFromBottomX(View view, int duration, Animator.AnimatorListener animatorListener, float...params){
        BaseCreative baseCreative = new BaseCreative();
        baseCreative.addAnimator(ObjectAnimator.ofFloat(view, BaseObjectAnimator.TRANSLATION_X, params[0], params[1]));
        baseCreative.setDuration(duration);
        baseCreative.addListener(animatorListener);
        baseCreative.startAnimationTogether();
    }
    // Animation Y
    public static void showViewFromBottomY(View view, int duration, Animator.AnimatorListener animatorListener, float...params){
        BaseCreative baseCreative = new BaseCreative();
        baseCreative.addAnimator(ObjectAnimator.ofFloat(view, BaseObjectAnimator.TRANSLATION_Y, params[0], params[1]));
        baseCreative.setDuration(duration);
        baseCreative.addListener(animatorListener);
        baseCreative.startAnimationTogether();
    }
}
