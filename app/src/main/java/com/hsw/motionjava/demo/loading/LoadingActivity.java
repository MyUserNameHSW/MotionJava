package com.hsw.motionjava.demo.loading;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.google.android.material.appbar.AppBarLayout;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.Durations;
import com.hsw.motionjava.R;
import com.hsw.motionjava.SequentialTransitionSet;
import com.hsw.motionjava.demolist.DiffCheessCallback;
import com.hsw.motionjava.edge.EdgeToEdge;

/**
 * @author heshuai
 * created on: 2020/6/28 1:44 PM
 * description:
 */
public class LoadingActivity extends AppCompatActivity {

    private RecyclerView list;

    private LoadingViewModel viewModel;

    private CheeseAdapter cheeseAdapter;
    private CheeseAdapter.PlaceholderAdapter placeholderAdapter;
    private SequentialTransitionSet fade;
    private RecyclerView.ItemAnimator savedItemAnimator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);
        Toolbar toolbar  = findViewById(R.id.toolbar);
        list = findViewById(R.id.list);
        setSupportActionBar(toolbar);
        EdgeToEdge.setUpRoot((ViewGroup) findViewById(R.id.coordinator));
        EdgeToEdge.setUpAppBar((AppBarLayout) findViewById(R.id.app_bar), toolbar);
        EdgeToEdge.setUpScrollingContent(list);
        fade = new SequentialTransitionSet();
        fade.setDuration(Durations.LARGE_EXPAND_DURATION);
        fade.setInterpolator(PathInterpolatorCompat.create(0.4f, 0f, 0.2f, 1f));
        fade.addTransition(new Fade(Fade.OUT));
        fade.addTransition(new Fade(Fade.IN));
        fade.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(@NonNull Transition transition) {

            }

            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                if (savedItemAnimator != null) {
                    list.setItemAnimator(savedItemAnimator);
                }
            }

            @Override
            public void onTransitionCancel(@NonNull Transition transition) {

            }

            @Override
            public void onTransitionPause(@NonNull Transition transition) {

            }

            @Override
            public void onTransitionResume(@NonNull Transition transition) {

            }
        });
        cheeseAdapter = new CheeseAdapter(new DiffCheessCallback());
        placeholderAdapter = new CheeseAdapter.PlaceholderAdapter();
        viewModel = new LoadingViewModel();
        list.setAdapter(placeholderAdapter);
        viewModel.getCheeses().observe(this, new Observer<PagedList<Cheese>>() {
            @Override
            public void onChanged(PagedList<Cheese> cheeses) {
                if (list.getAdapter() != cheeseAdapter) {
                    list.setAdapter(cheeseAdapter);
                    savedItemAnimator  = list.getItemAnimator();
                    list.setItemAnimator(null);
                    TransitionManager.beginDelayedTransition(list, fade);
                }

                cheeseAdapter.submitList(cheeses);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loading, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            TransitionManager.beginDelayedTransition(list, fade);
            list.setAdapter(placeholderAdapter);
            viewModel.refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
