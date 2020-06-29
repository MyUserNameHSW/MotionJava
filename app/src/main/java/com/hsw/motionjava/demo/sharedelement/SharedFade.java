package com.hsw.motionjava.demo.sharedelement;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

import com.hsw.motionjava.demo.navfadethrough.MirrorView;

/**
 * @author heshuai
 * created on: 2020/6/28 6:30 PM
 * description:
 */
public class SharedFade extends Transition {
    public final static String PROPNAME_IS_MIRROR = "com.hsw.motionjava.demo:is_mirror";


    public void captureMirrorValues(@NonNull TransitionValues transitionValues) {
        if (null == transitionValues.view) {
            return;
        }
        transitionValues.values.put(PROPNAME_IS_MIRROR, transitionValues.view);
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureMirrorValues(transitionValues);
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureMirrorValues(transitionValues);
    }

    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        if (null == startValues.view || null == endValues.view) {
            return null;
        }
        View startView = startValues.view;
        View endView = endValues.view;

        if (startView instanceof MirrorView) {
            return ObjectAnimator.ofFloat(endView, View.ALPHA, 0f, 0f, 1f);
        }

        if (endView instanceof MirrorView) {
            return ObjectAnimator.ofFloat(endView, View.ALPHA, 1f, 0f, 0f);
        }

        return super.createAnimator(sceneRoot, startValues, endValues);
    }

    @Nullable
    @Override
    public String[] getTransitionProperties() {
        return new String[]{PROPNAME_IS_MIRROR};
    }
}
