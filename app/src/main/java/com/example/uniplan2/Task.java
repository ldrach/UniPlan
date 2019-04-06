package com.example.uniplan2;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "task")
public class Task {

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name= "id")
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

    public int getId(){
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

