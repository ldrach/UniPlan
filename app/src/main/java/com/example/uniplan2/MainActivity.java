 package com.example.uniplan2;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.widget.Toast;


import java.util.List;
import java.util.Date;

 public class MainActivity extends AppCompatActivity {

     public Database db;
     public TaskDao taskDao;
     ListView taskListView;
     private tasksViewModel mViewModel;

     private String taskName;
     private String taskDescription;
     private String taskDueDate;
     private int taskCount;
     private String taskAdded;

     private int day;
     private int month;
     private int year;

     ArrayAdapter<String> tasksAdapter;
     //Array of all dates for tasks with task info
     public String[] taskDates;
     public String[] taskDescriptions;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskListView = findViewById(R.id.taskListView);
        taskCount = 0;

         taskDates = new String[100];
         taskDescriptions = new String[100];
         for(int a=0;a<taskDates.length;a++){
             taskDates[a] = "April 1";
             taskDescriptions[a] = "Description";
         }

          /*
        !!!!!!!!!!!!!!!! NEED TO RESTORE PREVIOUS STATE OF taskCount and instance of database here,
        AFTER TASK COUNT IS INITIALIZED TO 0, OTHERWISE taskDates WILL ALWAYS BE EMPTY AND NO TASKS
        WILL APPEAR ON MAIN PAGE
         */

         //If there are any tasks, populates taskDates array with tasks from database
         if(taskCount>0){
             getTasksArray();
         }

         //Instance of room database implemented here~~~~~~~~~~~~~~~~~~~
         db = Database.getFileDatabase(getApplicationContext());



         //Remove this line after testing
         db.taskDao().deleteAll();

         Intent intent = getIntent();

         //Set task count
         taskAdded = intent.getStringExtra("taskAdded");
         Toast.makeText(this, taskAdded, Toast.LENGTH_SHORT).show();
         if((taskAdded != null) && taskAdded.equalsIgnoreCase("t")) {

             //Populating database with task data fields
             Task task = new Task();
             task.name = intent.getStringExtra("taskName");
             task.id = taskCount;
             task.notes = intent.getStringExtra("notes");
             year = intent.getIntExtra("year", 0);
             month = intent.getIntExtra("month", 0);
             day = intent.getIntExtra("day", 0);
             task.date = "" + day + "/" + month + "/" + year;

             //Database is not storing the data. Either the insert function doesn't work,
             // or the find function does not work, or it is just not running properly
             db.taskDao().insert(task);
             Toast.makeText(this, task.displayTask(),Toast.LENGTH_SHORT).show();

             taskCount++;
         }

         taskDescriptions = getTasksArray();
         Toast.makeText(this, taskDates[0],Toast.LENGTH_SHORT).show();



             tasksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskDates);
             taskListView.setAdapter(tasksAdapter);









        FloatingActionButton fab = findViewById(R.id.addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates a new array with 5 more spots and assigns it back to taskDates
                if(taskCount>(taskDates.length-5)){
                    String[] temp = new String[taskDates.length+5];
                    for(int n=0;n<taskDates.length;n++){
                        temp[n] = taskDates[n];
                    }
                    taskDates = temp;
                }
                Intent i = new Intent(MainActivity.this, AddTask.class);

                startActivity(i);
            }
        });

        Button addClassButton = findViewById(R.id.addClassBtn);
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddClass.class);
                startActivity(i);
            }
        });





    }

    //Populates taskDates with dates of all tasks, and returns an array with the corresponding task
    //names and descriptions
    private String[] getTasksArray(){
        String[] tasks = new String[taskCount];
        for (int i = 0; i<taskCount; i++) {

            Task currentTask = db.taskDao().findTask(i);
            String currentDate = currentTask.date;

            taskDates[i] = currentDate;
            tasks[i] = currentTask.displayTask();
        }
        return tasks;
    }




}
