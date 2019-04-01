package com.example.uniplan2;

import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {Task.class, Class.class}, version = 2)
public abstract class Database extends RoomDatabase {


public abstract TaskDao taskDao();
public abstract ClassDao classDao();
}
