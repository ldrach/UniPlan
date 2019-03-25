package com.example.uniplan2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "class")
public class Class {

    //cid = class id
    @PrimaryKey
    public int cid;

    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "roomNum")
    public String roomNum;

    @ColumnInfo(name = "color")
    public String color;

    //Day 1 Start
    @ColumnInfo(name = "day1S")
    public Date day1S;

    //Day 1 End
    @ColumnInfo(name = "day1E")
    public int day1E;

    @ColumnInfo(name = "day2S")
    public int day2S;

    @ColumnInfo(name = "day2E")
    public int day2E;

    @ColumnInfo(name = "day3S")
    public int day3S;

    @ColumnInfo(name = "day3E")
    public int day3E;

    @ColumnInfo(name = "day4S")
    public int day4S;

    @ColumnInfo(name = "day4E")
    public int day4E;

    @ColumnInfo(name = "day5S")
    public int day5S;

    @ColumnInfo(name = "day5E")
    public int day5E;

}
