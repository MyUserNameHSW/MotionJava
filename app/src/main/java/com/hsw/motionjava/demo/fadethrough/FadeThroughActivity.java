package com.hsw.motionjava.demo.fadethrough;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.hsw.motionjava.R;
import com.hsw.motionjava.edge.EdgeToEdge;

/**
 * @author heshuai
 * created on: 2020/6/25 1:08 PM
 * description:
 */
public class FadeThroughActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fade_through);

        Toolbar toolbar = findViewById(R.id.toolbar);
        final MaterialCardView card = findViewById(R.id.card);
        final ConstraintLayout contact = findViewById(R.id.card_contact);
        final ConstraintLayout cheese = findViewById(R.id.card_cheese);
        MaterialButton toggle = findViewById(R.id.toggle);
        ImageView icon  = findViewById(R.id.contact_icon);

        setSupportActionBar(toolbar);
        EdgeToEdge.setUpRoot((ViewGroup) findViewById(R.id.root));
        EdgeToEdge.setUpAppBar((AppBarLayout) findViewById(R.id.app_bar), toolbar);
        EdgeToEdge.setUpScrollingContent((ViewGroup) findViewById(R.id.content));
        Glide.with(icon).load(R.drawable.cheese_2).transform(new CircleCrop()).into(icon);

        final TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        transitionSet.setInterpolator(PathInterpolatorCompat.create(0.4f, 0f, 0.2f, 1f));
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new Fade(Fade.OUT));
        transitionSet.addTransition(new Fade(Fade.IN));

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(card, transitionSet.setDuration(300L));
                    contact.setVisibility(View.GONE);
                    cheese.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(card, transitionSet.setDuration(300L));
                    contact.setVisibility(View.VISIBLE);
                    cheese.setVisibility(View.GONE);
                }
            }
        });

    }
}
