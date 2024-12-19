package com.example.lab03_04;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.ComponentActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateWorkoutActivity extends ComponentActivity{
    private Account profileInfo;
    private AssetManager assets;
    public int id;
        public void onCreate(Bundle savedInstanceState){

            super.onCreate(savedInstanceState);
            setContentView(R.layout.createworkout);
            setupButtons();

            Intent intent = getIntent();
            id = intent.getIntExtra("id",-1);
        }

        private void setupButtons(){

            Button buttonCreate = (Button) findViewById(R.id.workoutCreate);

            //ImageButton workoutsButton;
            ImageButton profileButton;
            ImageButton historyButton;

            // Nav Bar Buttons
            profileButton = (ImageButton) findViewById(R.id.navProfileWorkout);
            historyButton = (ImageButton) findViewById(R.id.navHistoryWorkout);
            //workoutsButton = (ImageButton) findViewById(R.id.navWorkoutProfile);

            buttonCreate.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //int id = -1;

                    EditText wNInput = (EditText) findViewById(R.id.workoutName);
                    EditText wTInput = (EditText) findViewById(R.id.workoutType);
                    EditText wWInput = (EditText) findViewById(R.id.workoutWeight);
                    EditText wSInput = (EditText) findViewById(R.id.editTextText2);
                    EditText wRInput = (EditText) findViewById(R.id.workoutReps);


                    if(validateAccountInfo()){
                        //profile info to retrieve name for newfile
                        //String name = profileInfo.getName();
                        createWorkout(Integer.toString(id));
                        Toast.makeText(getBaseContext(), "WORKOUT CREATED", Toast.LENGTH_LONG).show();
                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                        //finish();
                    }
                    else{
                        wNInput.setText("");
                        wTInput.setText("");
                        wWInput.setText("");
                        wSInput.setText("");
                        wRInput.setText("");
                        wNInput.setError("All fields must be filled out");
                        wTInput.setError("All fields must be filled out");
                        wWInput.setError("All fields must be filled out");
                        wSInput.setError("All fields must be filled out");
                        wRInput.setError("All fields must be filled out");
                    }
                }
            });

            // Moves to history page - TODO set intent to History class once implemented
            historyButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(CreateWorkoutActivity.this, HistoryActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });

            // Move to profile activity
            profileButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(CreateWorkoutActivity.this, ProfileActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
        }

    private boolean validateAccountInfo(){

        EditText wNInput = (EditText) findViewById(R.id.workoutName);
        EditText wTInput = (EditText) findViewById(R.id.workoutType);
        EditText wWInput = (EditText) findViewById(R.id.workoutWeight);
        EditText wSInput = (EditText) findViewById(R.id.editTextText2);
        EditText wRInput = (EditText) findViewById(R.id.workoutReps);
            if(!wNInput.getText().toString().equals("") && !wTInput.getText().toString().equals("") &&
                    !wWInput.getText().toString().equals("") && !wSInput.getText().toString().equals("")
            && !wRInput.getText().toString().equals("")){
                return true;
            }
            return false;
        }
        private void createWorkout(String name){

            EditText wNInput = (EditText) findViewById(R.id.workoutName);
            EditText wTInput = (EditText) findViewById(R.id.workoutType);
            EditText wWInput = (EditText) findViewById(R.id.workoutWeight);
            EditText wSInput = (EditText) findViewById(R.id.editTextText2);
            EditText wRInput = (EditText) findViewById(R.id.workoutReps);
            String workoutName = wNInput.getText().toString();
            String workoutType = wTInput.getText().toString();
            String workoutWeight = wWInput.getText().toString();
            String workoutSets = wSInput.getText().toString();
            String workoutReps = wRInput.getText().toString();

            String tName = getName(id) + "-workouts.txt";

            File f = new File(getFilesDir().getAbsolutePath() + "/" + tName);
            OutputStreamWriter w = null;
            if(!f.exists()){
                try {
                    // Writes to new workouts file
                    w = new OutputStreamWriter(openFileOutput(tName, MODE_PRIVATE));
                    w.write(getName(id) + "," + workoutName + "," + workoutType + "," + workoutWeight + "," + workoutSets + "," + workoutReps);
                    incrementWorkoutsCompleted();
                    w.close();
                }
                catch(IOException e){
                    Toast.makeText(getBaseContext(),"IOException" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else{
                try {
                    w = new OutputStreamWriter(openFileOutput(tName, MODE_APPEND));
                    w.append("\n" + getName(id) +","+workoutName+","+workoutType+","+workoutWeight+","+workoutSets+","+workoutReps);
                    incrementWorkoutsCompleted();
                    w.close();
                }
                catch(IOException e){
                    Toast.makeText(getBaseContext(),"IOException" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
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

        public void incrementWorkoutsCompleted() {

            List<String> lines = new ArrayList<>();

            try {
                File file = new File(getFilesDir().getAbsolutePath() + "/accounts.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int tempId = Integer.parseInt(parts[0]);

                    if (tempId == id) {
                        int currentWorkouts = Integer.parseInt(parts[7]);
                        int updatedWorkouts = currentWorkouts + 1;
                        parts[7] = String.valueOf(updatedWorkouts);
                        line = String.join(",", parts);
                    }

                    lines.add(line);
                }

                reader.close();

                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
                writer.close();

                System.out.println("Workouts updated and line replaced successfully.");
            } catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
        }
