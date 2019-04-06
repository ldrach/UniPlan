package com.example.uniplan2;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@android.arch.persistence.room.Database(entities = {Task.class, Class.class}, version = 5)
public abstract class Database extends RoomDatabase {

    private static Database INSTANCE;

    public abstract TaskDao taskDao();
    public abstract ClassDao classDao();

    public static Database getFileDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "The Database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){INSTANCE=null;}

}
