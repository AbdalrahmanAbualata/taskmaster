package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Button saveNameOfUser = findViewById(R.id.button_save_name);// to save the name of user in the input Field
        EditText userNameField  = findViewById(R.id.input_user_name);// enter the name of user .

        saveNameOfUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameField.getText().toString();// target the name of user from the input after clicking the button
                // save the user name in the SharedPreferences (like local storage in js).
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
//                sharedPreferences.edit().putString("userName",userName).apply();
                SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
                sharedPrefEditor.putString("userName",userName);
                sharedPrefEditor.apply();
                // return to the main page after save the name of user
                Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(mainIntent);
//                finish();  we can use that instead of the intent
            }
        });

    }
}