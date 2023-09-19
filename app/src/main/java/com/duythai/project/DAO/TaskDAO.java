package com.duythai.project.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.duythai.project.model.Task;
import com.duythai.project.utils.DateConverter;

import java.util.Date;
import java.util.List;

@Dao
public interface TaskDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @TypeConverters({DateConverter.class})
    void insert(Task task);
    @Query("DELETE FROM task_table")
    void deleteAll();
    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAll();
    @Query("SELECT * FROM task_table WHERE date = :date")
    LiveData<List<Task>> getTodayTask(Date date);
    @Query("SELECT * FROM task_table WHERE date = :date")
    LiveData<List<Task>> getTomorrowTask(Date date);
    @Query("SELECT * FROM task_table WHERE date < :date")
    LiveData<List<Task>> getLateTask(Date date);
    @Query("SELECT * FROM task_table WHERE date > :date")
    LiveData<List<Task>> getUpcomingTask(Date date);
    @Query("SELECT * FROM task_table WHERE category = :category")
    LiveData<List<Task>> getTasksByCategory(String category);
    @Query("DELETE FROM task_table WHERE id = :id")
    void delete(long id);
    @Update
    void update(Task task);
}
