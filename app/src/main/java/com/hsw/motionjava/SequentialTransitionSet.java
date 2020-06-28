package com.hsw.motionjava;


import android.animation.TimeInterpolator;

import androidx.annotation.NonNull;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/28 2:02 PM
 * description:
 */
public class SequentialTransitionSet extends TransitionSet {

    private Long _duration = -1L;
    private TimeInterpolator _interpolator = null;

    private List<Float> weights = new ArrayList<>();

    public SequentialTransitionSet() {
        setOrdering(ORDERING_SEQUENTIAL);
    }

    public TransitionSet addTransition(Transition transition, Float weight) {
        super.addTransition(transition);
        weights.add(weight);
        distributeDuration();
        distributeInterpolator();
        return this;
    }

    @NonNull
    @Override
    public TransitionSet addTransition(@NonNull Transition transition) {
        return addTransition(transition, 1f);
    }

    @Override
    public long getDuration() {
        return _duration;
    }

    @Override
    public TransitionSet setDuration(long duration) {
        _duration = duration;
        distributeDuration();
        return this;
    }

    @Override
    public TransitionSet setInterpolator(TimeInterpolator interpolator) {
        _interpolator = interpolator;
        distributeInterpolator();
        return this;
    }

    @Override
    public TimeInterpolator getInterpolator() {
        return _interpolator;
    }
    private void distributeDuration() {
        if (_duration < 0) {
            for (int i = 0; i < getTransitionCount(); i++) {
                getTransitionAt(i).setDuration(-1);
            }
            return;
        }
        int totalWeight = weights.size();
        for (int i = 0; i < getTransitionCount(); i++) {
            getTransitionAt(i).setDuration((long) (_duration * weights.get(i) / totalWeight));
        }
    }

    private void distributeInterpolator() {
        TimeInterpolator interpolator = _interpolator;
        if (interpolator == null) {
            for (int i = 0; i < getTransitionCount(); i++) {
                getTransitionAt(i).setInterpolator(null);
            }
                return;
        }
        int totalWeight = weights.size();
        float start = 0f;
        for (int i = 0; i < getTransitionCount(); i++) {
            float range = weights.get(i) / totalWeight;
            getTransitionAt(i).setInterpolator(new SegmentInterpolator(interpolator, start, start + range));
            start += range;
        }
    }
}
