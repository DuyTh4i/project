package com.duythai.project.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.duythai.project.DAO.TaskRepository;
import com.duythai.project.model.Task;
import com.duythai.project.utils.DateConverter;

import java.util.Date;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;
    private LiveData<List<Task>> todayTasks, tomorrowTasks, upcomingTasks, lateTasks, personalTasks, workTasks, healthTasks, financeTasks, familyTasks;;
    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        todayTasks = taskRepository.getTodayTasks(DateConverter.getCurrentDate());
        tomorrowTasks = taskRepository.getTomorrowTasks(DateConverter.getTomorrow());
        upcomingTasks = taskRepository.getUpcomingTasks(DateConverter.getTomorrow());
        lateTasks = taskRepository.getLateTasks(DateConverter.getCurrentDate());
        personalTasks = taskRepository.getTasksByCategory("Personal");
        workTasks = taskRepository.getTasksByCategory("Work");
        familyTasks = taskRepository.getTasksByCategory("Family");
        financeTasks = taskRepository.getTasksByCategory("Finance");
        healthTasks = taskRepository.getTasksByCategory("Health");
    }

    public LiveData<List<Task>> getTomorrowTasks(Date date) {
        return tomorrowTasks;
    }

    public LiveData<List<Task>> getLateTasks(Date date) {
        return lateTasks;
    }

    public LiveData<List<Task>> getTodayTasks(Date date) {
        return todayTasks;
    }

    public LiveData<List<Task>> getUpcomingTasks(Date date) {
        return upcomingTasks;
    }

    public LiveData<List<Task>> getTasksByCategory(String category) {
        switch (category){
            case "Personal":
                return personalTasks;
            case "Work":
                return workTasks;
            case "Health":
                return healthTasks;
            case "Finance":
                return financeTasks;
            default:
                return familyTasks;
        }
    }

    public void delete(long id) {
        taskRepository.delete(id);
    }

    public void insert(Task task){
        taskRepository.insert(task);
    }

    public void update(Task task) {
        taskRepository.update(task);
    }
}
