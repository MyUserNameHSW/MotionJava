package com.hsw.motionjava.demo.reorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hsw.motionjava.Cheese;
import com.hsw.motionjava.R;

/**
 * @author heshuai
 * created on: 2020/6/25 6:32 PM
 * description:
 */
public class CheeseGridAdapter extends ListAdapter<Cheese, CheeseGridAdapter.CheeseItemViewHolder> {

    private Context context;

    private OnVhLongClickListener onLongClickListener;

    protected CheeseGridAdapter(Context context, @NonNull DiffUtil.ItemCallback<Cheese> diffCallback) {
        super(diffCallback);
        this.context = context;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    public void setOnLongClickListener(OnVhLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    @NonNull
    @Override
    public CheeseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final CheeseItemViewHolder viewHolder = new CheeseItemViewHolder(context, parent);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickListener.onLongClick(viewHolder);
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CheeseItemViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }

    public interface OnVhLongClickListener {
        void onLongClick(RecyclerView.ViewHolder viewHolder);
    }

    static class CheeseItemViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout constraintLayout;
        private ConstraintSet constraintSet;
        private ImageView image;
        private TextView name;
        public CheeseItemViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.cheese_staggered_grid_item, parent, false));
            constraintLayout = itemView.findViewById(R.id.cheese);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
        }

        public void bindData(Cheese cheese) {
            constraintSet.setDimensionRatio(R.id.image,  "H,"+ cheese.getImageWidth()+ ":" + cheese.getImageHeight());
            constraintSet.applyTo(constraintLayout);
            Glide.with(image).load(cheese.getImage()).into(image);
            name.setText(cheese.getName());
        }
    }
}
