package com.example.uniplan2;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.jar.Attributes;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "task")
public class Task {

    //Primary key needs to be set up properly
    @PrimaryKey(autoGenerate = true)


    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "notes")
    public String notes;

    @ColumnInfo(name = "date")
    public String date;

    public String displayTask(){
        String str = name + "\n" + notes;
        return str;
    }

    public int getClassId(){
        return id;
    }
    public String getTaskName(){
        return name;
    }
    public String getTaskNotes(){
        return notes;
    }
    public String getTaskDate() {
        return date;
    }


}

