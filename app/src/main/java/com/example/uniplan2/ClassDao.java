package com.example.uniplan2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ClassDao {
    @Query("SELECT * FROM class")
    List<Class> getAll();

    @Query("SELECT * FROM class WHERE name LIKE :className LIMIT 1")
    Class findByName(String className);


}
