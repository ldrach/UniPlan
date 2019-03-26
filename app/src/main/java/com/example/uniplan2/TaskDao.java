package com.example.uniplan2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE id='taskID'")
    Task findTask(String taskID);

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

}