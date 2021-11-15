package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);


        // create intent to get passing extra from main activity
        Intent detailIntent=getIntent();
        String taskName  = detailIntent.getStringExtra("taskName");


// set the name of the task in the  TextView
        TextView taskNameView = findViewById(R.id.task_details_title_taskName_view);
        taskNameView.setText(taskName);
    }
}