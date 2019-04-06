package com.example.uniplan2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ClassDao {


    @Query("SELECT * FROM class")
    List<Class> getAll();

    @Query("SELECT * FROM class WHERE name LIKE :className LIMIT 1")
    Class findByName(String className);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Class newClass);

    @Update
    void updateClass(Class newClass);

    @Delete
    void delete(Class newClass);

    @Query("Delete FROM class")
    void deleteAll();
}
