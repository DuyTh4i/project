package com.duythai.project.apdapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.duythai.project.DAO.AppDatabase;
import com.duythai.project.DAO.ITaskDAO;
import com.duythai.project.R;
import com.duythai.project.model.Category;
import com.duythai.project.model.Task;

import java.util.*;

@SuppressWarnings("unchecked")
public class TaskAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<Task> list;
    private Context mcontext;
    private AppDatabase db;
    private ITaskDAO taskDao;

    public TaskAdapter(Context mcontext) {
        this.list = new ArrayList<>();
        this.mcontext = mcontext;
        db = Room.databaseBuilder(mcontext, AppDatabase.class, "lap.db").allowMainThreadQueries().build();
        taskDao = db.taskDAO();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_normal, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Task task = list.get(position);
        holder.tvContent.setText(task.getContent());
        holder.tvCategory.setText(task.getCategory());
        holder.tvDate.setText(task.getDate());

        holder.cbDone.setChecked(task.isDone());
        holder.cvItem.setCardBackgroundColor(Color.WHITE);
        if(task.isDone())
            holder.cvItem.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));

        holder.cbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean flag) {
                task.setDone(flag);
                if(flag)
                    holder.cvItem.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));
                else
                    holder.cvItem.setCardBackgroundColor(Color.WHITE);
            }
        });

        boolean isLate = isTaskLate(task);
        holder.tvContent.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        if(isLate)
            holder.tvContent.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));

        holder.cvItem.setOnClickListener(view -> {
            showEditDialog(task, position);
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void addTask(Task task) {
        taskDao.insert(task);
        list.add(task);
        notifyItemInserted(list.size() - 1);
    }

    public void getAll(ArrayList<Task> list) {
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        Task task = list.get(position);
        taskDao.delete(task.getContent(), task.getDate(), task.getCategory());
        list.remove(position);
        notifyItemRemoved(position);
    }

    private boolean isTaskLate(Task task) {

        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        long currentTimeMillis = currentDate.getTimeInMillis();
        Date dueDate = task.stringToDate(task.getDate());

        return dueDate != null && dueDate.getTime() < currentTimeMillis;
    }

    private void showEditDialog(Task task, int position) {
        Category categories = new Category();
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        View view = LayoutInflater.from(mcontext).inflate(R.layout.fragment_add_task, null);
        builder.setView(view);

        EditText etContent = view.findViewById(R.id.etContent);
        Spinner sCategory = view.findViewById(R.id.sCategory);
        EditText etNote = view.findViewById(R.id.etNote);
        ImageView ivCalendar = view.findViewById(R.id.ivCalendar);
        TextView tvDate = view.findViewById(R.id.tvDate);

        ArrayAdapter ad = new ArrayAdapter(mcontext, android.R.layout.simple_spinner_item, categories.list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(ad);

        etContent.setText(task.getContent());
        etNote.setText(task.getNote());
        tvDate.setText(task.getDate());
        sCategory.setSelection(categories.list.indexOf(task.getCategory()));
        ivCalendar.setOnClickListener(view1 -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(mcontext,
                    (view2, year1, monthOfYear, dayOfMonth) -> tvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1),
                    year, month, day);
            datePickerDialog.show();
        });
        tvDate.setOnClickListener(view1 -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(mcontext,
                    (view2, year1, monthOfYear, dayOfMonth) -> tvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1),
                    year, month, day);
            datePickerDialog.show();
        });

        builder.setPositiveButton("Update", (dialog, which)->{
           String content = etContent.getText().toString();
           String date = tvDate.getText().toString();
           String category = sCategory.getSelectedItem().toString();
           String note = etNote.getText().toString();
           task.setContent(content);
           task.setDate(date);
           task.setCategory(category);
           task.setNote(note);
           taskDao.update(content, date, category, note, task.getId());
           notifyItemChanged(position);
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}
