package com.duythai.project.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duythai.project.DAO.AppDatabase;
import com.duythai.project.DAO.ITaskDAO;
import com.duythai.project.R;
import com.duythai.project.apdapter.TaskAdapter;
import com.duythai.project.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NormalFragment extends Fragment {

    RecyclerView rvToday, rvTomorrow, rvUpcoming, rvLate;
    TextView tvToday, tvTomorrow, tvUpcoming, tvLate;
    public static ArrayList<Task> tasks;
    ArrayList<Task> today, tomorrow, late, upcoming;
    public static TaskAdapter todayAdapter, tomorrowAdapter, lateAdapter, upcomingAdapter;
    AppDatabase db;
    ITaskDAO taskDAO;

    public interface OnTaskAddedListener{
        void onTaskAddedNormal(Task task);
        boolean onNavigationItemSelectedNormal(@NonNull MenuItem item);
    }

    public void addTaskToList(Task task){
        tasks.add(task);
        todayAdapter.notifyItemInserted(tasks.size() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_normal, container, false);
        View cardLayout = inflater.inflate(R.layout.layout_item, container, false);

        tvToday = root.findViewById(R.id.tvToday);
        tvTomorrow = root.findViewById(R.id.tvTomorrow);
        tvUpcoming = root.findViewById(R.id.tvUpcoming);
        tvLate = root.findViewById(R.id.tvLate);

        rvToday = root.findViewById(R.id.rvToday);
        rvTomorrow = root.findViewById(R.id.rvTomorrow);
        rvUpcoming = root.findViewById(R.id.rvUpcoming);
        rvLate = root.findViewById(R.id.rvLate);

        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "prjdb").allowMainThreadQueries().build();
        taskDAO = db.taskDAO();

        today = new ArrayList<>();
        tomorrow = new ArrayList<>();
        upcoming = new ArrayList<>();
        late = new ArrayList<>();

        todayAdapter = new TaskAdapter(requireContext());
        tomorrowAdapter = new TaskAdapter(requireContext());
        upcomingAdapter = new TaskAdapter(requireContext());
        lateAdapter = new TaskAdapter(requireContext());

        ArrayList<Task> list = new ArrayList<>(db.taskDAO().getAll());
        for(Task task : list){
            loadTask(task.getContent(), task.getCategory(), task.getDate(), task.getNote());
        }

        todayAdapter.getAll(today);
        tomorrowAdapter.getAll(tomorrow);
        upcomingAdapter.getAll(upcoming);
        lateAdapter.getAll(late);

        LinearLayoutManager man1 = new LinearLayoutManager(requireContext());
        LinearLayoutManager man2 = new LinearLayoutManager(requireContext());
        LinearLayoutManager man3 = new LinearLayoutManager(requireContext());
        LinearLayoutManager man4 = new LinearLayoutManager(requireContext());

        rvToday.setLayoutManager(man1);
        rvTomorrow.setLayoutManager(man2);
        rvUpcoming.setLayoutManager(man3);
        rvLate.setLayoutManager(man4);

        rvToday.setAdapter(todayAdapter);
        rvTomorrow.setAdapter(tomorrowAdapter);
        rvUpcoming.setAdapter(upcomingAdapter);
        rvLate.setAdapter(lateAdapter);

        tvToday.setText("Today(" + today.size() + ")");
        tvTomorrow.setText("Tomorrow(" + tomorrow.size() + ")");
        tvUpcoming.setText("Upcoming(" + upcoming.size() + ")");
        tvLate.setText("Late(" + late.size() + ")");

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                todayAdapter.remove(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvToday);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                tomorrowAdapter.remove(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvTomorrow);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                upcomingAdapter.remove(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvUpcoming);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                lateAdapter.remove(viewHolder.getAdapterPosition());
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

        tvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params2.height!=0)
                    params2.height=0;
                else
                    params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvTomorrow.setLayoutParams(params2);
            }
        });

        tvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params3.height!=0)
                    params3.height=0;
                else
                    params3.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvUpcoming.setLayoutParams(params3);
            }
        });

        tvToday.setOnClickListener(new View.OnClickListener() {
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

    void loadTask(String content, String category, String due, String note){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(due));
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
        Calendar currentDate = Calendar.getInstance();
        Task addedTask = new Task(content, category, note, due, false);
        if (calendar.get(Calendar.DAY_OF_YEAR) < currentDate.get(Calendar.DAY_OF_YEAR))
            late.add(addedTask);
        else if (calendar.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR))
            today.add(addedTask);
        else if (calendar.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR) + 1)
            tomorrow.add(addedTask);
        else
            upcoming.add(addedTask);
    }
}