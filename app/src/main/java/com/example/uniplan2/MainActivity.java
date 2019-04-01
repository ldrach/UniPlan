 package com.example.uniplan2;

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


import java.util.List;
import java.util.Date;

 public class MainActivity extends AppCompatActivity {

     public Database db;
     ListView taskListView;

     private String taskName;
     private String taskDescription;
     private String taskDueDate;
     private int taskCount;

     private int day;
     private int month;
     private int year;

     ArrayAdapter<String> tasksAdapter;
     //Array of all dates for tasks with task info
     String[] taskDates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDates = new String[100];
        if(taskCount>0){
            for (int i = 0; i<taskCount; i++) {
                Task currentTask = db.taskDao().findTask(i);
                String currentDate = currentTask.date;

                taskDates[i] = currentDate;
            }
        }


        //Instance of room database implemented here~~~~~~~~~~~~~~~~~~~
        db = Room.databaseBuilder(getApplicationContext(),
                Database.class, "Database").allowMainThreadQueries().fallbackToDestructiveMigration().build();


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




        Intent intent = getIntent();

        //Populating database with task data fields

        try {
            Task task = new Task();
            task.name = intent.getStringExtra("name");
            task.id = taskCount + 1;
            task.notes = intent.getStringExtra("notes");
            year = intent.getIntExtra("year", 0);
            month = intent.getIntExtra("month", 0);
            day = intent.getIntExtra("day", 0);
            task.date = "" + day + "/" + month + "/" + year;


            db.taskDao().insert(task);



        taskListView = findViewById(R.id.taskListView);
        tasksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskDates);
        //taskListView.setAdapter(tasksAdapter);
        }catch(NullPointerException e){}
    }




}
