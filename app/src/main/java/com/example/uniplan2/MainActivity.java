package com.example.uniplan2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public Database db;
    public TaskDao taskDao;
    public ListView taskListView;
    private tasksViewModel mViewModel;
    private Toolbar mTopToolbar;



    public List<Task> taskList;


    private String taskName;
    private String taskDescription;
    private String taskDueDate;

    private int taskCount;
    private String taskAdded;
    private String classAdded;

    private int day;
    private int month;
    private int year;

    ArrayAdapter<String> tasksAdapter;
    //Array of all dates for tasks with task info
    public String[] taskDates;
    public String[] taskDescriptions;
    public String[] taskDisplay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        taskListView = findViewById(R.id.taskListView);
        taskCount = 0;

        //Instance of room database implemented here~~~~~~~~~~~~~~~~~~~
        db = Database.getFileDatabase(getApplicationContext());
        taskList = db.taskDao().getAll();

        taskDates = new String[taskList.size()+5];
        taskDescriptions = new String[taskList.size()+5];
        taskDisplay = new String[taskList.size()+5];
        updateTaskArrays();


        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskDisplay);
        taskListView.setAdapter(tasksAdapter);


        //~~~~~~~~~~~~~~Retrieves intent that started the activity. ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
        //Here we determine whether a class was just added or a task was just added, or neither.
        Intent intent = getIntent();

        //Case 1: A task has been added, and taskAdded contains "t"
        taskAdded = intent.getStringExtra("taskAdded");
        if((taskAdded != null) && taskAdded.equalsIgnoreCase("t")) {
            //setIdFromZero();
            //Populating database with task data fields
            Task task = new Task();
            task.name = intent.getStringExtra("taskName");
            //task.id = taskCount;
            task.notes = intent.getStringExtra("notes");
            year = intent.getIntExtra("year", 0);
            month = intent.getIntExtra("month", 0);
            day = intent.getIntExtra("day", 0);
            task.date = "" + year + "/" + month + "/" + day;

            long index = db.taskDao().insert(task);


        }
        //Case 2: A class has been added, and classAdded contains "t"
        classAdded = intent.getStringExtra("classAdded");
        if((classAdded != null) && classAdded.equalsIgnoreCase("t")) {
            //Populating database with class data fields
            Class classObject = new Class();
            classObject.name = intent.getStringExtra("className");


            db.classDao().insert(classObject);

            /*task.id = taskCount;
            task.notes = intent.getStringExtra("notes");
            year = intent.getIntExtra("year", 0);
            month = intent.getIntExtra("month", 0);
            day = intent.getIntExtra("day", 0);
            task.date = "" + year + "/" + month + "/" + day;

            long index = db.taskDao().insert(task);

*/

        }

        //populates arrays after new task is added
        updateTaskArrays();


        FloatingActionButton fab = findViewById(R.id.addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates a new array with 5 more spots and assigns it back to taskDates & taskDescriptions
                if(taskList.size()>(taskDates.length-1)|| taskList.size()>(taskDescriptions.length-1)){
                    String[] temp = new String[taskDates.length+5];
                    String[] temp2 = new String[taskDescriptions.length+5];
                    String[] temp3 = new String[taskDisplay.length+5];
                    for(int n=0;n<taskDates.length;n++){
                        temp[n] = taskDates[n];
                        temp2[n] = taskDescriptions[n];
                        temp3[n] = taskDisplay[n];
                    }
                    taskDates = temp;
                    taskDescriptions = temp2;
                    taskDisplay = temp3;
                }
                Intent i = new Intent(MainActivity.this, AddTask.class);

                startActivity(i);
            }
        });

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Log.d("position","position = " +position);
                if(taskList.size()!=0) {
                     db.taskDao().delete(taskList.get(position));
                    //backgroundTask bgt = new backgroundTask();
                    //bgt.execute();
                    Toast.makeText(MainActivity.this, "Task Removed", Toast.LENGTH_LONG).show();
                    updateTaskArrays();

                }else
                    Toast.makeText(MainActivity.this, "Nothing To Remove", Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }//End of onCreate method


    private void updateTaskArrays(){
        taskList = db.taskDao().getAll();
        taskCount = taskList.size();
        Task currentTask;
        for(int a=0;a<taskDates.length;a++){
            taskDates[a] = "9999/1/1";
            taskDescriptions[a] = "Description";
            taskDisplay[a] = "";
        }

        if (taskCount !=0) {
            for (int i = 0; i < taskCount; i++) {
                currentTask = taskList.get(i);
                taskDates[i] = currentTask.getTaskDate();
                String str = String.valueOf(currentTask.id);
                str += currentTask.getTaskName();
                str += "\nDescription: " + currentTask.getTaskNotes();
                taskDescriptions[i] = str;
            }
            sortArraysByDate();
            for (int z = 0; z < taskCount; z++) {
                String a = taskDates[z];
                String b = taskDescriptions[z];//.substring(1);
                taskDisplay[z] = a + "\n" + b;
            }
        }else
            taskDisplay[0] = "";
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskDisplay);
        taskListView.setAdapter(tasksAdapter);
    }
    //Sorts arrays, given the date in the taskDates array
    private void sortArraysByDate(){
        String[] str1;
        String[] str2;

        int n=taskDates.length;
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-i-1;j++) {
                str1 = taskDates[j].split("/");
                str2 = taskDates[j+1].split("/");
                //If year 1 is greater than year 2, they swap.
                if(Integer.parseInt(str1[0])>Integer.parseInt(str2[0])){
                    String temp = taskDates[j];
                    String temp2 = taskDescriptions[j];
                    taskDates[j] = taskDates[j+1];
                    taskDescriptions[j] = taskDescriptions[j+1];
                    taskDates[j+1] = temp;
                    taskDescriptions[j+1] = temp2;
                    //If year 1 and 2 are equal, and month 1 is greater than month 2, they swap.
                }
                if((Integer.parseInt(str1[0])==Integer.parseInt(str2[0])) && Integer.parseInt(str1[1])>Integer.parseInt(str2[1])){
                    String temp = taskDates[j];
                    String temp2 = taskDescriptions[j];
                    taskDates[j] = taskDates[j+1];
                    taskDescriptions[j] = taskDescriptions[j+1];
                    taskDates[j+1] = temp;
                    taskDescriptions[j+1] = temp2;
                    //If years 1 and 2, and months 1 and 2 are equal, and day 1 is greater than day 2, they swap.
                }
                if((Integer.parseInt(str1[0])==Integer.parseInt(str2[0])) && (Integer.parseInt(str1[1])==Integer.parseInt(str2[1])) && Integer.parseInt(str1[2])>Integer.parseInt(str2[2])){
                    String temp = taskDates[j];
                    String temp2 = taskDescriptions[j];
                    taskDates[j] = taskDates[j+1];
                    taskDescriptions[j] = taskDescriptions[j+1];
                    taskDates[j+1] = temp;
                    taskDescriptions[j+1] = temp2;
                }
            }
        }
    }

    public void setIdFromZero(){
        taskList = db.taskDao().getAll();
        taskCount = taskList.size();
        Task currentTask;
        for(int i=0;i<taskCount;i++){
            currentTask = taskList.get(i);
            currentTask.id = i;
        }
    }

    public class backgroundTask extends AsyncTask<Integer, String, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            //updateTaskArrays();
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            //tasksAdapter.notifyDataSetChanged();
           // taskListView.setAdapter(tasksAdapter);
        }

    }


/*---------------------------------------------For Menu------------------------------------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_calendar) {
            Intent i = new Intent(getBaseContext(), calendar.class);
            startActivity(i);
            return true;
        }

        else if (id == R.id.action_menu) {
            //Show Menu here
            return true;
        }

        else if (id == R.id.action_add) {
            Intent i = new Intent(getBaseContext(), AddClass.class);
            startActivity(i);
            return true;
        }

        else if (id == R.id.menu_classes){
            Intent i = new Intent(getBaseContext(), ViewClasses.class);
            //i.putExtra("className", className);
            startActivity(i);
            return true;
        }

        else if (id == R.id.menu_settings){
            Intent i = new Intent(getBaseContext(), Settings.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
/*-----------------------------------End of Menu------------------------------------------------*/

}
