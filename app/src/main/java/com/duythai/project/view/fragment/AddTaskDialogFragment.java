package com.duythai.project.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.*;
import android.widget.*;

import com.duythai.project.R;
import com.duythai.project.model.Category;
import com.duythai.project.model.Task;
import com.duythai.project.utils.DateConverter;
import com.duythai.project.viewmodel.TaskViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@SuppressWarnings("unchecked")
public class AddTaskDialogFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText etContent, etNote;
    Spinner sCategory;
    ImageView ivCalendar;
    TextView tvDate;
    Category categories = new Category();

    TaskViewModel viewModel;

    public AddTaskDialogFragment(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
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
        sCategory.setOnItemSelectedListener(this);

        ivCalendar.setOnClickListener(view1 -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                    (view2, year1, monthOfYear, dayOfMonth) -> tvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1),
                    year, month, day);
            datePickerDialog.show();
        });
        tvDate.setOnClickListener(view1 -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                    (view2, year1, monthOfYear, dayOfMonth) -> tvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1),
                    year, month, day);
            datePickerDialog.show();
        });

        builder.setPositiveButton("Save", (dialog, which) -> {
            viewModel.insert(new Task(etContent.getText().toString(), sCategory.getSelectedItem().toString(), etNote.getText().toString(), DateConverter.getCurrentDate(), false));
        });

        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }


    @Override
    public void onClick(View view) {}
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {}
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}