package com.example.lab03_04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HistoryActivity extends ComponentActivity {

    private int id;
    Scanner s;
    String str = "";
    String name = "";
    String fileName = "";
    String[] arr = null;
    ArrayList<String[]> arr1 = new ArrayList<String[]>();
    String f = "";
    public static int position = -1;
    public static int totalWorkouts = 0;


    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);

        try{
            name = getUserName(id);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        fileName = "/" + name + "-workouts.txt";
        f = getFilesDir().getAbsolutePath() + fileName;
        int numWorkouts = 0;
        getHistory(f);
        setupButtons();
    }

    private void setupButtons() {
        ImageButton profileButton;
        ImageButton workoutsButton;
        Button prev;
        Button next;
        TextView Name = (TextView) findViewById(R.id.histNameDisplay);
        TextView Type = (TextView) findViewById(R.id.histTypeDisplay);
        TextView Weight = (TextView) findViewById(R.id.histWeightDisplay);
        TextView Sets = (TextView) findViewById(R.id.histSetsDisplay);
        TextView Reps = (TextView) findViewById(R.id.histRepsDisplay);

        profileButton = (ImageButton) findViewById(R.id.navProfileHistory);
        workoutsButton = (ImageButton) findViewById(R.id.navWorkoutHistory);
        prev = (Button) findViewById(R.id.prevEntry);
        next = (Button) findViewById(R.id.nextEntry);

        profileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, ProfileActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (position > 0) {//if previous array index contains values, set hist textViews to those values
                    Name.setText(arr1.get(position -1)[1]);
                    Type.setText(arr1.get(position -1)[2]);
                    Weight.setText(arr1.get(position -1)[3]);
                    Sets.setText(arr1.get(position -1)[4]);
                    Reps.setText(arr1.get(position -1)[5]);
                    position--;
                }
                else{
                    Toast.makeText(getBaseContext(), "Oldest workout already reached", Toast.LENGTH_SHORT).show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (position < totalWorkouts-1) {//if previous array index contains values, set hist textViews to those values
                    Name.setText(arr1.get(position +1)[1]);
                    Type.setText(arr1.get(position +1)[2]);
                    Weight.setText(arr1.get(position +1)[3]);
                    Sets.setText(arr1.get(position +1)[4]);
                    Reps.setText(arr1.get(position +1)[5]);
                    position++;
                }
                else{
                    Toast.makeText(getBaseContext(), "Newest workout already reached", Toast.LENGTH_SHORT).show();
                }
            }
        });

        workoutsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, CreateWorkoutActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

    private String getUserName(int id) throws FileNotFoundException {
        File f = new File(getFilesDir().getAbsolutePath() + "/accounts.txt");
        Scanner s;
        String name = "";
        String str = "";
        String[] arr = null;

        try {

            if(f.exists()) {
                s = new Scanner(f);

                while (s.hasNext()) {
                    str = s.nextLine();
                    arr = str.split(",");
                    if (Integer.parseInt(arr[0]) == id) {
                        return arr[1];
                    }
                }

                s.close();
            }
        }

        catch (Exception e) {
            Toast.makeText(getBaseContext(), "Exception while getting name", Toast.LENGTH_SHORT).show();
        }

        return "noName";
    }

    private void getHistory(String f){
        TextView Name = (TextView) findViewById(R.id.histNameDisplay);
        TextView Type = (TextView) findViewById(R.id.histTypeDisplay);
        TextView Weight = (TextView) findViewById(R.id.histWeightDisplay);
        TextView Sets = (TextView) findViewById(R.id.histSetsDisplay);
        TextView Reps = (TextView) findViewById(R.id.histRepsDisplay);

        File file = new File(f);
        Scanner s;
        String name = "";
        String str = "";
        String[] arr = new String[6];

        try {
            if(file.exists()) {
                s = new Scanner(file);

                while (s.hasNext()) {
                    position +=1;
                    str = s.nextLine();
                    arr = str.split(",");
                    arr1.add(arr);
                }

                //Toast.makeText(getBaseContext(), "all file contents copied", Toast.LENGTH_SHORT).show();
                Name.setText(arr1.get(position)[1]);
                Type.setText(arr1.get(position)[2]);
                Weight.setText(arr1.get(position)[3]);
                Sets.setText(arr1.get(position)[4]);
                Reps.setText(arr1.get(position)[5]);
                Toast.makeText(getBaseContext(),"index: " + position, Toast.LENGTH_SHORT).show();
                totalWorkouts = position + 1;
                s.close();
            }
        }

        catch (Exception e) {
            Toast.makeText(getBaseContext(), "Exception while getting history", Toast.LENGTH_SHORT).show();
        }
    }
}
