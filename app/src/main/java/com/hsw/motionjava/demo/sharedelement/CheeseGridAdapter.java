package com.hsw.motionjava.demo.sharedelement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.card.MaterialCardView;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.R;
import com.hsw.motionjava.demo.navfadethrough.MirrorView;

/**
 * @author heshuai
 * created on: 2020/6/29 9:52 AM
 * description:
 */
public class CheeseGridAdapter extends ListAdapter<Cheese, CheeseGridAdapter.CheeseViewHolder> {

    private final static String STATE_LAST_SELECTED_ID = "last_selected_id";

    private Long lastSelectedId = null;
    private OnImageLoadFinishListener loadFinishListener;

    public CheeseGridAdapter(@NonNull DiffUtil.ItemCallback<Cheese> diffCallback, OnImageLoadFinishListener finishListener) {
        super(diffCallback);
        this.loadFinishListener = finishListener;
    }

    @NonNull
    @Override
    public CheeseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final CheeseViewHolder viewHolder = new CheeseViewHolder(parent);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Cheese cheese = getItem(viewHolder.getAdapterPosition());
                lastSelectedId = cheese.getId();
                FragmentNavigator.Extras.Builder extrasBuild = new FragmentNavigator.Extras.Builder();
                extrasBuild.addSharedElement(viewHolder.card,CheeseDetailFragment.TRANSITION_NAME_BACKGROUND);
                extrasBuild.addSharedElement(viewHolder.image, CheeseDetailFragment.TRANSITION_NAME_IMAGE);
                extrasBuild.addSharedElement(viewHolder.name, CheeseDetailFragment.TRANSITION_NAME_NAME);
                extrasBuild.addSharedElement(viewHolder.favorite, CheeseDetailFragment.TRANSITION_NAME_FAVORITE);
                extrasBuild.addSharedElement(viewHolder.bookmark, CheeseDetailFragment.TRANSITION_NAME_BOOKMARK);
                extrasBuild.addSharedElement(viewHolder.share, CheeseDetailFragment.TRANSITION_NAME_SHARE);
                extrasBuild.addSharedElement(viewHolder.toolbar, CheeseDetailFragment.TRANSITION_NAME_TOOLBAR);
                extrasBuild.addSharedElement(viewHolder.body, CheeseDetailFragment.TRANSITION_NAME_BODY);

                Bundle bundle = new Bundle();
                bundle.putLong("cheeseId", cheese.getId());
                Navigation.findNavController(v).navigate(R.id.cheeseDetailFragment, bundle, null, extrasBuild.build());
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheeseViewHolder holder, int position) {
        Cheese cheese = getItem(position);
        ViewCompat.setTransitionName(holder.image, "image-" + cheese.getId());
        ViewCompat.setTransitionName(holder.name, "name-" + cheese.getId());
        ViewCompat.setTransitionName(holder.toolbar, "toolbar-" + cheese.getId());
        ViewCompat.setTransitionName(holder.card, "card-" + cheese.getId());
        ViewCompat.setTransitionName(holder.favorite, "favorite-" + cheese.getId());
        ViewCompat.setTransitionName(holder.bookmark, "bookmark-" + cheese.getId());
        ViewCompat.setTransitionName(holder.share, "share-" + cheese.getId());
        ViewCompat.setTransitionName(holder.body, "body-" + cheese.getId());
        holder.name.setText(cheese.getName());
        RequestBuilder requestBuilder = Glide.with(holder.image).load(cheese.getImage()).dontTransform();

        if (null != lastSelectedId && cheese.getId() == lastSelectedId) {
            RequestOptions options = new RequestOptions();
            options = options.priority(Priority.IMMEDIATE);
            requestBuilder.apply(options).addListener(new RequestListener() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                    if (null != loadFinishListener) {
                        loadFinishListener.onImageLoadFinish();
                        lastSelectedId = null;
                    }
                    return false;
                }

                @Override
                public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                    if (null != loadFinishListener) {
                        loadFinishListener.onImageLoadFinish();
                        lastSelectedId = null;
                    }
                    return false;
                }
            });
        }
        requestBuilder.into(holder.image);
    }

    public void saveInstanceState(Bundle outState){
        if (null != lastSelectedId) {
            outState.putLong(STATE_LAST_SELECTED_ID, lastSelectedId);
        }
    }

    public void restoreInstanceState(Bundle state) {
        if (lastSelectedId == null && state.containsKey(STATE_LAST_SELECTED_ID)) {
            lastSelectedId = state.getLong(STATE_LAST_SELECTED_ID);
        }
    }

    static class CheeseViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        ImageView image;
        TextView name;
        MirrorView toolbar;
        ImageView favorite;
        ImageView bookmark;
        ImageView share;
        MirrorView body;
        
        public CheeseViewHolder(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.cheese_grid_item, parent, false));
            card = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            toolbar = itemView.findViewById(R.id.toolbar);
            favorite = itemView.findViewById(R.id.favorite);
            bookmark = itemView.findViewById(R.id.bookmark);
            share = itemView.findViewById(R.id.share);
            body = itemView.findViewById(R.id.body);
        }
    }

    public interface OnImageLoadFinishListener {
        void onImageLoadFinish();
    }
}
