package com.example.lab03_04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class RegisterActivity extends ComponentActivity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        setupButtons();
    }

    private void setupButtons(){
        Button submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = -1;

                EditText unameInput = (EditText) findViewById(R.id.register_unameinput);
                EditText passInput = (EditText) findViewById(R.id.register_passinput);
                EditText nameInput = (EditText) findViewById(R.id.register_nameinput);
                EditText weightInput = (EditText) findViewById(R.id.register_weightinput);
                EditText goalInput = (EditText) findViewById(R.id.register_goalinput);
                EditText birthdayInput = (EditText) findViewById(R.id.register_birthdayinput);
                EditText heightfeetInput = (EditText) findViewById(R.id.register_heightfeetinput);
                EditText heightinInput = (EditText) findViewById(R.id.register_heightininput);


                if(validateAccountInfo()){
                    id = createLogin();
                    if(id>0){
                        createAccount(id);
                    }
                    finish();
                }
                else{
                    unameInput.setText("");
                    passInput.setText("");
                    nameInput.setText("");
                    weightInput.setText("");
                    goalInput.setText("");
                    birthdayInput.setText("");
                    heightfeetInput.setText("");
                    heightinInput.setText("");
                    unameInput.setError("All fields must be filled out.");
                    passInput.setError("All fields must be filled out.");
                    nameInput.setError("All fields must be filled out.");
                    weightInput.setError("All fields must be filled out.");
                    goalInput.setError("All fields must be filled out.");
                    birthdayInput.setError("All fields must be filled out.");
                    heightfeetInput.setError("All fields must be filled out.");
                    heightinInput.setError("All fields must be filled out.");


                }
            }
        });
    }

    private boolean validateAccountInfo(){
        EditText unameInput = (EditText) findViewById(R.id.register_unameinput);
        EditText passInput = (EditText) findViewById(R.id.register_passinput);
        EditText nameInput = (EditText) findViewById(R.id.register_nameinput);
        EditText weightInput = (EditText) findViewById(R.id.register_weightinput);
        EditText goalInput = (EditText) findViewById(R.id.register_goalinput);
        EditText birthdayInput = (EditText) findViewById(R.id.register_birthdayinput);
        EditText heightfeetInput = (EditText) findViewById(R.id.register_heightfeetinput);
        EditText heightinInput = (EditText) findViewById(R.id.register_heightininput);


        return(!nameInput.getText().toString().equals("") && !weightInput.getText().toString().equals("") &&
                !goalInput.getText().toString().equals("") && !birthdayInput.getText().toString().equals("") && !heightfeetInput.getText().toString().equals("") &&
                !heightinInput.getText().toString().equals(""));

    }

    private int createLogin(){
        EditText unameInput = (EditText) findViewById(R.id.register_unameinput);
        EditText passInput = (EditText) findViewById(R.id.register_passinput);
        String username = unameInput.getText().toString();
        String password = passInput.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "/login.txt");
        OutputStreamWriter w = null;
        Scanner scan;
        int id= -1;
        String str = null;
        String [] Arr;

        if(!f.exists()){
            id = 1;
            try {
                w = new OutputStreamWriter(openFileOutput("login.txt",MODE_PRIVATE));
                w.write(id + "," + username + "," + password);
                w.close();
            }
            catch(IOException e){
                Toast.makeText(getBaseContext(), "IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }
        else{
            try {
                scan = new Scanner(openFileInput("login.txt"));
                while (scan.hasNextLine()) {
                    str = scan.nextLine();
                }
                if (str != null) {
                    Arr = str.split(",");
                    if (Arr.length == 3) {
                        id = Integer.parseInt(Arr[0]) + 1;
                    }
                }
                scan.close();

                w = new OutputStreamWriter(openFileOutput("login.txt",MODE_APPEND));
                w.append("\n" + id + "," + username + "," + password);
                w.close();

            }catch(IOException e){
                Toast.makeText(getBaseContext(), "IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        return id;
    }

    private void createAccount(int id){
        EditText nameInput = (EditText) findViewById(R.id.register_nameinput);
        EditText weightInput = (EditText) findViewById(R.id.register_weightinput);
        EditText goalInput = (EditText) findViewById(R.id.register_goalinput);
        EditText birthdayInput = (EditText) findViewById(R.id.register_birthdayinput);
        EditText heightfeetInput = (EditText) findViewById(R.id.register_heightfeetinput);
        EditText heightinInput = (EditText) findViewById(R.id.register_heightininput);

        String Name = nameInput.getText().toString();
        String weight = weightInput.getText().toString();
        String goal = goalInput.getText().toString();
        String birthday = birthdayInput.getText().toString();
        String heightfeet = heightfeetInput.getText().toString();
        String heightin = heightinInput.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath() + "/accounts.txt");
        OutputStreamWriter w =null;

        if(!f.exists()){
            try {
                w = new OutputStreamWriter(openFileOutput("accounts.txt",MODE_PRIVATE));
                w.write(id + "," + Name + "," + weight + "," + goal + "," + birthday + "," + heightfeet + "," + heightin + "," + 0);
                w.close();
            }
            catch(IOException e){
                Toast.makeText(getBaseContext(), "IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            try {
                w = new OutputStreamWriter(openFileOutput("accounts.txt", MODE_APPEND));
                w.append(id + "," + Name + "," + weight + "," + goal + "," + birthday + "," + heightfeet + "," + heightin + "," + 0);
                w.close();

            }catch(IOException e){
                Toast.makeText(getBaseContext(), "IOException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

