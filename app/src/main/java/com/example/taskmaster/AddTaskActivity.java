package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button buttonthree = (Button) findViewById(R.id.button_addTask_activity_addTask);

        buttonthree.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View v){

                Toast.makeText(getApplicationContext(), "Task Added",Toast.LENGTH_LONG).show();
            }
        });
    }
}
