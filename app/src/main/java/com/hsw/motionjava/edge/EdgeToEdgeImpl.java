package com.hsw.motionjava.edge;

import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

/**
 * @author heshuai
 * created on: 2020/6/24 10:39 AM
 * description:
 */
public interface EdgeToEdgeImpl {
    /**
     * Configures a root view of an Activity in edge-to-edge display.
     * @param root A root view of an Activity.
     */
    void setUpRoot(ViewGroup root);

    /**
     * Configures an app bar and a toolbar for edge-to-edge display.
     * @param appBar An [AppBarLayout].
     * @param toolbar A [Toolbar] in the [appBar].
     */
    void setUpAppBar(AppBarLayout appBar, Toolbar toolbar);

    /**
     * Configures a scrolling content for edge-to-edge display.
     * @param scrollingContent A scrolling ViewGroup. This is typically a RecyclerView or a
     * ScrollView. It should be as wide as the screen, and should touch the bottom edge of
     * the screen.
     */
    void setUpScrollingContent(ViewGroup scrollingContent);
}
