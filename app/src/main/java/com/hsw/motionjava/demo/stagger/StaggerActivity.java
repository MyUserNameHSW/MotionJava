package com.hsw.motionjava.demo.stagger;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.google.android.material.appbar.AppBarLayout;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.R;
import com.hsw.motionjava.demolist.DiffCheessCallback;
import com.hsw.motionjava.edge.EdgeToEdge;

import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/28 11:17 AM
 * description:
 */
public class StaggerActivity extends AppCompatActivity {

    private CheeseListViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stagger_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        final RecyclerView list = findViewById(R.id.list);
        setSupportActionBar(toolbar);
        EdgeToEdge.setUpRoot((ViewGroup) findViewById(R.id.root));
        EdgeToEdge.setUpAppBar((AppBarLayout) findViewById(R.id.app_bar), toolbar);
        EdgeToEdge.setUpScrollingContent(list);

        final CheeseListAdapter adapter = new CheeseListAdapter(new DiffCheessCallback(),this);
        list.setAdapter(adapter);

        // We animate item additions on our side, so disable it in RecyclerView.

        list.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateAdd(RecyclerView.ViewHolder holder) {
                dispatchAddFinished(holder);
                dispatchAddStarting(holder);
                return false;
            }
        });

        // This is the transition for the stagger effect.
        final Stagger stagger = new Stagger();

        viewModel = new CheeseListViewModel();

        viewModel.getCheese().observe(this, new Observer<List<Cheese>>() {
            @Override
            public void onChanged(List<Cheese> cheeses) {
                TransitionManager.beginDelayedTransition(list, stagger);
                adapter.submitList(cheeses);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stagger, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            viewModel.clear();
            viewModel.refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
