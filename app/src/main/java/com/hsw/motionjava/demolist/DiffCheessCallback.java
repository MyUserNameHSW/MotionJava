package com.hsw.motionjava.demolist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.hsw.motionjava.Cheese;

/**
 * @author heshuai
 * created on: 2020/6/24 11:56 AM
 * description:
 */
public class DiffCheessCallback extends DiffUtil.ItemCallback<Cheese> {
    @Override
    public boolean areItemsTheSame(@NonNull Cheese oldItem, @NonNull Cheese newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Cheese oldItem, @NonNull Cheese newItem) {
        return oldItem == newItem;
    }
}