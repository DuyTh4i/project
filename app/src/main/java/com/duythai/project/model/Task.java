package com.duythai.project.model;

import androidx.annotation.*;
import androidx.room.*;

import java.text.*;
import java.util.*;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;
    @NonNull
    @ColumnInfo(name = "content")
    private String content;
    @NonNull
    @ColumnInfo(name = "category")
    private String category;
    @NonNull
    @ColumnInfo(name = "note")
    private String note;
    @NonNull
    @ColumnInfo(name = "date")
    private String date;
    @NonNull
    @ColumnInfo(name = "isChecked")
    private boolean isDone;

    public Task(@NonNull String content, @NonNull String category, @NonNull String note, @NonNull String date, boolean isDone) {
        this.content = content;
        this.category = category;
        this.note = note;
        this.date = date;
        this.isDone = isDone;
    }

    public static Date stringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public void setNote(@NonNull String note) {
        this.note = note;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
