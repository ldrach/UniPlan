package com.example.uniplan2;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import java.util.Calendar;


public class AddClass extends AppCompatActivity implements View.OnClickListener{

    EditText className, fromTimePicker1, toTimePicker1, fromTimePicker2, toTimePicker2, fromTimePicker3,
            toTimePicker3, fromTimePicker4, toTimePicker4, fromTimePicker5, toTimePicker5, classRoom1,
            classRoom2, classRoom3, classRoom4, classRoom5;
    private int mHour, mMinute;
    public String classNameString;
    private int fromHour1, fromHour2, fromHour3, fromHour4, fromHour5, toHour1, toHour2, toHour3,
    toHour4, toHour5, fromMin1, fromMin2, fromMin3, fromMin4, fromMin5, toMin1, toMin2, toMin3,
            toMin4, toMin5;
    private int spinner1, spinner2, spinner3, spinner4, spinner5;
    public String room1, room2, room3, room4, room5;
    public boolean classAdded;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_class);

        classAdded = false;

        className = findViewById(R.id.classNameEditText);

        fromTimePicker1 = findViewById(R.id.fromTimeEdit1);
        toTimePicker1 = findViewById(R.id.toTimeEdit1);
        fromTimePicker2 = findViewById(R.id.fromTimeEdit2);
        toTimePicker2 = findViewById(R.id.toTimeEdit2);
        fromTimePicker3 = findViewById(R.id.fromTimeEdit3);
        toTimePicker3 = findViewById(R.id.toTimeEdit3);
        fromTimePicker4 = findViewById(R.id.fromTimeEdit4);
        toTimePicker4 = findViewById(R.id.toTimeEdit4);
        fromTimePicker5 = findViewById(R.id.fromTimeEdit5);
        toTimePicker5 = findViewById(R.id.toTimeEdit5);

        classRoom1 = findViewById(R.id.room1EditText);
        classRoom2 = findViewById(R.id.room2EditText);
        classRoom3 = findViewById(R.id.room3EditText);
        classRoom4 = findViewById(R.id.room4EditText);
        classRoom5 = findViewById(R.id.room5EditText);

        final Spinner daySpinner1 = findViewById(R.id.classDay1Spinner);
        final Spinner daySpinner2 = findViewById(R.id.classDay2Spinner);
        final Spinner daySpinner3 = findViewById(R.id.classDay3Spinner);
        final Spinner daySpinner4 = findViewById(R.id.classDay4Spinner);
        final Spinner daySpinner5 = findViewById(R.id.classDay5Spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dayOfWeek, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner1.setAdapter(adapter);
        daySpinner2.setAdapter(adapter);
        daySpinner3.setAdapter(adapter);
        daySpinner4.setAdapter(adapter);
        daySpinner5.setAdapter(adapter);



        fromTimePicker1.setOnClickListener(this);
        toTimePicker1.setOnClickListener(this);
        fromTimePicker2.setOnClickListener(this);
        toTimePicker2.setOnClickListener(this);
        fromTimePicker3.setOnClickListener(this);
        toTimePicker3.setOnClickListener(this);
        fromTimePicker4.setOnClickListener(this);
        toTimePicker4.setOnClickListener(this);
        fromTimePicker5.setOnClickListener(this);
        toTimePicker5.setOnClickListener(this);




        FloatingActionButton addClassDoneButton = findViewById(R.id.addClassDoneButton);
        addClassDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                classNameString = className.getText().toString();

                room1 = classRoom1.getText().toString();
                room2 = classRoom2.getText().toString();
                room3 = classRoom3.getText().toString();
                room4 = classRoom4.getText().toString();
                room5 = classRoom5.getText().toString();

                spinner1 = daySpinner1.getSelectedItemPosition();
                spinner2 = daySpinner2.getSelectedItemPosition();
                spinner3 = daySpinner3.getSelectedItemPosition();
                spinner4 = daySpinner4.getSelectedItemPosition();
                spinner5 = daySpinner5.getSelectedItemPosition();


                Intent i = new Intent(getBaseContext(), MainActivity.class );
                i.putExtra("className", classNameString);
                i.putExtra("fromHour1", fromHour1);
                i.putExtra("fromHour2", fromHour2);
                i.putExtra("fromHour3", fromHour3);
                i.putExtra("fromHour4", fromHour4);
                i.putExtra("fromHour5", fromHour5);
                i.putExtra("fromMin1", fromMin1);
                i.putExtra("fromMin2", fromMin2);
                i.putExtra("fromMin3", fromMin3);
                i.putExtra("fromMin4", fromMin4);
                i.putExtra("fromMin5", fromMin5);
                i.putExtra("toHour1", toHour1);
                i.putExtra("toHour2", toHour2);
                i.putExtra("toHour3", toHour3);
                i.putExtra("toHour4", toHour4);
                i.putExtra("toHour5", toHour5);
                i.putExtra("toMin1", toMin1);
                i.putExtra("toMin2", toMin2);
                i.putExtra("toMin3", toMin3);
                i.putExtra("toMin4", toMin4);
                i.putExtra("toMin5", toMin5);
                i.putExtra("spinner1", spinner1);
                i.putExtra("spinner2", spinner2);
                i.putExtra("spinner3", spinner3);
                i.putExtra("spinner4", spinner4);
                i.putExtra("spinner5", spinner5);
                i.putExtra("room1", room1);
                i.putExtra("room2", room2);
                i.putExtra("room3", room3);
                i.putExtra("room4", room4);
                i.putExtra("room5", room5);
                i.putExtra("classAdded", classAdded);

                classAdded = true;

                startActivity(i);
            }
        });
    }



    @Override
    public void onClick(View v) {

        if(v == fromTimePicker1){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    fromTimePicker1.setText(String.format("%01d:%02d", hourOfDay, minute));
                    fromHour1 = hourOfDay;
                    fromMin1 = minute;
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == toTimePicker1){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    toTimePicker1.setText(String.format("%01d:%02d", hourOfDay, minute));
                    toHour1 = hourOfDay;
                    toMin1 = minute;
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == fromTimePicker2){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    fromTimePicker2.setText(String.format("%01d:%02d", hourOfDay, minute));
                    fromHour2 = hourOfDay;
                    fromMin2 = minute;
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == toTimePicker2){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    toTimePicker2.setText(String.format("%01d:%02d", hourOfDay, minute));
                    toHour2 = hourOfDay;
                    toMin2 = minute;
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == fromTimePicker3){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    fromTimePicker3.setText(String.format("%01d:%02d", hourOfDay, minute));
                    fromHour3 = hourOfDay;
                    fromMin3 = minute;
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == toTimePicker3){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    toTimePicker3.setText(String.format("%01d:%02d", hourOfDay, minute));
                    toHour3 = hourOfDay;
                    toMin3 = minute;
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == fromTimePicker4){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    fromTimePicker4.setText(String.format("%01d:%02d", hourOfDay, minute));
                    fromHour4 = hourOfDay;
                    fromMin4 = minute;
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == toTimePicker4){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    toTimePicker4.setText(String.format("%01d:%02d", hourOfDay, minute));
                    toHour4 = hourOfDay;
                    toMin4 = minute;
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == fromTimePicker5){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    fromTimePicker5.setText(String.format("%01d:%02d", hourOfDay, minute));
                    fromHour5 = hourOfDay;
                    fromMin5 = minute;
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == toTimePicker5){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    toTimePicker5.setText(String.format("%01d:%02d", hourOfDay, minute));
                    toHour5 = hourOfDay;
                    toMin5 = minute;
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

    }
}
