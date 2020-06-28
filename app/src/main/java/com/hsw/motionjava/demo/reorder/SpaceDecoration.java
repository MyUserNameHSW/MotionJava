package com.hsw.motionjava.demo.reorder;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author heshuai
 * created on: 2020/6/26 4:31 PM
 * description:
 */
public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public SpaceDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(spacing, spacing, spacing, spacing);
    }
}
