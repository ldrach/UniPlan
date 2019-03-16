 package com.example.uniplan2;

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

 public class MainActivity extends AppCompatActivity {

     ListView taskListView;
     //days array will be the first array which will organize the list items by their due date
     ArrayAdapter<String> daysAdapter;
     String[] days = {"Jan 30", "Feb 1", "Feb 2"};

     private String taskName;
     private String taskDescription;
     private String taskDueDate;

     private int day;
     private int month;
     private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



        //if( "some boolean value that states whether you've been to the other activity or not")
        //Intent intent = getIntent();
        //days[0] = intent.getStringExtra("dueDay");
        taskListView = (ListView) findViewById(R.id.taskListView);
        daysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, days);
        taskListView.setAdapter(daysAdapter);

    }


}
