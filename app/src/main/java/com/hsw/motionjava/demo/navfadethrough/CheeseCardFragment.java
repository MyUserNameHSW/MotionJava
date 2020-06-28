package com.hsw.motionjava.demo.navfadethrough;

import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.ui.NavigationUI;
import androidx.transition.Fade;
import androidx.transition.Transition;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.Durations;
import com.hsw.motionjava.R;

/**
 * @author heshuai
 * created on: 2020/6/28 5:15 PM
 * description:
 */
public class CheeseCardFragment extends Fragment {
    private Transition exitTransition, reenterTransition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exitTransition = new Fade(Fade.OUT) {
            @Override
            public long getDuration() {
                return Durations.LARGE_EXPAND_DURATION / 2;
            }

            @Nullable
            @Override
            public TimeInterpolator getInterpolator() {
                return PathInterpolatorCompat.create(0.4f, 0f, 1f, 1f);
            }
        };

        reenterTransition = new Fade(Fade.IN) {
            @Override
            public long getDuration() {
                return Durations.LARGE_COLLAPSE_DURATION / 2;
            }

            @Override
            public long getStartDelay() {
                return Durations.LARGE_COLLAPSE_DURATION / 2;
            }

            @Nullable
            @Override
            public TimeInterpolator getInterpolator() {
                return PathInterpolatorCompat.create(0f, 0f, 0.2f, 1f);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cheese_card_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        final FrameLayout content = view.findViewById(R.id.content);
        final MaterialCardView card = view.findViewById(R.id.card);
        final ConstraintLayout cardContent = view.findViewById(R.id.card_content);
        ImageView image = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);
        final MirrorView mirror = view.findViewById(R.id.article_mirror);

        ViewCompat.setOnApplyWindowInsetsListener((View) view.getParent(), new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                layoutParams.topMargin = insets.getSystemWindowInsetTop();
                content.setPadding(insets.getSystemWindowInsetLeft(), content.getPaddingTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
                return insets;
            }
        });

        ViewCompat.setTransitionName(card, "card");
        ViewCompat.setTransitionName(cardContent, "card_content");
        ViewCompat.setTransitionName(mirror, "article");
        ViewGroupCompat.setTransitionGroup(cardContent, true);

        name.setText(Cheese.NAMES.get(1));
        image.setImageResource(Cheese.IMAGE_LIST.get(1));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections navDirections = new NavDirections() {
                    @Override
                    public int getActionId() {
                        return R.id.action_article;
                    }

                    @NonNull
                    @Override
                    public Bundle getArguments() {
                        Bundle bundle = new Bundle();
                        bundle.putLong("cheeseId", Cheese.getCheeseList().get(1).getId());
                        return bundle;
                    }
                };

                FragmentNavigator.Extras.Builder extrasBuild = new FragmentNavigator.Extras.Builder();
                extrasBuild.addSharedElement(card, CheeseArticleFragment.TRANSITION_NAME_BACKGROUND);
                extrasBuild.addSharedElement(cardContent, CheeseArticleFragment.TRANSITION_NAME_CARD_CONTENT);
                extrasBuild.addSharedElement(mirror, CheeseArticleFragment.TRANSITION_NAME_ARTICLE_CONTENT);
                Navigation.findNavController(v).navigate(navDirections, extrasBuild.build());
            }
        });
    }
}
