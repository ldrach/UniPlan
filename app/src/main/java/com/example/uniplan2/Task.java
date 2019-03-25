package com.example.uniplan2;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.jar.Attributes;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey
    public int tid;

    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "classID")
    public int classID;

    @ColumnInfo(name = "description")
    public int description;

    @ColumnInfo(name = "date")
    public Date date;


}

