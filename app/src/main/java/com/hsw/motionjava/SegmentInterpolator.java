package com.hsw.motionjava;

import android.animation.TimeInterpolator;

/**
 * @author heshuai
 * created on: 2020/6/28 1:59 PM
 * description:
 */
public class SegmentInterpolator implements TimeInterpolator {

    private TimeInterpolator base;
    private Float start = 0F;
    private Float end = 1F;

    public SegmentInterpolator(TimeInterpolator base, Float start, Float end) {
        this.base = base;
        this.start = start;
        this.end = end;
    }

    @Override
    public float getInterpolation(float input) {
        float offset = base.getInterpolation(start);
        float xRatio = (end - start) / 1f;
        float yRatio = (base.getInterpolation(end) - offset) / 1f;
        return (base.getInterpolation(start + (input * xRatio)) - offset) / yRatio;
    }
}
