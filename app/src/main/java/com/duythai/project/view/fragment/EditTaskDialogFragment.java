package com.duythai.project.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.duythai.project.R;
import com.duythai.project.model.Category;
import com.duythai.project.model.Task;
import com.duythai.project.utils.DateConverter;
import com.duythai.project.viewmodel.TaskViewModel;

import java.util.Calendar;
import java.util.Date;

public class EditTaskDialogFragment extends DialogFragment {
    Category categories = new Category();
    TaskViewModel viewModel;
    Date pickDate;
    Task task;
    public EditTaskDialogFragment(Task task){
        this.task = task;
        pickDate = task.getDate();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Category categories = new Category();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_task, null);
        builder.setView(view);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        EditText etContent = view.findViewById(R.id.etContent);
        Spinner sCategory = view.findViewById(R.id.sCategory);
        EditText etNote = view.findViewById(R.id.etNote);
        ImageView ivCalendar = view.findViewById(R.id.ivCalendar);
        TextView tvDate = view.findViewById(R.id.tvDate);

        ArrayAdapter ad = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories.list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(ad);

        etContent.setText(task.getContent());
        etNote.setText(task.getNote());
        tvDate.setText(DateConverter.toString(task.getDate()));
        sCategory.setSelection(categories.list.indexOf(task.getCategory()));
        ivCalendar.setOnClickListener(view1 -> {showDatePicker(tvDate);});
        tvDate.setOnClickListener(view1 -> {showDatePicker(tvDate);});

        builder.setPositiveButton("Update", (dialog, which)->{
            if(etContent.getText().toString().length() == 0|| tvDate.getText().toString().length() == 0) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Content and Date are required!")
                        .setPositiveButton(android.R.string.ok, null).show();
            }else{
                task.setContent(etContent.getText().toString());
                task.setDate(pickDate);
                task.setCategory(sCategory.getSelectedItem().toString());
                task.setNote(etNote.getText().toString());
                viewModel.update(task);
            }
        });
        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }

    void showDatePicker(TextView textView){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                (view1, year1, monthOfYear, dayOfMonth) -> {
                    textView.setText(dayOfMonth + "/" + monthOfYear + "/" + year1);
                    pickDate = DateConverter.setDate(year1, monthOfYear, dayOfMonth);
                }, year, month, day);
        datePickerDialog.show();
    }
}
