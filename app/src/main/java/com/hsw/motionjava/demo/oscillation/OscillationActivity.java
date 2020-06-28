package com.hsw.motionjava.demo.oscillation;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.R;
import com.hsw.motionjava.demolist.DiffCheessCallback;
import com.hsw.motionjava.edge.EdgeToEdge;

import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/28 4:28 PM
 * description:
 */
public class OscillationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oscillation_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView list = findViewById(R.id.list);
        setSupportActionBar(toolbar);

        EdgeToEdge.setUpRoot((ViewGroup) findViewById(R.id.root));
        EdgeToEdge.setUpAppBar((AppBarLayout) findViewById(R.id.app_bar), toolbar);
        EdgeToEdge.setUpScrollingContent(list);
        final CheeseAdapter adapter = new CheeseAdapter(new DiffCheessCallback());
        list.setAdapter(adapter);
        list.addOnScrollListener(new MyScrollListerer());
        list.setEdgeEffectFactory(new EdgeEffectFactory());
        OscillationViewModel viewModel = new OscillationViewModel();
        viewModel.getCheese().observe(this, new Observer<List<Cheese>>() {
            @Override
            public void onChanged(List<Cheese> cheeses) {
                adapter.submitList(cheeses);
            }
        });
    }

    class MyScrollListerer extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                CheeseAdapter.CheeseViewHolder holder = (CheeseAdapter.CheeseViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                holder.rotation
                        // Update the velocity.
                        // The velocity is calculated by the horizontal scroll offset.
                        .setStartVelocity(holder.currentVelocity - dx * 0.25f)
                        // Start the animation. This does nothing if the animation is already running.
                        .start();
            }
        }
    }

    class EdgeEffectFactory extends RecyclerView.EdgeEffectFactory {
        @NonNull
        @Override
        protected EdgeEffect createEdgeEffect(@NonNull final RecyclerView recyclerView, final int direction) {
            EdgeEffect edgeEffect = new EdgeEffect(recyclerView.getContext()) {

                @Override
                public void onPull(float deltaDistance) {
                    super.onPull(deltaDistance);
                    handlePull(deltaDistance);
                }

                @Override
                public void onPull(float deltaDistance, float displacement) {
                    super.onPull(deltaDistance, displacement);
                    handlePull(deltaDistance);
                }

                private void handlePull(Float deltaDistance) {
                    int sign = direction == DIRECTION_RIGHT ?  -1 : 1;
                    float rotationDelta = sign * deltaDistance * -10;
                    float translationXDelta = sign * recyclerView.getWidth() * deltaDistance * 0.2f;

                    for (int i = 0; i < recyclerView.getChildCount(); i++) {
                        CheeseAdapter.CheeseViewHolder holder = (CheeseAdapter.CheeseViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                        holder.rotation.cancel();
                        holder.translationX.cancel();
                        holder.itemView.setRotation(holder.itemView.getRotation() + rotationDelta);
                        holder.itemView.setTranslationX(holder.itemView.getTranslationX() + translationXDelta);
                    }
                }
            };
            return edgeEffect;
        }
    }
}
