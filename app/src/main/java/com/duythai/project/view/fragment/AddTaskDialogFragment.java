package com.duythai.project.view.fragment;

import android.app.*;
import android.os.Bundle;

import androidx.annotation.*;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.*;
import android.widget.*;

import com.duythai.project.R;
import com.duythai.project.model.Category;
import com.duythai.project.model.Task;
import com.duythai.project.utils.DateConverter;
import com.duythai.project.viewmodel.TaskViewModel;

import java.util.*;

public class AddTaskDialogFragment extends DialogFragment {

    Category categories = new Category();
    TaskViewModel viewModel;
    Date pickDate;

    public AddTaskDialogFragment(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_task, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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

        ivCalendar.setOnClickListener(view1 -> {showDatePicker(tvDate);});
        tvDate.setOnClickListener(view1 -> {showDatePicker(tvDate);});
        builder.setPositiveButton("Save", (dialog, which) -> {
            if(etContent.getText().toString().length() == 0|| tvDate.getText().toString().length() == 0) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Content and Date are required!")
                        .setPositiveButton(android.R.string.ok, null).show();
            }else{
                viewModel.insert(new Task(etContent.getText().toString(),
                        sCategory.getSelectedItem().toString(),
                        etNote.getText().toString(),
                        pickDate, false));
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