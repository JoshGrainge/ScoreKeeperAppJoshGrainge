package com.example.scorekeeperappjoshgrainge;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Boolean saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);

        // Assign switch listener to toggle data persistence
        Switch saveDataSwitch = (Switch) findViewById(R.id.save_switch);
        saveDataSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TogglePersistentData(b);
            }
        });

        // Assign check value of switch if data is being saved
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF, MODE_PRIVATE);
        if(sharedPreferences.getBoolean(MainActivity.SAVE_SWITCH, false)){
            saveDataSwitch.setChecked(true);
        }

        // Assign back button functionality to return to main activity
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Toggle data persistence based on switch value
    private void TogglePersistentData(boolean shouldSaveData){

        this.saveData = shouldSaveData;
    }

    // Save values to shared preferences when app is paused
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(MainActivity.SAVE_SWITCH, saveData);
        editor.apply();
    }
}
