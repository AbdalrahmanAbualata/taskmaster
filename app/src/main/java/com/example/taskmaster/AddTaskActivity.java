package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {
    private Spinner teamSpinner;
    private List<Team> teams =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText title,body,state;
        title = findViewById(R.id.taskTitle);
        body = findViewById(R.id.taskDescription);
        state = findViewById(R.id.taskState);
        Button addTask = findViewById(R.id.button_addTask_activity_addTask);




// for create spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Team 1", "Team 2", "Team 3"});
        teamSpinner = findViewById(R.id.teamSpinner);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(dataAdapter2);


//        TaskDataBase db = TaskDataBase.getInstance(this);
//        TaskDao taskDao = db.taskDao();


        getTeams();

        addTask.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Task task = new Task(title.getText().toString(),body.getText().toString(),state.getText().toString());
//                taskDao.insert(task);


                switch(teamSpinner.getSelectedItem().toString()){
                    case "Team 1":
                        Task task = Task.builder().title(title.getText().toString()).body(body.getText().toString()).state(state.getText().toString()).teamId(teams.get(2).getId()).build();
                        saveToAPI(task);
                        saveToDatastore(task);
                        break;
                    case "Team 2":
                        Task task2 = Task.builder().title(title.getText().toString()).body(body.getText().toString()).state(state.getText().toString()).teamId(teams.get(1).getId()).build();
                        saveToAPI(task2);
                        saveToDatastore(task2);
                        break;
                    case "Team 3":
                        Task task3 = Task.builder().title(title.getText().toString()).body(body.getText().toString()).state(state.getText().toString()).teamId(teams.get(0).getId()).build();
                        saveToAPI(task3);
                        saveToDatastore(task3);
                        break;
                }

                // add Task to data base
//                Task item = Task.builder()
//                        .title(title.getText().toString())
//                        .body(body.getText().toString())
//                        .state(state.getText().toString())
//                        .build();
//                Amplify.DataStore.save(
//                        item,
//                        success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
//                        error -> Log.e("Amplify", "Could not save item to DataStore", error)
//                );



                Toast.makeText(getApplicationContext(), "Task Added",Toast.LENGTH_LONG).show();
//                finish();
                Intent mainIntent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
    private void getTeams() {
        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team team : response.getData()) {
                        this.teams.add(team);
                        Log.i("MyAmplifyApp", team.getName());
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
    }

    public void saveToAPI(Task task){
        Amplify.API.mutate(ModelMutation.create(task),
                success -> Log.i("Tutorial", "Saved item: " + success.getData()),
                error -> Log.e("Tutorial", "Could not save item to API", error)
        );
    }

    public void saveToDatastore(Task task){
        Amplify.DataStore.save(task,
                success -> Log.i("Tutorial", "Saved item: " + success.item()),
                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
        );
    }
}
