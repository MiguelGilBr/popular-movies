package com.example.popularmovies.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

public class AnimatorHelper {

    private AnimatorSet mShrinkAnim;
    private AnimatorSet mGrowAnim;
    private AnimatorSet mTranslateYAnim;

    public ObjectAnimator initRotateInClockAnim(View view, int duration, boolean reverse) {
        ObjectAnimator objectAnimator =  ObjectAnimator.ofFloat(view, "rotation", 0f, 360f).setDuration(duration);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        if (reverse){
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        }
        return objectAnimator;
    }

    public ObjectAnimator initFadeInAnim(View view, int duration) {
        return ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).setDuration(duration);
    }

    public ObjectAnimator initFadeOutAnim(View view, int duration) {
        return ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).setDuration(duration);
    }

    public ObjectAnimator initDelayAnim(View view, int duration) {
        return ObjectAnimator.ofFloat(view, "alpha", 0f).setDuration(duration);
    }

    public ObjectAnimator initDelayTranslateYAnim(View view, int duration) {
        return ObjectAnimator.ofFloat(view, "translationY", 0).setDuration(duration);
    }

    public AnimatorSet initShrinkScale(View view, float value, int duration) {
        mShrinkAnim = new AnimatorSet();
        ObjectAnimator shrinkScaleX = ObjectAnimator.ofFloat(view, "scaleX", value);
        shrinkScaleX.setDuration(duration);
        ObjectAnimator shrinkScaleY = ObjectAnimator.ofFloat(view, "scaleY", value);
        shrinkScaleY.setDuration(duration);

        mShrinkAnim.playTogether(shrinkScaleX, shrinkScaleY);
        return mShrinkAnim;
    }
    public AnimatorSet initGrowScale(View view, float value, int duration) {
        mGrowAnim = new AnimatorSet();
        ObjectAnimator growScaleX = ObjectAnimator.ofFloat(view, "scaleX", value);
        growScaleX.setDuration(duration);
        ObjectAnimator growScaleY = ObjectAnimator.ofFloat(view, "scaleY", value);
        growScaleY.setDuration(duration);

        mGrowAnim.playTogether(growScaleX, growScaleY);
        return mGrowAnim;
    }

    public ObjectAnimator initTranslationY(View view, float value,  int duration) {
        return ObjectAnimator.ofFloat(view, "translationY", value).setDuration(duration);
    }

    public AnimatorSet initTranslationYTogether(View view, View imageView, float value,  int duration) {
        mTranslateYAnim = new AnimatorSet();
        ObjectAnimator translateYHighlightAnim = initTranslationY(view, value, duration);
        ObjectAnimator translateYTimelineAnim = initTranslationY(imageView, value, duration);

        mTranslateYAnim.playTogether(translateYHighlightAnim, translateYTimelineAnim);
        return mTranslateYAnim;
    }

    public AnimatorSet playSequentially(Animator... items) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(items);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //animation.start();
            }
        });
        return animatorSet;
    }


}