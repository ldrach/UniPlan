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
 import android.widget.ListView;
 import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {

     public Database db;
     public TaskDao taskDao;

     public ListView taskListView;
     public List<Task> taskLis
    // Commented out do to conflicts!
    // ListView taskListView;
    // private tasksViewModel mViewModel;
    // private Toolbar mTopToolbar;

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

         mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
         setSupportActionBar(mTopToolbar);

        taskListView = findViewById(R.id.taskListView);

         taskDates = new String[10];
         taskDescriptions = new String[10];
         for(int a=0;a<taskDates.length;a++){
             taskDates[a] = "April 1";
             taskDescriptions[a] = "Description";
         }

         //After determining whether a new task is to be added, the adapter is set to display the tasks
         tasksAdapter = new ArrayAdapter<>(this,
                 android.R.layout.simple_list_item_1, taskDates);
         taskListView.setAdapter(tasksAdapter);
          /*
        !!!!!!!!!!!!!!!! NEED TO RESTORE PREVIOUS STATE OF taskList, taskCount,
        and instance of database here
         */

         //Instance of room database implemented here~~~~~~~~~~~~~~~~~~~
         db = Database.getFileDatabase(getApplicationContext());
         taskList = db.taskDao().getAll();
/*
         //Grasping at straws for a fix, this might have been it. Will come up with a better solution later
         db.taskDao().deleteAll();
         int current=0;
         while(current<taskList.size()){
             db.taskDao().insert(taskList.get(current));
             current++;
         }
*/
         //If there are any tasks, updates task arrays with data
         if(taskList.size()>0) {
             updateTaskArrays();
             taskCount = taskList.size();
         }

         //If this intent is coming from the add task activity, the taskAdded string will be "t",
         //indicating that a new task is to be added to the database
         Intent intent = getIntent();
         taskAdded = intent.getStringExtra("taskAdded");
         if((taskAdded != null) && taskAdded.equalsIgnoreCase("t")) {
             taskAdded = "false";
             //Adds task to database from the given intent
             Task task = new Task();
             task.name = intent.getStringExtra("taskName");
             task.id = taskList.size();
             task.notes = intent.getStringExtra("notes");
             year = intent.getIntExtra("year", 0);
             month = intent.getIntExtra("month", 0);
             day = intent.getIntExtra("day", 0);
             task.date = "" + day + "/" + month + "/" + year;

             db.taskDao().insert(task);
             taskList = db.taskDao().getAll();
             taskCount = taskList.size();

             //populates arrays after new task is added
             updateTaskArrays();
         }

         //populates arrays after new task is added
         updateTaskArrays();





             //Add class button created here
        FloatingActionButton fab = findViewById(R.id.addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates a new array with 5 more spots and assigns it back to taskDates
                if(taskCount>(taskDates.length-1)){
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
    } //End of onCreate method


    //Populates taskDates with dates of all tasks, and taskDescriptions with the corresponding task
    //names and descriptions
    private void updateTaskArrays(){
        String str = " ";

        taskList = db.taskDao().getAll();
        for (int i = 0; i<taskCount; i++) {
            Task currentTask = taskList.get(i);

            taskDates[i] = currentTask.getTaskDate();

            str += currentTask.getTaskName() + "\nDescription: " + currentTask.getTaskNotes();
            taskDescriptions[i] = str;
        }
    }

    private Task generateTestTask(){
        Task task1 = new Task();
        task1.name = "testName";
        task1.id = taskCount;
        task1.notes = "testNotes";
        year = 2019;
        month = 1;
        day = 1;
        task1.date = "" + day + "/" + month + "/" + year;
         return task1;
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

         if (id == R.id.action_add) {
             Intent i = new Intent(getBaseContext(), AddClass.class);
             startActivity(i);
             return true;
         }

         return super.onOptionsItemSelected(item);
     }




}
