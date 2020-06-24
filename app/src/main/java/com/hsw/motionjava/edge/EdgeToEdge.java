package com.hsw.motionjava.edge;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.hsw.motionjava.R;

/**
 * @author heshuai
 * created on: 2020/6/24 10:45 AM
 * description:
 */
public class EdgeToEdge {

    public static void setUpRoot(ViewGroup root) {
        root.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    public static void setUpAppBar(final AppBarLayout appBar, final Toolbar toolbar) {
        final int toolbarPadding = toolbar.getResources().getDimensionPixelSize(R.dimen.spacing_medium);
        appBar.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets windowInsets) {
                appBar.setPadding(appBar.getPaddingLeft(), windowInsets.getSystemWindowInsetTop(), appBar.getPaddingRight(), appBar.getPaddingBottom());
                toolbar.setPadding(toolbarPadding + windowInsets.getSystemWindowInsetLeft(), toolbar.getPaddingTop(), windowInsets.getSystemWindowInsetRight(), toolbar.getPaddingBottom());
                return windowInsets;
            }
        });
    }

    public static void setUpScrollingContent(final ViewGroup scrollingContent) {
        final int originalPaddingLeft = scrollingContent.getPaddingLeft();
        final int originalPaddingRight = scrollingContent.getPaddingRight();
        final int originalPaddingBottom = scrollingContent.getPaddingBottom();

        scrollingContent.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets windowInsets) {
                scrollingContent.setPadding(originalPaddingLeft + windowInsets.getSystemWindowInsetLeft(), scrollingContent.getPaddingTop(), originalPaddingRight + windowInsets.getSystemWindowInsetRight(), originalPaddingBottom + windowInsets.getSystemWindowInsetBottom());
                return windowInsets;
            }
        });
    }
}
