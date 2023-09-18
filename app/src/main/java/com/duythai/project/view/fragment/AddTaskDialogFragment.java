package com.duythai.project.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.*;
import android.widget.*;

import com.duythai.project.R;
import com.duythai.project.model.Category;
import com.duythai.project.model.Task;

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
    Category categories;
    NormalFragment normalFragment = new NormalFragment();
    CategoryFragment categoryFragment = new CategoryFragment();

    public AddTaskDialogFragment(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_task, null);
        builder.setView(view);

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
            String title = etContent.getText().toString();
            String dueDate = tvDate.getText().toString();
            String label = sCategory.getSelectedItem().toString();
            String note = etNote.getText().toString();
            addTaskToList(title,label,dueDate, note);
        });

        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }

    private void addTaskToList(String content, String category, String date, String note) {
        // Convert the due date string to a Calendar object

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Calendar dueDateCalendar = Calendar.getInstance();
        try {
            dueDateCalendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        Calendar currentDate = Calendar.getInstance();
        Task addedTask = new Task(content, category, note, date, false);
        if (dueDateCalendar.get(Calendar.DAY_OF_YEAR) < currentDate.get(Calendar.DAY_OF_YEAR)) {
            NormalFragment.lateAdapter.addTask(addedTask);
            NormalFragment.lateAdapter.notifyDataSetChanged();
        } else if (dueDateCalendar.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)) {
            NormalFragment.todayAdapter.addTask(addedTask);
            NormalFragment.todayAdapter.notifyDataSetChanged();
        } else if (dueDateCalendar.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR) + 1) {
            normalFragment.tomorrowAdapter.addTask(addedTask);
            normalFragment.tomorrowAdapter.notifyDataSetChanged();
        } else {
            normalFragment.upcomingAdapter.addTask(addedTask);
            normalFragment.upcomingAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View view) {}
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {}
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}