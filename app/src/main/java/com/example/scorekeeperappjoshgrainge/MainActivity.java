package com.example.scorekeeperappjoshgrainge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int redScore;
    int blueScore;

    TextView redScoreText;
    TextView blueScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variable values
        redScore = 0;
        blueScore = 0;
        redScoreText = (TextView) findViewById(R.id.red_team_score_text);
        blueScoreText = (TextView) findViewById(R.id.blue_team_score_text);

        // Set spinner values to be score increment values in strings
        Spinner spinner = findViewById(R.id.spinner_score_interval);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,
                R.array.score_increment, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Assign on click listener to red and blue buttons
        Button rButton = findViewById(R.id.add_red_score_button);
        Button bButton = findViewById(R.id.add_blue_score_button);
        rButton.setOnClickListener(this);
        bButton.setOnClickListener(this);
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
}