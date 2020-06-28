package com.hsw.motionjava.demo.loading;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.Durations;
import com.hsw.motionjava.R;

/**
 * @author heshuai
 * created on: 2020/6/28 12:06 PM
 * description:
 */
public class CheeseAdapter extends ListAdapter<Cheese, CheeseAdapter.ItemViewHolder> {

    public CheeseAdapter(@NonNull DiffUtil.ItemCallback<Cheese> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (null == getItem(position)) {
            holder.showPlaceholder();
        } else {
            holder.bind(getItem(position));
        }
    }

    public static class PlaceholderAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.showPlaceholder();
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        ObjectAnimator animator;
        public ItemViewHolder(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.cheese_list_item, parent, false));
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);

            animator = ObjectAnimator.ofFloat(itemView, View.ALPHA, 1f, 0f, 1f);
            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.setDuration(1000L);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    itemView.setAlpha(1f);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        public void showPlaceholder() {
            // Shift the timing of fade-in/out for each item by its adapter position. We use the
            // elapsed real time to make this independent from the timing of method call.
            animator.setCurrentPlayTime(SystemClock.elapsedRealtime() - getAdapterPosition() * 30 % 1000L);
            animator.start();
            // Show the placeholder UI.
            image.setImageResource(R.drawable.image_placeholder);
            name.setText(null);
            name.setBackgroundResource(R.drawable.text_placeholder);
        }

        public void bind(Cheese cheese) {
            animator.end();
            Glide.with(image).load(cheese.getImage()).transform(new CircleCrop()).into(image);
            name.setText(cheese.getName());
            name.setBackgroundResource(0);
        }
    }
}
