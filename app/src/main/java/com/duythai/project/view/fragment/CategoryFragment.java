package com.duythai.project.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.*;

import android.view.*;
import android.widget.TextView;

import com.duythai.project.R;
import com.duythai.project.apdapter.TaskAdapter;
import com.duythai.project.viewmodel.TaskViewModel;

public class CategoryFragment extends Fragment {

    RecyclerView rvPersonal, rvWork, rvFinance, rvFamily, rvHealth;
    TextView tvPersonal, tvWork, tvFinance, tvFamily, tvHealth;
    TaskViewModel viewModel;

    public CategoryFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        tvPersonal = root.findViewById(R.id.tvPersonal);
        tvWork = root.findViewById(R.id.tvWork);
        tvFamily = root.findViewById(R.id.tvFamily);
        tvFinance = root.findViewById(R.id.tvFinance);
        tvHealth = root.findViewById(R.id.tvHealth);

        rvPersonal = root.findViewById(R.id.rvPersonal);
        rvWork = root.findViewById(R.id.rvWork);
        rvFamily = root.findViewById(R.id.rvFamily);
        rvFinance = root.findViewById(R.id.rvFinance);
        rvHealth = root.findViewById(R.id.rvHealth);

        TaskAdapter personalAdapter = new TaskAdapter(new TaskAdapter.TaskDiff(), getParentFragmentManager());
        TaskAdapter workAdapter = new TaskAdapter(new TaskAdapter.TaskDiff(), getParentFragmentManager());
        TaskAdapter financeAdapter = new TaskAdapter(new TaskAdapter.TaskDiff(), getParentFragmentManager());
        TaskAdapter familyAdapter = new TaskAdapter(new TaskAdapter.TaskDiff(), getParentFragmentManager());
        TaskAdapter healthAdapter = new TaskAdapter(new TaskAdapter.TaskDiff(), getParentFragmentManager());

        rvPersonal.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvWork.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvFamily.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvFinance.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvHealth.setLayoutManager(new LinearLayoutManager(requireContext()));

        rvPersonal.setAdapter(personalAdapter);
        rvWork.setAdapter(workAdapter);
        rvFamily.setAdapter(familyAdapter);
        rvFinance.setAdapter(financeAdapter);
        rvHealth.setAdapter(healthAdapter);

        viewModel.getTasksByCategory("Personal").observe(getViewLifecycleOwner(), tasks -> {
            tvPersonal.setText("Personal(" + tasks.size() + ")");
            personalAdapter.submitList(tasks);
        });

        viewModel.getTasksByCategory("Work").observe(getViewLifecycleOwner(), tasks -> {
            tvWork.setText("Work(" + tasks.size() + ")");
            workAdapter.submitList(tasks);
        });

        viewModel.getTasksByCategory("Family").observe(getViewLifecycleOwner(), tasks -> {
            tvFamily.setText("Family(" + tasks.size() + ")");
            familyAdapter.submitList(tasks);
        });

        viewModel.getTasksByCategory("Finance").observe(getViewLifecycleOwner(), tasks -> {
            tvFinance.setText("Finance(" + tasks.size() + ")");
            financeAdapter.submitList(tasks);
        });

        viewModel.getTasksByCategory("Health").observe(getViewLifecycleOwner(), tasks -> {
            tvHealth.setText("Health(" + tasks.size() + ")");
            healthAdapter.submitList(tasks);
        });

        ViewGroup.LayoutParams params1 = rvPersonal.getLayoutParams();
        ViewGroup.LayoutParams params2 = rvWork.getLayoutParams();
        ViewGroup.LayoutParams params3 = rvFamily.getLayoutParams();
        ViewGroup.LayoutParams params4 = rvFinance.getLayoutParams();
        ViewGroup.LayoutParams params5 = rvHealth.getLayoutParams();

        tvPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params1.height!=0)
                    params1.height=0;
                else
                    params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvPersonal.setLayoutParams(params1);
            }
        });

        tvWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params2.height!=0)
                    params2.height=0;
                else
                    params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvWork.setLayoutParams(params2);
            }
        });

        tvFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params3.height!=0)
                    params3.height=0;
                else
                    params3.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvFinance.setLayoutParams(params3);
            }
        });

        tvFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params4.height!=0)
                    params4.height=0;
                else
                    params4.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvFamily.setLayoutParams(params4);
            }
        });

        tvHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params5.height!=0)
                    params5.height=0;
                else
                    params5.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvHealth.setLayoutParams(params5);
            }
        });
        return root;
    }
}