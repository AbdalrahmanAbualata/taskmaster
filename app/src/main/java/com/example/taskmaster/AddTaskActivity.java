package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText title,body,state;
        title = findViewById(R.id.taskTitle);
        body = findViewById(R.id.taskDescription);
        state = findViewById(R.id.taskState);
        Button addTask = findViewById(R.id.button_addTask_activity_addTask);

//        TaskDataBase db = TaskDataBase.getInstance(this);
//        TaskDao taskDao = db.taskDao();
        

        addTask.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Task task = new Task(title.getText().toString(),body.getText().toString(),state.getText().toString());
//                taskDao.insert(task);

                // add Task to data base
                Task item = Task.builder()
                        .title(title.getText().toString())
                        .body(body.getText().toString())
                        .state(state.getText().toString())
                        .build();
                Amplify.DataStore.save(
                        item,
                        success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
                        error -> Log.e("Amplify", "Could not save item to DataStore", error)
                );


                Toast.makeText(getApplicationContext(), "Task Added",Toast.LENGTH_LONG).show();
//                finish();
                Intent mainIntent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
