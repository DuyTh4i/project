package com.duythai.project.DAO;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.duythai.project.model.Task;
import com.duythai.project.utils.DateConverter;

import java.util.Date;
import java.util.List;

public class TaskRepository {
    private TaskDAO taskDAO;
    private LiveData<List<Task>> todayTasks, tomorrowTasks, upcomingTasks, lateTasks;

    public TaskRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDAO = db.taskDAO();
        todayTasks = taskDAO.getTodayTask(DateConverter.getCurrentDate());
        tomorrowTasks = taskDAO.getTomorrowTask(DateConverter.getTomorrow());
        upcomingTasks = taskDAO.getUpcomingTask(DateConverter.getTomorrow());
        lateTasks = taskDAO.getLateTask(DateConverter.getCurrentDate());
    }

    public LiveData<List<Task>> getTodayTasks(Date date){ return todayTasks;}

    public LiveData<List<Task>> getLateTasks(Date date) {
        return lateTasks;
    }
    public LiveData<List<Task>> getTomorrowTasks(Date date) {
        return tomorrowTasks;
    }
    public LiveData<List<Task>> getUpcomingTasks(Date date) {
        return upcomingTasks;
    }

    public void insert(Task task){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDAO.insert(task);
        });
    }

    public void delete(long id){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDAO.delete(id);
        });
    }
}
