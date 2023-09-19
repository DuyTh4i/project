package com.duythai.project.apdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.duythai.project.R;
import com.duythai.project.model.Task;
import com.duythai.project.utils.DateConverter;
import com.duythai.project.view.fragment.EditTaskDialogFragment;
import com.duythai.project.viewmodel.TaskViewModel;

import java.util.Calendar;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    TextView tvContent, tvCategory, tvDate;
    CheckBox cbDone;
    CardView cvItem;
    TaskViewModel taskViewModel;
    FragmentManager fragmentManager;


    public TaskViewHolder(@NonNull View itemView, ViewModelStoreOwner owner, FragmentManager fragmentManager) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tvContent);
        tvCategory = itemView.findViewById(R.id.tvCategory);
        tvDate = itemView.findViewById(R.id.tvDate);
        cbDone = itemView.findViewById(R.id.cbDone);
        cvItem = itemView.findViewById(R.id.cvItem);
        taskViewModel = new ViewModelProvider(owner).get(TaskViewModel.class);
        this.fragmentManager = fragmentManager;
    }

    public void bind(Task task){

        tvContent.setText(task.getContent());
        tvCategory.setText(task.getCategory());
        tvDate.setText(DateConverter.toString(task.getDate()));

        cbDone.setChecked(task.isDone());
        cvItem.setCardBackgroundColor(Color.WHITE);
        if(task.isDone())
            cvItem.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));

        cbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean flag) {
                task.setDone(flag);
                taskViewModel.update(task);
                if(flag)
                    cvItem.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));
                else
                    cvItem.setCardBackgroundColor(Color.WHITE);
            }
        });

        tvContent.setTextColor(ContextCompat.getColor(itemView.getContext(), isTaskLate(task)? R.color.red : R.color.black));

        cvItem.setOnClickListener(view -> {
            EditTaskDialogFragment editTaskDialogFragment = new EditTaskDialogFragment(task);
            editTaskDialogFragment.show(fragmentManager, "EditTaskDialogFragment");
        });

        cvItem.setOnLongClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Delete task")
                    .setMessage("Do you really want to delete this task?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            taskViewModel.delete(task.getId());
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
            return false;
        });
    }

    public static TaskViewHolder create(ViewGroup parent, FragmentManager fragmentManager){
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false), (ViewModelStoreOwner) parent.getContext(), fragmentManager);
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
