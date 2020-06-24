package com.hsw.motionjava.demolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.internal.FlowLayout;
import com.hsw.motionjava.R;
import com.hsw.motionjava.edge.EdgeToEdge;

import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/24 11:17 AM
 * description:
 */
public class DemoListFragment extends Fragment {

    private DemoViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new DemoViewModel(getActivity().getApplication());
        return inflater.inflate(R.layout.demo_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView demoList = view.findViewById(R.id.demo_list);
        new EdgeToEdge().setUpScrollingContent(demoList);
        final DemoListAdapter adapter = new DemoListAdapter(requireContext(), new DiffCallback(), new DemoListAdapter.OnDemoSelectedListener() {
            @Override
            public void onDemoSelected(DemoBean demoBean) {
                requireActivity().startActivity(demoBean.toIntent());
            }
        });
        demoList.setAdapter(adapter);
        viewModel.demos.observe(getViewLifecycleOwner(), new Observer<List<DemoBean>>() {
            @Override
            public void onChanged(List<DemoBean> demoBeans) {
                adapter.submitList(demoBeans);
            }
        });
    }
}
