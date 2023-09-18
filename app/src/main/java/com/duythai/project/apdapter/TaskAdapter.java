package com.duythai.project.apdapter;

import android.annotation.SuppressLint;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.duythai.project.model.Task;

@SuppressWarnings("unchecked")
public class TaskAdapter extends ListAdapter<Task, TaskViewHolder> {

    public TaskAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TaskViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Task getItem(int position) {
        return super.getItem(position);
    }

    /*
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
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        Task task = list.get(position);
        taskDao.delete(task.getContent(), task.getDate(), task.getCategory());
        list.remove(position);
        notifyItemRemoved(position);
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
*/

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
