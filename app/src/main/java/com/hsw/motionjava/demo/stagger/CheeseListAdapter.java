package com.hsw.motionjava.demo.stagger;

import android.content.Context;
import android.media.Image;
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
import com.hsw.motionjava.R;

/**
 * @author heshuai
 * created on: 2020/6/28 11:04 AM
 * description:
 */
public class CheeseListAdapter extends ListAdapter<Cheese, CheeseListAdapter.ListItemViewHolder> {

    private Context context;

    public CheeseListAdapter(@NonNull DiffUtil.ItemCallback<Cheese> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListItemViewHolder(context, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }

    static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ListItemViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.cheese_list_item, parent, false));
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.name);
        }

        public void bindData(Cheese cheese) {
            textView.setText(cheese.getName());
            Glide.with(imageView).load(cheese.getImage()).transform(new CircleCrop()).into(imageView);
        }
    }
}
