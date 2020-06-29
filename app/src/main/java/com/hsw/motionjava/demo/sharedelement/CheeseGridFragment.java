package com.hsw.motionjava.demo.sharedelement;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Explode;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;

import com.google.android.material.appbar.AppBarLayout;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.Durations;
import com.hsw.motionjava.R;
import com.hsw.motionjava.demo.reorder.SpaceDecoration;
import com.hsw.motionjava.demolist.DiffCheessCallback;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author heshuai
 * created on: 2020/6/29 9:43 AM
 * description:
 */
public class CheeseGridFragment extends Fragment {

    private final static List<Cheese> LIST_DATA = Cheese.getCheeseList();
    private CheeseGridAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cheese_grid_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new CheeseGridAdapter(new DiffCheessCallback(), new CheeseGridAdapter.OnImageLoadFinishListener() {
            @Override
            public void onImageLoadFinish() {
                startPostponedEnterTransition();
            }
        });
        if (null != savedInstanceState) {
            adapter.restoreInstanceState(savedInstanceState);
        }

        postponeEnterTransition(500L, TimeUnit.MILLISECONDS);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        final RecyclerView grid = view.findViewById(R.id.grid);

        int gridPadding = getResources().getDimensionPixelSize(R.dimen.spacing_tiny);
        ViewCompat.setOnApplyWindowInsetsListener((View) view.getParent(), new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                layoutParams.topMargin = insets.getSystemWindowInsetTop();
                grid.setPadding(insets.getSystemWindowInsetLeft(), grid.getPaddingTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
                return insets;
            }
        });

        grid.addItemDecoration(new SpaceDecoration(gridPadding));
        grid.setAdapter(adapter);
        adapter.submitList(LIST_DATA);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        adapter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public Object getExitTransition() {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setDuration(Durations.LARGE_EXPAND_DURATION / 2);
        transitionSet.setInterpolator(PathInterpolatorCompat.create(0.4f, 0f, 1f, 1f));
        Slide slide = new Slide(Gravity.TOP);
        slide.setMode(Slide.MODE_OUT);
        slide.addTarget(R.id.app_bar);
        transitionSet.addTransition(slide);
        Explode explode = new Explode();
        explode.setMode(Explode.MODE_OUT);
        explode.excludeTarget(R.id.app_bar, true);
        transitionSet.addTransition(explode);
        return transitionSet;
    }

    @Nullable
    @Override
    public Object getReenterTransition() {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setDuration(Durations.LARGE_COLLAPSE_DURATION / 2);
        transitionSet.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 0.2f, 1f));
        Slide slide = new Slide(Gravity.TOP);
        slide.setMode(Slide.MODE_IN);
        slide.addTarget(R.id.app_bar);

        Explode explode = new Explode();
        explode.setStartDelay(Durations.LARGE_COLLAPSE_DURATION / 2);
        explode.setMode(Explode.MODE_IN);
        explode.excludeTarget(R.id.app_bar, true);
        transitionSet.addTransition(slide);
        transitionSet.addTransition(explode);
        return transitionSet;
    }

}
