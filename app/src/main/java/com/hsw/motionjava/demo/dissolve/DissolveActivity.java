package com.hsw.motionjava.demo.dissolve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.hsw.motionjava.R;
import com.hsw.motionjava.edge.EdgeToEdge;

public class DissolveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dissolve);
        Toolbar toolbar  = findViewById(R.id.toolbar);
        final ImageView image = findViewById(R.id.image);
        final MaterialCardView card = findViewById(R.id.card);
        MaterialButton next = findViewById(R.id.next);
        setSupportActionBar(toolbar);
        EdgeToEdge.setUpRoot((ViewGroup) findViewById(R.id.root));
        EdgeToEdge.setUpAppBar((AppBarLayout) findViewById(R.id.app_bar), toolbar);
        EdgeToEdge.setUpScrollingContent((ViewGroup) findViewById(R.id.content));

        final Transition dissolve = new Dissolve();
        dissolve.addTarget(image);
        dissolve.setDuration(500L);
//        dissolve.setInterpolator(PathInterpolatorCompat.create(0.4f, 0f, 0.2f, 1f));

        final DissolveViewModel viewModel = new DissolveViewModel();

        viewModel.getImageResId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                // This delays the dissolve to be invoked at the next draw frame.
                TransitionManager.beginDelayedTransition(card, dissolve);
                // Here, we are simply changing the image shown on the image view. The animation is
                // handled by the transition API.
                image.setImageResource(integer);
            }
        });

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.next();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.next();
            }
        });

    }
}
