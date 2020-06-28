package com.hsw.motionjava.demo.navfadethrough;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeTransform;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.Durations;
import com.hsw.motionjava.R;

/**
 * @author heshuai
 * created on: 2020/6/28 5:15 PM
 * description:
 */
public class CheeseArticleFragment extends Fragment {
    public final static String TRANSITION_NAME_BACKGROUND = "background";
    public final static String TRANSITION_NAME_CARD_CONTENT = "card_content";
    public final static String TRANSITION_NAME_ARTICLE_CONTENT = "article_content";


    private Transition enterTransition, returnTransition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enterTransition =
                createSharedElementTransition(Durations.LARGE_EXPAND_DURATION, R.id.article_mirror);
        returnTransition =
                createSharedElementTransition(Durations.LARGE_COLLAPSE_DURATION, R.id.card_mirror);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cheese_article_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView name = view.findViewById(R.id.name);
        ImageView image = view.findViewById(R.id.image);
        final NestedScrollView scroll = view.findViewById(R.id.scroll);
        final LinearLayout content = view.findViewById(R.id.content);

        FrameLayout background = view.findViewById(R.id.background);
        CoordinatorLayout coordinator = view.findViewById(R.id.coordinator);
        MirrorView mirror = view.findViewById(R.id.card_mirror);

        ViewCompat.setTransitionName(background, TRANSITION_NAME_BACKGROUND);
        ViewCompat.setTransitionName(coordinator, TRANSITION_NAME_ARTICLE_CONTENT);
        ViewCompat.setTransitionName(mirror, TRANSITION_NAME_CARD_CONTENT);
        ViewGroupCompat.setTransitionGroup(coordinator, true);

        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
                layoutParams.topMargin = insets.getSystemWindowInsetTop();
                CoordinatorLayout.LayoutParams scrollLayoutParams = (CoordinatorLayout.LayoutParams) scroll.getLayoutParams();
                scrollLayoutParams.bottomMargin = insets.getSystemWindowInsetBottom();
                content.setPadding(insets.getSystemWindowInsetLeft(), content.getPaddingTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
                return insets;
            }
        });
        name.setText(Cheese.NAMES.get(1));
        image.setImageResource(Cheese.IMAGE_LIST.get(1));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
    }

    @Nullable
    @Override
    public Object getSharedElementEnterTransition() {
        return enterTransition;
    }

    @Nullable
    @Override
    public Object getSharedElementReturnTransition() {
        return returnTransition;
    }

    private Transition createSharedElementTransition(Long duration, @IdRes Integer noTransform) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setDuration(duration);
        transitionSet.setInterpolator(PathInterpolatorCompat.create(0.4f, 0f, 0.2f, 1f));
        //transitionSet.addTransition(new SharedFade());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform().excludeTarget(noTransform, true));
        return transitionSet;
    }
}
