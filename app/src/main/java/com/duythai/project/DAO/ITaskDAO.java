package com.duythai.project.DAO;

import androidx.room.*;
import com.duythai.project.model.Task;
import java.util.List;

@Dao
public interface ITaskDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);
    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table")
    List<Task> getAll();

    @Query("SELECT date FROM task_table")
    List<String> getDates();
    @Query("SELECT category FROM task_table")
    List<String> getCategories();
    @Query("SELECT content FROM task_table")
    List<String> getContents();

    @Query("DELETE FROM task_table WHERE content = :content AND date = :date AND category = :category")
    void delete(String content, String date, String category);

    @Query("UPDATE task_table SET content = :content, date = :date, category = :category, note= :note WHERE id = :id")
    void update(String content, String date, String category, String note, long id);
}
