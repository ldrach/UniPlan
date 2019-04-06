package com.example.uniplan2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    public List<Class> classList;
    public int classCount;
    private Toolbar mTopToolbar;

    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Testline

        /*Needs on touch listener that launches editClass Activity for that class*/
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_view_classes);


        mTopToolbar = findViewById(R.id.basic_toolbar);
        setSupportActionBar(mTopToolbar);



        backBtn = findViewById(R.id.viewClassesBackButton);
        classListView = findViewById(R.id.ClassList);

        db = Database.getFileDatabase(getApplicationContext());
        classList = db.classDao().getAll();

        updateClassArrays();


        classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classDisplay);
        classListView.setAdapter(classAdapter);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });

       /* classListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Log.d("position","position = " +position);
                Intent i = new Intent(getBaseContext(), editClass.class);
                //Add method to get data from selected class to populate the EditClass form
                startActivity(i);
                return true;
            }

        });*/

        updateClassArrays();


    }//End of onCreate

    private void updateClassArrays(){
        classList = db.classDao().getAll();
        classCount = classList.size();
        Class currentClass;

        classes = new String[10];
        classDisplay = new String[10];
        for(int i=0;i<10;i++) {
            classes[i] = "Class Test";
            classDisplay[i] = "";
        }

        if (classCount !=0) {
            for (int i = 0; i < classCount; i++) {
                currentClass = classList.get(i);
                classes[i] = currentClass.getClassName();
                classDisplay[i] = currentClass.getClassName();
            }
        }
    }
}
