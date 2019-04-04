package com.example.uniplan2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

public class tasksViewModel extends AndroidViewModel {
    public final String[] datesArray=new String[100];
    private int taskCount;

    private Database db;
    public tasksViewModel(@NonNull Application application) {
        super(application);
        createDb();

        populateDatesArray();
    }

    private void createDb() {
        db = Database.getFileDatabase(this.getApplication());

        //DatabaseInitializer.populateAsync(db);
    }


    private void populateDatesArray(){
        for (int i = 0; i<taskCount; i++) {
            Task currentTask = db.taskDao().findTask(i);
            String currentDate = currentTask.date;

            datesArray[i] = currentDate;
        }
    }

    /*
    !!!!!!!!!!!!!!!!!!! TASK ID NEEDS TO START AT 0 AND INCREMENT BY 1 EACH TIME FOR THIS TO
    WORK. ID HAS NOT BEEN INITIALIZED YET.
     */
    private String[] getTaskDescriptions(){
        String[] descriptions = new String[taskCount+1];
        for (int i = 0; i<taskCount; i++) {

            Task currentTask = db.taskDao().findTask(i);
            descriptions[i] = currentTask.displayTask();
        }
        return descriptions;
    }


}
