package com.example.uniplan2;

import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class AddTask extends AppCompatActivity implements View.OnClickListener {

    public Button btnDatePicker;
    public EditText txtDate;
    private int mYear, mMonth, mDay;

    //Values to store in task table
    public String taskName;
    public String notes;
    public int id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        final EditText taskNameEditText =  findViewById(R.id.taskNameEditText);
        final EditText taskDescriptionEditText =  findViewById(R.id.taskDescriptionEditText);


        btnDatePicker=findViewById(R.id.dateButton);
        txtDate=findViewById(R.id.dueDateEdit);

        btnDatePicker.setOnClickListener(this);

    //~~~~~~~~~~~~~Once the done button is clicked~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        FloatingActionButton addTaskDoneButton = findViewById(R.id.addTaskDoneButton);
        addTaskDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskName = taskNameEditText.getText().toString();
                notes = taskDescriptionEditText.getText().toString();

                Intent i = new Intent(getBaseContext(), MainActivity.class );
                i.putExtra("taskName", taskName);
                i.putExtra("notes", notes);
                i.putExtra("day", mDay);
                i.putExtra("month", mMonth);
                i.putExtra("year", mYear);

                startActivity(i);


            }
        });
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    }

    @Override
    public void onClick(View v) {
        if(v==btnDatePicker){

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txtDate.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
