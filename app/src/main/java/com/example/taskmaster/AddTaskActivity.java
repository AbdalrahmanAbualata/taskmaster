package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    private Spinner teamSpinner;
    private List<Team> teams = new ArrayList<>();
    Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
    Uri uri;
    String mylocation;

    private FusedLocationProviderClient fusedLocationClient;
//    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText title, body, state;
        title = findViewById(R.id.taskTitle);
        body = findViewById(R.id.taskDescription);
        state = findViewById(R.id.taskState);
        Button addTask = findViewById(R.id.button_addTask_activity_addTask);

        Button upload = (Button) findViewById(R.id.upload);
        TextView filename = (TextView) findViewById(R.id.filename);

        filename.setText("Choose a file");


        // location   ************************************************


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // this is for taking the permissions to access the location service .

        if (!hasLocationAccess()) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1212);
            if (hasLocationAccess()) {
                getLocation();
            }
        } else {
            getLocation();
        }

        // location *******************************************************


        // intent Filter  ************************************************

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        }

        // intent Filter ***********************************************


///************************************************************************

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a File");
                startActivityForResult(chooseFile, 12);

            }
        });

// for create spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"team1", "team2", "team3"});
        teamSpinner = findViewById(R.id.teamSpinner);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(dataAdapter2);


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titletaken = title.getText().toString();
                String desctaken = body.getText().toString();
                String stateTaken = state.getText().toString();
                String team = teamSpinner.getSelectedItem().toString();
                Amplify.DataStore.query(
                        Team.class, Team.NAME.contains(team),
                        items -> {
                            while (items.hasNext()) {
                                Team item = items.next();
                                Task item1 = Task.builder().title(titletaken).body(desctaken).state(state.getText().toString()).teamId(item.getId()).build();
                                Amplify.DataStore.save(
                                        item1,
                                        success -> Log.i("COMO", "Saved item: "),
                                        error -> Log.e("Amplify", "Could not save item to DataStore", error)
                                );
                                String key = item1.getId();
                                // location
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddTaskActivity.this);
                                sharedPreferences.edit().putString(key, mylocation).apply();

                                try {
                                    InputStream file = getContentResolver().openInputStream(uri);
                                    Amplify.Storage.uploadInputStream(
                                            key,
                                            file,
                                            result -> Log.i("UPLOAD", "Successfully uploaded: " + result.getKey()),
                                            storageFailure -> Log.e("UPLOAD", "Upload failed", storageFailure)
                                    );
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                Log.i("abd", "Id was stored ");
                                Log.i("Amplify", "Id " + item.getId());
                            }
                        },
                        failure -> Log.e("Amplify", "Could not query DataStore", failure)
                );

                Toast.makeText(getApplicationContext(), "Task Added", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {

                            Geocoder geocoder;
                            List<Address> address = new ArrayList<>();
                            geocoder = new Geocoder(AddTaskActivity.this, Locale.getDefault());

                            try {
                                address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            String addressss = address.get(0).getAddressLine(0);
                            String city = address.get(0).getLocality();
                            String state = address.get(0).getAdminArea();
                            String country = address.get(0).getCountryName();
                            String postalCode = address.get(0).getPostalCode();
                            String knownName = address.get(0).getFeatureName();

                            mylocation = country + "- " + city;
                            System.out.println("Abd" + mylocation);

                        }
                    }
                });
    }

    private boolean hasLocationAccess() {
        boolean haveFineLocationAccess = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean haveCoarseLocationAccess = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        return haveFineLocationAccess || haveCoarseLocationAccess;
    }


    // intent Filter
    void handleSendImage(Intent intent) {
        uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        TextView filename = (TextView) findViewById(R.id.filename);
        if (uri != null) {
            filename.setText(uri.getPath());
        } else {
            filename.setText("Choose a file");
        }
        // Update UI to reflect image being shared
    }

    // ***************************************************

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 12 && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uri = resultData.getData();
                TextView filename = (TextView) findViewById(R.id.filename);

                if (uri != null) {
                    filename.setText(uri.getPath());
                } else {
                    filename.setText("Choose a file");

                }
                Toast.makeText(getApplicationContext(), uri.getPath(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void back(View view) {
        this.finish();

    }
}
