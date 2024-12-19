package com.example.lab03_04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class WorkoutListActivity extends ComponentActivity {
    private int id;
    private ArrayList<String> workoutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workoutlistnew);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);

        loadWorkouts();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, workoutList);
        ListView listView = findViewById(R.id.workoutListViewNew);

        listView.setAdapter(adapter);

        setupButtons();
    }

    private void loadWorkouts() {
        String name = getName(id); // Fetch the user's name or ID here
        File file = new File(getFilesDir().getAbsolutePath() + name + "workouts.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                workoutList.add(line.split(",")[1]); // Assuming 2nd element is the workout name
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Error loading workouts", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupButtons() {
        ImageButton profileButton = findViewById(R.id.navProfileProfile);
        ImageButton createWorkoutButton = findViewById(R.id.navCreateWorkoutList);
        ImageButton historyButton = findViewById(R.id.navHistoryProfile);
        ImageButton workoutButton = findViewById(R.id.navWorkoutProfile);
        Button createNewWorkout = (Button) findViewById(R.id.createNewWorkout);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutListActivity.this, ProfileActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutListActivity.this, CreateWorkoutActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutListActivity.this, HistoryActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        workoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutListActivity.this, WorkoutListActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        createNewWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutListActivity.this, CreateWorkoutActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
    public String getName(int id) {

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
                        return arr[1];
                    }
                }
                s.close();
            }
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "None";
    }
}
