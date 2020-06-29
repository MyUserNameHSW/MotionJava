package com.hsw.motionjava.demo.sharedelement;

import android.graphics.drawable.Drawable;
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
import androidx.transition.ChangeImageTransform;
import androidx.transition.ChangeTransform;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.Durations;
import com.hsw.motionjava.R;

import java.util.concurrent.TimeUnit;

/**
 * @author heshuai
 * created on: 2020/6/29 9:44 AM
 * description:
 */
public class CheeseDetailFragment extends Fragment {

    public final static String TRANSITION_NAME_IMAGE = "image";
    public final static String TRANSITION_NAME_NAME = "name";
    public final static String TRANSITION_NAME_TOOLBAR = "toolbar";
    public final static String TRANSITION_NAME_BACKGROUND = "background";
    public final static String TRANSITION_NAME_FAVORITE = "favorite";
    public final static String TRANSITION_NAME_BOOKMARK = "bookmark";
    public final static String TRANSITION_NAME_SHARE = "share";
    public final static String TRANSITION_NAME_BODY = "body";


    private Cheese cheese;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long cheeseId = getArguments().getLong("cheeseId", 1);
        for (Cheese item : Cheese.getCheeseList()) {
            if (cheeseId == item.getId()) {
                cheese = item;
                break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cheese_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postponeEnterTransition(500L, TimeUnit.MILLISECONDS);

        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        View dummyName = view.findViewById(R.id.dummy_name);
        TextView name = view.findViewById(R.id.name);
        ImageView image = view.findViewById(R.id.image);
        final NestedScrollView scroll = view.findViewById(R.id.scroll);
        final LinearLayout content = view.findViewById(R.id.content);
        CoordinatorLayout coordinator = view.findViewById(R.id.detail);
        View favorite = view.findViewById(R.id.favorite);
        View bookmark = view.findViewById(R.id.bookmark);
        View share = view.findViewById(R.id.share);

        // Transition names. Note that they don't need to match with the names of the selected grid
        // item. They only have to be unique in this fragment.
        ViewCompat.setTransitionName(image, TRANSITION_NAME_IMAGE);
        ViewCompat.setTransitionName(dummyName, TRANSITION_NAME_NAME);
        ViewCompat.setTransitionName(toolbar, TRANSITION_NAME_TOOLBAR);
        ViewCompat.setTransitionName(coordinator, TRANSITION_NAME_BACKGROUND);
        ViewCompat.setTransitionName(favorite, TRANSITION_NAME_FAVORITE);
        ViewCompat.setTransitionName(bookmark, TRANSITION_NAME_BOOKMARK);
        ViewCompat.setTransitionName(share, TRANSITION_NAME_SHARE);
        ViewCompat.setTransitionName(scroll, TRANSITION_NAME_BODY);
        ViewGroupCompat.setTransitionGroup(scroll, true);

        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
                layoutParams.topMargin = insets.getSystemWindowInsetTop();
                FrameLayout.LayoutParams scrollLayoutParams = (FrameLayout.LayoutParams) scroll.getLayoutParams();
                scrollLayoutParams.bottomMargin = insets.getSystemWindowInsetBottom();
                content.setPadding(insets.getSystemWindowInsetLeft(), content.getPaddingTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
                return insets;
            }
        });
        if (null != cheese) {
            name.setText(cheese.getName());

            Glide.with(image).load(cheese.getImage())
                    .dontTransform()
                    // We can start the transition when the image is loaded.
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            startPostponedEnterTransition();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            startPostponedEnterTransition();
                            return false;
                        }
                    })
                    .into(image);
        }

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
        return createSharedElementTransition(Durations.LARGE_EXPAND_DURATION);
    }

    @Nullable
    @Override
    public Object getSharedElementReturnTransition() {
        return createSharedElementTransition(Durations.LARGE_COLLAPSE_DURATION);
    }


    private Transition createSharedElementTransition(Long duration) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setDuration(duration);
        transitionSet.setInterpolator(PathInterpolatorCompat.create(0.4f, 0f, 0.2f, 1f));
        transitionSet.addTransition(new SharedFade());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeImageTransform());
        transitionSet.addTransition(new ChangeTransform());
        return transitionSet;
    }


}
