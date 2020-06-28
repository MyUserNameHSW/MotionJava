package com.hsw.motionjava.demo.navfadethrough;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author heshuai
 * created on: 2020/6/28 5:39 PM
 * description:
 */
public class MirrorView extends View {

    private View substance;

    public MirrorView(Context context) {
        this(context, null);
    }

    public MirrorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MirrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(true);
    }

    public View getSubstance() {
        return substance;
    }

    public void setSubstance(View substance) {
        this.substance = substance;
        setWillNotDraw(substance == null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        substance.draw(canvas);
    }
}
