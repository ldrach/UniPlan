 package com.example.uniplan2;

import android.arch.persistence.room.Room;
import android.content.Intent;
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
     String[] taskDates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(taskCount>0){
            for (int i = 0; i<taskCount; i++) {
                Task currentTask = db.taskDao().findTask(i);
                Date currentDate = currentTask.date;
                int currentDay = currentDate.getDay();
                int currentMonth = currentDate.getMonth();
                int currentYear = currentDate.getYear();

                taskDates[i] = " " + currentDay + " / " + currentMonth + " / " + currentYear;
            }
        }

        //Instance of room database implemented here~~~~~~~~~~~~~~~~~~~
        db = Room.databaseBuilder(getApplicationContext(),
                Database.class, "Database").build();


        FloatingActionButton fab = findViewById(R.id.addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        Task task = new Task();
        task.name = intent.getStringExtra("name");
        task.id = taskCount+1;
        task.notes = intent.getStringExtra("notes");
        task.date = new Date(intent.getIntExtra("year", 0), intent.getIntExtra("month", 0), intent.getIntExtra("day", 0));

        db.taskDao().insert(task);

        taskListView = (ListView) findViewById(R.id.taskListView);
        tasksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskDates);
        taskListView.setAdapter(tasksAdapter);

    }


}
