package com.hsw.motionjava.demo.oscillation;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.R;

/**
 * @author heshuai
 * created on: 2020/6/28 3:59 PM
 * description:
 */
public class CheeseAdapter extends ListAdapter<Cheese, CheeseAdapter.CheeseViewHolder> {

    public CheeseAdapter(@NonNull DiffUtil.ItemCallback<Cheese> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public CheeseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheeseViewHolder cheeseViewHolder = new CheeseViewHolder(parent);
        cheeseViewHolder.itemView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.setPivotX(v.getWidth() / 2f);
                v.setPivotY(0f);
            }
        });
        return cheeseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheeseViewHolder holder, int position) {
        Cheese cheese = getItem(position);
        Glide.with(holder.image).load(cheese.getImage()).into(holder.image);
        holder.name.setText(cheese.getName());
    }

    static class CheeseViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        SpringAnimation rotation, translationX;
        float currentVelocity = 0f;
        public CheeseViewHolder(@NonNull ViewGroup viewGroup) {
            super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cheese_board_item, viewGroup, false));
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            rotation = new SpringAnimation(itemView, SpringAnimation.ROTATION);
            SpringForce springForce = new SpringForce();
            springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
            springForce.setFinalPosition(0f);
            springForce.setStiffness(SpringForce.STIFFNESS_LOW);
            rotation.setSpring(springForce);
            rotation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                @Override
                public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                    currentVelocity = velocity;
                }
            });

            SpringForce springForce2 = new SpringForce();
            springForce2.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
            springForce2.setFinalPosition(0f);
            springForce2.setStiffness(SpringForce.STIFFNESS_LOW);
            translationX = new SpringAnimation(itemView, SpringAnimation.TRANSLATION_X);
            translationX.setSpring(springForce2);
        }
    }
}
