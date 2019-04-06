package com.example.uniplan2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class ViewClasses extends AppCompatActivity {

    public Database db;
    public ListView classListView;
    ArrayAdapter<String> classAdapter;
    public String[] classes;
    public String[] classDisplay;
    public boolean classAdded;
    public List<Class> classList;
    public int classCount;

    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*Needs on touch listener that launches editClass Activity for that class*/
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_view_classes);
        classAdded=false;

        backBtn = findViewById(R.id.viewClassesBackButton);

        /*db = Database.getFileDatabase(getApplicationContext());
        classList = db.classDao().getAll();

        classes = new String[10];
        classDisplay = new String[10];
        for(int i=0;i<10;i++) {
            classes[i] = "Class Test";
        }

        classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classDisplay);
        classListView.setAdapter(classAdapter);

        Intent intent = getIntent();

        classAdded = intent.getBooleanExtra("classAdded", classAdded);

        if(classAdded=true) {

            //Populating database with class data fields
            Class newClass = new Class();
            newClass.name = intent.getStringExtra("className");


            long index = db.classDao().insert(newClass);
            Log.d("position","Index = " + index);


        }*/

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });

        //updateClassArrays();
    }//End of onCreate

    private void updateClassArrays(){
        classList = db.classDao().getAll();
        classCount = classList.size();
        Class currentClass;

        if (classCount !=0) {
            for (int i = 0; i < classCount; i++) {
                currentClass = classList.get(i);
                String str = String.valueOf(currentClass.id);
                str += currentClass.getClassName();
                classes[i] = str;
            }
        }
    }
}
