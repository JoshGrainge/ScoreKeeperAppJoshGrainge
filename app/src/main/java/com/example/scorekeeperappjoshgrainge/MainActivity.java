package com.example.scorekeeperappjoshgrainge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Data persistence variables
    public static final String SHARED_PREF = "sharedPreferences";
    public static final String RED_TEXT = "red_team_score_text";
    public static final String BLUE_TEXT = "blue_team_score_text";
    public static final String INCREMENT_VALUE = "spinner_score_interval";
    public static final String SAVE_SWITCH = "save_switch";


    int redScore;
    int blueScore;

    TextView redScoreText;
    TextView blueScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variable values
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        Boolean hasSavedData = sharedPreferences.getBoolean(SAVE_SWITCH, false);

        redScore = hasSavedData ? sharedPreferences.getInt(RED_TEXT, 0) : 0;
        blueScore = hasSavedData ? sharedPreferences.getInt(BLUE_TEXT, 0) : 0;
        redScoreText = (TextView) findViewById(R.id.red_team_score_text);
        blueScoreText = (TextView) findViewById(R.id.blue_team_score_text);

        // Update score text fields for when data is persistent
        redScoreText.setText(String.valueOf(redScore));
        blueScoreText.setText(String.valueOf(blueScore));

        // Set spinner values to be score increment values in strings
        Spinner spinner = findViewById(R.id.spinner_score_interval);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,
                R.array.score_increment, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set spinner value to saved value
        if(hasSavedData){
            spinner.setSelection(sharedPreferences.getInt(INCREMENT_VALUE, 1) - 1);
        }

        // Assign on click listener to red and blue buttons
        Button rButton = findViewById(R.id.add_red_score_button);
        Button bButton = findViewById(R.id.add_blue_score_button);
        rButton.setOnClickListener(this);
        bButton.setOnClickListener(this);
    }

    // Create options menu in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Drop down menu functionality to display about or to navigate to settings activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case  R.id.settings:
                // goto new activity to allow user to toggle persistent data
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;

            case R.id.about:
                Toast toast = Toast.makeText(getApplicationContext(),
                         "Name: Josh Grainge, Course Code: CPIN, Student Number: A00129117",
                              Toast.LENGTH_LONG);
                toast.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Add score on click
    @Override
    public void onClick(View view) {

        int redButtonId = R.id.add_red_score_button;

        // Add to team that is associated with button
        if(view.getId() == redButtonId)
            AddRedScore();
        else
            AddBlueScore();
    }

    // Add point value to red team score
    void AddRedScore() {
        redScore += GetIncrementValue();
        redScoreText.setText(String.valueOf(redScore));
    }

    // Add point value to blue team score
    void AddBlueScore() {
        blueScore += GetIncrementValue();
        blueScoreText.setText(String.valueOf(blueScore));
    }

    // Get the currently selected spinner value as an integer
    int GetIncrementValue() {
        Spinner spinner = findViewById(R.id.spinner_score_interval);
        return Integer.parseInt(spinner.getSelectedItem().toString());
    }

    // Save values to shared preferences when app is paused
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(RED_TEXT, redScore);
        editor.putInt(BLUE_TEXT, blueScore);
        editor.putInt(INCREMENT_VALUE, GetIncrementValue());
        editor.apply();

    }
}