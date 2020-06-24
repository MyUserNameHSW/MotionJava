package com.hsw.motionjava.demo.fabtransformation;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.R;
import com.hsw.motionjava.edge.EdgeToEdge;

import java.util.Arrays;
import java.util.List;

public class FabTransformationActivity extends AppCompatActivity {
    FloatingActionButton fab;
    TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_transformation);
        CoordinatorLayout root = findViewById(R.id.root);
        Toolbar toolbar = findViewById(R.id.toolbar);
        final CircularRevealCardView sheet = findViewById(R.id.sheet);
        View scrim = findViewById(R.id.scrim);
        ItemClickListener cheeseOnClick = new ItemClickListener();
        final List<CheeseItemViewHolder> cheeseHolders = Arrays.asList(new CheeseItemViewHolder((LinearLayout) findViewById(R.id.cheese_1), cheeseOnClick),
                new CheeseItemViewHolder((LinearLayout) findViewById(R.id.cheese_2), cheeseOnClick),
                new CheeseItemViewHolder((LinearLayout) findViewById(R.id.cheese_3), cheeseOnClick),
                new CheeseItemViewHolder((LinearLayout) findViewById(R.id.cheese_4), cheeseOnClick));

        message = findViewById(R.id.message);
        fab = findViewById(R.id.fab);
        setSupportActionBar(toolbar);

        EdgeToEdge.setUpRoot(root);
        EdgeToEdge.setUpAppBar((AppBarLayout) findViewById(R.id.app_bar), toolbar);
        final int fabMargin = getResources().getDimensionPixelSize(R.dimen.spacing_medium);
        ViewCompat.setOnApplyWindowInsetsListener(root, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
                layoutParams.setMargins(fabMargin + insets.getSystemWindowInsetLeft(), layoutParams.topMargin, fabMargin + insets.getSystemWindowInsetRight(), fabMargin + insets.getSystemWindowInsetBottom());
                fab.setLayoutParams(layoutParams);

                CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) sheet.getLayoutParams();
                layoutParams2.setMargins(fabMargin + insets.getSystemWindowInsetLeft(), layoutParams2.topMargin, fabMargin + insets.getSystemWindowInsetRight(), fabMargin + insets.getSystemWindowInsetBottom());
                sheet.setLayoutParams(layoutParams2);
                return insets;
            }
        });

        FabTransformationViewModel viewModel = new FabTransformationViewModel();
        viewModel.getListCheese().observe(this, new Observer<List<Cheese>>() {
            @Override
            public void onChanged(List<Cheese> cheeses) {
                for (int i = 0; i < cheeseHolders.size(); i++) {
                    CheeseItemViewHolder viewHolder = cheeseHolders.get(i);
                    if (i < cheeses.size()) {
                        viewHolder.root.setVisibility(View.VISIBLE);
                        viewHolder.root.setTag(R.id.tag_name, cheeses.get(i).getName());
                        viewHolder.textView.setText(cheeses.get(i).getName());
                        Glide.with(viewHolder.imageView)
                                .load(cheeses.get(i).getImage())
                                .transform(new CircleCrop())
                                .into(viewHolder.imageView);
                    } else {
                        viewHolder.root.setVisibility(View.GONE);
                    }
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setExpanded(true);
            }
        });
        scrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setExpanded(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (fab.isExpanded()) {
            fab.setExpanded(false);
        } else {
            super.onBackPressed();
        }
    }

    private class CheeseItemViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout root;

        private CheeseItemViewHolder(LinearLayout linearLayout, View.OnClickListener onClickListener) {
            imageView = linearLayout.findViewById(R.id.image);
            textView = linearLayout.findViewById(R.id.name);
            this.root = linearLayout;
            linearLayout.setOnClickListener(onClickListener);
        }
    }

    private class ItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            message.setText("you clicked " + v.getTag(R.id.tag_name).toString());
            fab.setExpanded(false);
        }
    }

}
