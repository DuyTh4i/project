package com.duythai.project.apdapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.duythai.project.R;
import com.duythai.project.model.Task;

import java.util.Calendar;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    TextView tvContent, tvCategory, tvDate;

    CheckBox cbDone;
    CardView cvItem;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tvContent);
        tvCategory = itemView.findViewById(R.id.tvCategory);
        tvDate = itemView.findViewById(R.id.tvDate);
        cbDone = itemView.findViewById(R.id.cbDone);
        cvItem = itemView.findViewById(R.id.cvItem);
    }

    public void bind(Task task, int position){

        tvContent.setText(task.getContent());
        tvCategory.setText(task.getCategory());
        tvDate.setText(task.getDate().toString());

        cbDone.setChecked(task.isDone());
        cvItem.setCardBackgroundColor(Color.WHITE);
        if(task.isDone())
            cvItem.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));

        cbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean flag) {
                task.setDone(flag);
                if(flag)
                    cvItem.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));
                else
                    cvItem.setCardBackgroundColor(Color.WHITE);
            }
        });

        tvContent.setTextColor(ContextCompat.getColor(itemView.getContext(), isTaskLate(task)? R.color.red : R.color.black));

        cvItem.setOnClickListener(view -> {
            //showEditDialog(task, position);
        });
    }

    public static TaskViewHolder create(ViewGroup parent){
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false));
    }

    private boolean isTaskLate(Task task) {

        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        return task.getDate() != null && task.getDate().getTime() < currentDate.getTimeInMillis();
    }
}
