package com.hsw.motionjava.demo.stagger;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.transition.Fade;
import androidx.transition.SidePropagation;
import androidx.transition.TransitionValues;

import com.hsw.motionjava.Durations;

/**
 * @author heshuai
 * created on: 2020/6/28 10:49 AM
 * description:
 */
public class Stagger extends Fade {
    public Stagger() {
        super(Fade.IN);
        setDuration(Durations.LARGE_EXPAND_DURATION / 2);
        setInterpolator(PathInterpolatorCompat.create(0f, 0f, 0.2f, 1f));
        SidePropagation sidePropagation = new SidePropagation();
        sidePropagation.setSide(Gravity.BOTTOM);
        sidePropagation.setPropagationSpeed(1f);
        setPropagation(sidePropagation);
    }


    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        View view = null;
        if (null != startValues) {
            view = startValues.view;
        } else if (null != endValues) {
            view = endValues.view;
        } else {
            return null;
        }
        Animator fadeAnimator = super.createAnimator(sceneRoot, startValues, endValues);

        if (null == fadeAnimator) {
            return null;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fadeAnimator, ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.getHeight() * 0.5f, 0f));
        return animatorSet;
    }
}
