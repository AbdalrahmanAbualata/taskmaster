package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amplifyframework.core.Amplify;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button loginBut = findViewById(R.id.loginBut);// to Login
        Button saveNameOfUser = findViewById(R.id.button_save_name);// to save the name of user in the input Field
        EditText userNameField  = findViewById(R.id.input_user_name);// enter the name of user .

        // save the user name in the SharedPreferences (like local storage in js).
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
//                sharedPreferences.edit().putString("userName",userName).apply();
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();

      // create spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Team 1", "Team 2", "Team 3"});
       Spinner teamSpinner = findViewById(R.id.chooseTeamSpinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(dataAdapter);




//        String userNameTitle =Amplify.Auth.getCurrentUser().getUsername();
//
//        Amplify.Auth.fetchAuthSession(
//                result ->{
//                    if(result.isSignedIn()){
//                        loginBut.setText("signOut");
//                    }
//                },
//                error -> Log.e("AmplifyQuickstart", error.toString())
//        );


//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Amplify.Auth.signInWithWebUI(
//                        SettingsActivity.this,
//                        result -> Log.i("AuthQuickStart", result.toString()),
//                        error -> Log.e("AuthQuickStart", error.toString())
//                );
//            }
//        });

        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.fetchAuthSession(
                        result ->{
                            if(result.isSignedIn()){
                                Amplify.Auth.signOut(
                                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                                        error -> Log.e("AuthQuickstart", error.toString())
                                );
                                loginBut.setText("sign in");
                            }
                            else{

                                Amplify.Auth.signInWithWebUI(
                                        SettingsActivity.this,
                                        result1 -> Log.i("AuthQuickStart", result1.toString()),
                                        error -> Log.e("AuthQuickStart", error.toString())
                                );
                                loginBut.setText("sign out");
                            }
                        },
                        error -> Log.e("AmplifyQuickstart", error.toString())
                );
            }
        });

        saveNameOfUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameField.getText().toString();// target the name of user from the input after clicking the button
                sharedPrefEditor.putString("userName",userName);
                sharedPrefEditor.apply();

                // to select the team
                switch(teamSpinner.getSelectedItem().toString()){
                    case "Team 1":
                        sharedPrefEditor.putString("teamName","59523fe4-b088-4967-a9d3-af07c040cf42");
                        sharedPrefEditor.apply();
                        break;
                    case "Team 2":
                        sharedPrefEditor.putString("teamName","2a490f92-db90-463c-9269-ebd6d5e31233");
                        sharedPrefEditor.apply();
                        break;
                    case "Team 3":
                        sharedPrefEditor.putString("teamName","2a38e3e0-7023-4cb5-9a31-a3979698cf13");
                        sharedPrefEditor.apply();
                        break;
                }

                // return to the main page after save the name of user
                Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(mainIntent);
//                finish();  //we can use that instead of the intent
            }
        });

    }

//    @SuppressLint("SetTextI18n")
//    @Override
//    protected void onResume(){
//        super.onResume();
//        Button loginBut = findViewById(R.id.loginBut);// to Login
//
//        Amplify.Auth.fetchAuthSession(
//                result ->{
//                    if(result.isSignedIn()){
//                        loginBut.setText("signOut");
//                    }
//                },
//                error -> Log.e("AmplifyQuickstart", error.toString())
//        );
//
//    }

}