package com.hsw.motionjava.demo.dissolve;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOverlay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

import java.util.logging.Logger;

/**
 * @author heshuai
 * created on: 2020/6/24 2:45 PM
 * description:
 */
public class Dissolve extends Transition {
    private final static String PROPNAME_BITMAP = "com.hsw.motionjava.demo.dissolve:bitmap";


    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        // Store the current appearance of the view as a Bitmap.
        View view = transitionValues.view;
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        transitionValues.values.put(PROPNAME_BITMAP, bitmap);
    }

    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        Bitmap startBitmap = (Bitmap) startValues.values.get(PROPNAME_BITMAP);
        Bitmap endBitmap = (Bitmap) endValues.values.get(PROPNAME_BITMAP);

        // No need to animate if the start and the end look identical.
        if (startBitmap.sameAs(endBitmap)) {
            return null;
        }

        View view = endValues.view;

        final BitmapDrawable startDrawable = new BitmapDrawable(view.getResources(), startBitmap);
        startDrawable.setBounds(0, 0, startBitmap.getWidth(), startBitmap.getHeight());


        // Use ViewOverlay to show the start bitmap on top of the view that is currently showing the
        // end state. This allows us to overlap the start and end states during the animation.
        final ViewOverlay overlay = view.getOverlay();
        overlay.add(startDrawable);

        // Fade out the start bitmap.
        ObjectAnimator objectAnimator = ObjectAnimator
                // Use [BitmapDrawable#setAlpha(int)] to animate the alpha value.
                .ofInt(startDrawable, "alpha", 255, 0);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                overlay.remove(startDrawable);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                overlay.remove(startDrawable);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return objectAnimator;
    }
}
