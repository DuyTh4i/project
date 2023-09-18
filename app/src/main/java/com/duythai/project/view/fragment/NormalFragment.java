package com.duythai.project.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.*;

import android.view.*;
import android.widget.TextView;

import com.duythai.project.R;
import com.duythai.project.apdapter.TaskAdapter;
import com.duythai.project.utils.DateConverter;
import com.duythai.project.viewmodel.TaskViewModel;


public class NormalFragment extends Fragment {

    RecyclerView rvToday, rvTomorrow, rvUpcoming, rvLate;
    TextView tvToday, tvTomorrow, tvUpcoming, tvLate;
    TaskViewModel viewModel;

    public NormalFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_normal, container, false);
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        tvToday = root.findViewById(R.id.tvToday);
        tvTomorrow = root.findViewById(R.id.tvTomorrow);
        tvUpcoming = root.findViewById(R.id.tvUpcoming);
        tvLate = root.findViewById(R.id.tvLate);

        rvToday = root.findViewById(R.id.rvToday);
        rvTomorrow = root.findViewById(R.id.rvTomorrow);
        rvUpcoming = root.findViewById(R.id.rvUpcoming);
        rvLate = root.findViewById(R.id.rvLate);

        TaskAdapter todayAdapter = new TaskAdapter(new TaskAdapter.TaskDiff());
        TaskAdapter tomorrowAdapter = new TaskAdapter(new TaskAdapter.TaskDiff());
        TaskAdapter upcomingAdapter = new TaskAdapter(new TaskAdapter.TaskDiff());
        TaskAdapter lateAdapter = new TaskAdapter(new TaskAdapter.TaskDiff());

        rvToday.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvTomorrow.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvUpcoming.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvLate.setLayoutManager(new LinearLayoutManager(requireContext()));

        rvToday.setAdapter(todayAdapter);
        rvTomorrow.setAdapter(tomorrowAdapter);
        rvUpcoming.setAdapter(upcomingAdapter);
        rvLate.setAdapter(lateAdapter);

        viewModel.getTodayTasks(DateConverter.getCurrentDate()).observe(getViewLifecycleOwner(), tasks ->{
            todayAdapter.submitList(tasks);
            tvToday.setText("Today(" + todayAdapter.getItemCount() + ")");
        });
        viewModel.getTomorrowTasks(DateConverter.getTomorrow()).observe(getViewLifecycleOwner(), tasks ->{
            tomorrowAdapter.submitList(tasks);
            tvTomorrow.setText("Tomorrow(" + tomorrowAdapter.getItemCount() + ")");
        });
        viewModel.getUpcomingTasks(DateConverter.getTomorrow()).observe(getViewLifecycleOwner(), tasks ->{
            upcomingAdapter.submitList(tasks);
            tvUpcoming.setText("Upcoming(" + upcomingAdapter.getItemCount() + ")");
        });
        viewModel.getLateTasks(DateConverter.getCurrentDate()).observe(getViewLifecycleOwner(), tasks ->{
            lateAdapter.submitList(tasks);
            tvLate.setText("Late(" + lateAdapter.getItemCount() + ")");
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(todayAdapter.getItem(viewHolder.getAdapterPosition()).getId());
                tvToday.setText("Today(" + todayAdapter.getItemCount() + ")");
            }
        }).attachToRecyclerView(rvToday);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(tomorrowAdapter.getItem(viewHolder.getAdapterPosition()).getId());
                tvTomorrow.setText("Tomorrow(" + tomorrowAdapter.getItemCount() + ")");
            }
        }).attachToRecyclerView(rvTomorrow);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(upcomingAdapter.getItem(viewHolder.getAdapterPosition()).getId());
                tvUpcoming.setText("Upcoming(" + upcomingAdapter.getItemCount() + ")");
            }
        }).attachToRecyclerView(rvUpcoming);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(lateAdapter.getItem(viewHolder.getAdapterPosition()).getId());
                tvLate.setText("Late(" + lateAdapter.getItemCount() + ")");
            }
        }).attachToRecyclerView(rvLate);

        ViewGroup.LayoutParams params1 = rvToday.getLayoutParams();
        ViewGroup.LayoutParams params2 = rvTomorrow.getLayoutParams();
        ViewGroup.LayoutParams params3 = rvUpcoming.getLayoutParams();
        ViewGroup.LayoutParams params4 = rvLate.getLayoutParams();

        tvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params1.height!=0)
                    params1.height=0;
                else
                    params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvToday.setLayoutParams(params1);
            }
        });

        tvTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params2.height!=0)
                    params2.height=0;
                else
                    params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvTomorrow.setLayoutParams(params2);
            }
        });

        rvUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params3.height!=0)
                    params3.height=0;
                else
                    params3.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvUpcoming.setLayoutParams(params3);
            }
        });

        rvLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params4.height!=0)
                    params4.height=0;
                else
                    params4.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvLate.setLayoutParams(params4);
            }
        });

        return root;
    }
}