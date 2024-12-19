package com.example.lab03_04;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class ProfileActivity extends ComponentActivity {

    public Account profileInfo;
    private AssetManager assets;
    Button logOutButton;
    ImageButton workoutsButton;
    ImageButton historyButton;

    public int id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        profileInfo = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setUpProfile();
        setUpButtons();

    }

    private void setUpButtons() {
        logOutButton = (Button) findViewById(R.id.buttonLogout);
        workoutsButton = (ImageButton) findViewById(R.id.navWorkoutProfile);
        historyButton = (ImageButton) findViewById(R.id.navHistoryProfile);

        // Logout Button - Moves to login activity
        logOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Move to Create Workout activity
        workoutsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CreateWorkoutActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Intent intent = new Intent(ProfileActivity.this, HistoryActivity.class);
               intent.putExtra("id",id);
               startActivity(intent);
            }
        });
    }

    public void setUpProfile() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);

        File f = new File(getFilesDir().getAbsolutePath() + "/accounts.txt");
        Scanner s;
        String str = "";
        String[] arr = null;

        try {
            if(f.exists()) {
                s = new Scanner(openFileInput("accounts.txt"));
                while (s.hasNext()) {
                    str = s.nextLine();
                    arr = str.split(",");
                    if (Integer.parseInt(arr[0]) == id) {
                        /* Profile format id, name, weight, goal, bday, heightFt, heightIn, */
                        profileInfo = new Account(id, arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), arr[4], Integer.parseInt(arr[5]), Integer.parseInt(arr[6]));
                        profileInfo.setWorkoutsCompleted(Integer.parseInt(arr[7]));
                        break;
                    }
                }
                s.close();
            }
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (profileInfo != null) {
            TextView name = (TextView) findViewById(R.id.nameString);
            TextView age = (TextView) findViewById(R.id.ageString);
            TextView weight = (TextView) findViewById(R.id.weightString);
            TextView goal = (TextView) findViewById(R.id.goalString);
            TextView heightFt = (TextView) findViewById(R.id.heightFtString);
            TextView heightIn = (TextView) findViewById(R.id.heightInString);
            TextView bday = (TextView) findViewById(R.id.bdayString);
            TextView workouts = (TextView) findViewById(R.id.workoutsString);

            name.setText(profileInfo.getName());        // TODO - Proper data / need profile creation
            age.setText(String.valueOf(profileInfo.getAge()));
            weight.setText(String.valueOf(profileInfo.getWeight()));
            goal.setText(String.valueOf(profileInfo.getGoal()));
            heightFt.setText(String.valueOf(profileInfo.getHeightFt()));
            heightIn.setText(String.valueOf(profileInfo.getHeightIn()));
            bday.setText(String.valueOf(profileInfo.getBday()));
            workouts.setText(String.valueOf(profileInfo.getWorkoutsCompleted()));
        }
    }
}
