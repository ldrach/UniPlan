 package com.example.uniplan2;

 import android.content.Intent;
 import android.content.pm.ActivityInfo;
 import android.os.Bundle;
 import android.support.design.widget.FloatingActionButton;
 import android.support.v7.app.AppCompatActivity;
 import android.support.v7.widget.Toolbar;
 import android.view.Menu;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.ListView;
 import android.widget.Toast;

 import java.util.List;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

         mTopToolbar = findViewById(R.id.my_toolbar);
         setSupportActionBar(mTopToolbar);

        taskListView = findViewById(R.id.taskListView);
        taskCount = 0;

         taskDates = new String[10];
         taskDescriptions = new String[10];
         for(int a=0;a<taskDates.length;a++){
             taskDates[a] = "April 1";
             taskDescriptions[a] = "Description";
         }
         tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskDates);
         taskListView.setAdapter(tasksAdapter);
          /*
        !!!!!!!!!!!!!!!! NEED TO RESTORE PREVIOUS STATE OF taskCount and instance of database here,
        AFTER TASK COUNT IS INITIALIZED TO 0, OTHERWISE taskDates WILL ALWAYS BE EMPTY AND NO TASKS
        WILL APPEAR ON MAIN PAGE
         */


         //Instance of room database implemented here~~~~~~~~~~~~~~~~~~~
         db = Database.getFileDatabase(getApplicationContext());
         taskList = db.taskDao().getAll();
         updateTaskArrays();
/*
         //Grasping at straws for a fix, this might have been it. Will come up with a better solution later
         db.taskDao().deleteAll();
         int current=0;
         while(current<taskList.size()){
             db.taskDao().insert(taskList.get(current));
             current++;
         }
*/



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

             db.taskDao().insert(task);

             taskCount++;
         }


 //Issues found here
         //populates arrays after new task is added
         updateTaskArrays();







        FloatingActionButton fab = findViewById(R.id.addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates a new array with 5 more spots and assigns it back to taskDates & taskDescriptions
                if(taskCount>(taskDates.length-1)|| taskCount>(taskDescriptions.length-1)){
                    String[] temp = new String[taskDates.length+5];
                    String[] temp2 = new String[taskDescriptions.length+5];
                    for(int n=0;n<taskDates.length;n++){
                        temp[n] = taskDates[n];
                        temp2[n] = taskDescriptions[n];
                    }
                    taskDates = temp;
                    taskDescriptions = temp2;
                }
                Intent i = new Intent(MainActivity.this, AddTask.class);

                startActivity(i);
            }
        });

    }//End of onCreate method

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

    private void updateTaskArrays(){
        taskList = db.taskDao().getAll();
        taskCount = taskList.size();
        Task currentTask;
        String str = "";
         for(int i=0;i<taskCount;i++){
             currentTask = taskList.get(i);
             taskDates[i] = currentTask.getTaskDate();
             str = currentTask.getTaskName();
             str += "\nDescription: " + currentTask.getTaskNotes();
             taskDescriptions[i] = str;
         }
    }
//Sorts arrays, given the date in the taskDates array
    private void sortArraysByDate(){
         String tempDate;
         String str;
         String[] temp1 = new String[taskDates.length];
         String[] temp2 = new String[taskDescriptions.length];
        for(int n=0;n<taskDates.length;n++){
           str = taskDates[n];
        }
        taskDates = temp1;
        taskDescriptions = temp2;
    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.menu_main, menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         // Handle action bar item clicks here. The action bar will
         // automatically handle clicks on the Home/Up button, so long
         // as you specify a parent activity in AndroidManifest.xml.
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




}
