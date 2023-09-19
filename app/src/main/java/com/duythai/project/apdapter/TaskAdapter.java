package com.duythai.project.apdapter;

import android.annotation.SuppressLint;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.duythai.project.model.Task;

@SuppressWarnings("unchecked")
public class TaskAdapter extends ListAdapter<Task, TaskViewHolder> {

    FragmentManager fragmentManager;
    public TaskAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, FragmentManager fragmentManager) {
        super(diffCallback);
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TaskViewHolder.create(parent, fragmentManager);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Task getItem(int position) {
        return super.getItem(position);
    }

    public static class TaskDiff extends DiffUtil.ItemCallback<Task> {

        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem == newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.equals(newItem);
        }
    }
}
