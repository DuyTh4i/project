package com.duythai.project.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import androidx.room.Room;

import android.view.*;
import android.widget.TextView;

import com.duythai.project.DAO.AppDatabase;
import com.duythai.project.DAO.ITaskDAO;
import com.duythai.project.R;
import com.duythai.project.apdapter.TaskAdapter;
import com.duythai.project.model.Task;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    RecyclerView rvPersonal, rvWork, rvFinance, rvFamily, rvHealth;
    TextView tvPersonal, tvWork, tvFinance, tvFamily, tvHealth;
    public static ArrayList<Task> tasks;
    ArrayList<Task> personal, work, finance, family, health;
    public static TaskAdapter personalAdapter, workAdapter, financeAdapter, familyAdapter, healthAdapter;
    AppDatabase db;
    ITaskDAO taskDAO;

    public CategoryFragment(){}

    public interface OnTaskAddedListener{
        void onTaskAddedCategory(Task task);
        boolean onNavigationItemSelectedCategory(@NonNull MenuItem item);
    }

    public void addTaskToList(Task task){
        tasks.add(task);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        View cardLayout = inflater.inflate(R.layout.layout_item, container, false);

        tvPersonal = root.findViewById(R.id.tvPersonal);
        tvWork = root.findViewById(R.id.tvWork);
        tvFamily = root.findViewById(R.id.tvFamily);
        tvFinance = root.findViewById(R.id.tvFinance);
        tvHealth = root.findViewById(R.id.tvHealth);

        rvPersonal = root.findViewById(R.id.rvPersonal);
        rvWork = root.findViewById(R.id.rvWork);
        rvFamily = root.findViewById(R.id.rvFamily);
        rvFinance = root.findViewById(R.id.rvFinance);
        rvHealth = root.findViewById(R.id.rvHealth);

        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "prjdb").allowMainThreadQueries().build();
        taskDAO = db.taskDAO();

        personal = new ArrayList<>();
        work = new ArrayList<>();
        finance = new ArrayList<>();
        family = new ArrayList<>();
        health = new ArrayList<>();

        personalAdapter = new TaskAdapter(requireContext());
        workAdapter = new TaskAdapter(requireContext());
        financeAdapter = new TaskAdapter(requireContext());
        familyAdapter = new TaskAdapter(requireContext());
        healthAdapter = new TaskAdapter(requireContext());

        ArrayList<Task> list = new ArrayList<>(db.taskDAO().getAll());
        for(Task task : list){
            loadTask(task.getContent(), task.getCategory(), task.getDate(), task.getNote());
        }

        personalAdapter.getAll(personal);
        workAdapter.getAll(work);
        financeAdapter.getAll(finance);
        familyAdapter.getAll(family);
        healthAdapter.getAll(health);

        LinearLayoutManager man1 = new LinearLayoutManager(requireContext());
        LinearLayoutManager man2 = new LinearLayoutManager(requireContext());
        LinearLayoutManager man3 = new LinearLayoutManager(requireContext());
        LinearLayoutManager man4 = new LinearLayoutManager(requireContext());
        LinearLayoutManager man5 = new LinearLayoutManager(requireContext());

        rvPersonal.setLayoutManager(man1);
        rvWork.setLayoutManager(man2);
        rvFamily.setLayoutManager(man3);
        rvFinance.setLayoutManager(man4);
        rvHealth.setLayoutManager(man5);

        rvPersonal.setAdapter(personalAdapter);
        rvWork.setAdapter(workAdapter);
        rvFamily.setAdapter(familyAdapter);
        rvFinance.setAdapter(financeAdapter);
        rvHealth.setAdapter(healthAdapter);

        tvPersonal.setText("Personal(" + personal.size() + ")");
        tvWork.setText("Work(" + work.size() + ")");
        tvFamily.setText("Family(" + family.size() + ")");
        tvFinance.setText("Finance(" + finance.size() + ")");
        tvHealth.setText("Health(" + health.size() + ")");

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                personal.remove(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvPersonal);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                work.remove(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvWork);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                familyAdapter.remove(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvFamily);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                financeAdapter.remove(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvFinance);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                healthAdapter.remove(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvHealth);

        ViewGroup.LayoutParams params1 = rvPersonal.getLayoutParams();
        ViewGroup.LayoutParams params2 = rvWork.getLayoutParams();
        ViewGroup.LayoutParams params3 = rvFamily.getLayoutParams();
        ViewGroup.LayoutParams params4 = rvFinance.getLayoutParams();
        ViewGroup.LayoutParams params5 = rvHealth.getLayoutParams();

        tvPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params1.height!=0)
                    params1.height=0;
                else
                    params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvPersonal.setLayoutParams(params1);
            }
        });

        tvWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params2.height!=0)
                    params2.height=0;
                else
                    params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvWork.setLayoutParams(params2);
            }
        });

        tvFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params3.height!=0)
                    params3.height=0;
                else
                    params3.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvFinance.setLayoutParams(params3);
            }
        });

        tvFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params4.height!=0)
                    params4.height=0;
                else
                    params4.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvFamily.setLayoutParams(params4);
            }
        });

        tvHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(params5.height!=0)
                    params5.height=0;
                else
                    params5.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvHealth.setLayoutParams(params5);
            }
        });
        return root;
    }

    public void loadTask(String content, String category, String note, String date) {
        Task task = new Task(content, category, note, date,false);
        switch (category) {
            case "Personal":
                personal.add(task);
                break;
            case "Work":
                work.add(task);
                break;
            case "Health":
                health.add(task);
                break;
            case "Finance":
                finance.add(task);
                break;
            case "Family":
                family.add(task);
                break;
        }
    }
}