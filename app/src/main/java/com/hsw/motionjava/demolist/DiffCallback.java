package com.hsw.motionjava.demolist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

/**
 * @author heshuai
 * created on: 2020/6/24 11:56 AM
 * description:
 */
public class DiffCallback extends DiffUtil.ItemCallback<DemoBean> {
    @Override
    public boolean areItemsTheSame(@NonNull DemoBean oldItem, @NonNull DemoBean newItem) {
        return oldItem.packageName.equals(newItem.packageName) &&
                oldItem.name.equals(newItem.name);
    }

    @Override
    public boolean areContentsTheSame(@NonNull DemoBean oldItem, @NonNull DemoBean newItem) {
        return oldItem == newItem;
    }
}