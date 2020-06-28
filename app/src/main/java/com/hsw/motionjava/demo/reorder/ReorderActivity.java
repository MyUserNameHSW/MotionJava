package com.hsw.motionjava.demo.reorder;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.R;
import com.hsw.motionjava.demolist.DiffCallback;
import com.hsw.motionjava.demolist.DiffCheessCallback;
import com.hsw.motionjava.edge.EdgeToEdge;

import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/25 6:30 PM
 * description:
 *  TODO 存在拖动时候的闪烁问题
 */
public class ReorderActivity extends AppCompatActivity {

    private ReorderViewModel viewModel;
    private float pickUpElevation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reorder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        final RecyclerView list = findViewById(R.id.list);
        setSupportActionBar(toolbar);
        EdgeToEdge.setUpRoot((ViewGroup) findViewById(R.id.root));
        EdgeToEdge.setUpAppBar((AppBarLayout) findViewById(R.id.app_bar), toolbar);
        EdgeToEdge.setUpScrollingContent(list);
        pickUpElevation = getResources().getDimensionPixelSize(R.dimen.pick_up_elevation);
        list.addItemDecoration(new SpaceDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_small)));

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallBack());
        itemTouchHelper.attachToRecyclerView(list);

        final CheeseGridAdapter adapter = new CheeseGridAdapter(this, new DiffCheessCallback());
        adapter.setOnLongClickListener(new CheeseGridAdapter.OnVhLongClickListener() {
            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                itemTouchHelper.startDrag(viewHolder);
            }
        });
        list.setAdapter(adapter);
        viewModel = new ReorderViewModel();
        viewModel.getList().observe(this, new Observer<List<Cheese>>() {
            @Override
            public void onChanged(List<Cheese> cheeses) {
                adapter.submitList(cheeses);
            }
        });
    }

    private class MyItemTouchCallBack extends ItemTouchHelper.Callback {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(ItemTouchHelper.LEFT|ItemTouchHelper.DOWN|ItemTouchHelper.RIGHT|ItemTouchHelper.UP, 0);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            viewModel.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (null == viewHolder) {
                return;
            }
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                ViewCompat.animate(viewHolder.itemView).setDuration(150L).translationZ(pickUpElevation);
            }
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            ViewCompat.animate(viewHolder.itemView).setDuration(150L).translationZ(0f);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    }
}
