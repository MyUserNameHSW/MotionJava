package com.hsw.motionjava.demolist;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.hsw.motionjava.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/24 11:33 AM
 * description:
 */
public class DemoListAdapter extends ListAdapter<DemoBean, DemoListAdapter.DemoItemViewHolder> {

    private OnDemoSelectedListener onDemoSelectedListener;
    private Context context;

    protected DemoListAdapter(Context context, DiffCallback diffCallback, OnDemoSelectedListener demoSelectedListener) {
        super(diffCallback);
        this.onDemoSelectedListener = demoSelectedListener;
        this.context = context;
    }

    @NonNull
    @Override
    public DemoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DemoItemViewHolder(context, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoItemViewHolder holder, final int position) {
        holder.bind(getItem(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onDemoSelectedListener) {
                    onDemoSelectedListener.onDemoSelected(getItem(position));
                }
            }
        });
    }

    static class DemoItemViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        TextView description;
        List<TextView> apis;

        public DemoItemViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.demo_item, parent, false));
            label = itemView.findViewById(R.id.label);
            description = itemView.findViewById(R.id.description);
            apis = new ArrayList<>();
            apis.add((TextView) itemView.findViewById(R.id.api_1));
            apis.add((TextView) itemView.findViewById(R.id.api_2));
            apis.add((TextView) itemView.findViewById(R.id.api_3));
            apis.add((TextView) itemView.findViewById(R.id.api_4));
            apis.add((TextView) itemView.findViewById(R.id.api_5));
        }

        public void bind(DemoBean demoBean) {
            label.setText(demoBean.getLabel());
            description.setVisibility(TextUtils.isEmpty(demoBean.getDescription()) ? View.GONE : View.VISIBLE);
            description.setText(demoBean.getDescription());
            for (int i = 0; i < apis.size(); i++) {
                if (demoBean.getApis().size() > i) {
                    apis.get(i).setVisibility(View.VISIBLE);
                    apis.get(i).setText(demoBean.getApis().get(i));
                } else {
                    apis.get(i).setVisibility(View.GONE);
                }
            }
        }

    }

    public interface OnDemoSelectedListener {
        void onDemoSelected(DemoBean demoBean);
    }
}
