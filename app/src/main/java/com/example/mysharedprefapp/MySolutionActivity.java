package com.example.mysharedprefapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MySolutionActivity extends AppCompatActivity {

    private final int MAX_USER_COUNT = 5;

    private String userCountKey = "users_in";
    private String userKeyPostfix = "user_";

    private int usersIn = 0;

    private SharedPreferences sharedPreferences;

    private EditText nameET, numberET;

    private TextView currentUsersTV;

    private Button saveUsersBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_solution_layout);

        sharedPreferences = this.getSharedPreferences("com.example.my_contacts", Context.MODE_PRIVATE);

        nameET = findViewById(R.id.editText);
        numberET = findViewById(R.id.editText2);
        usersIn = sharedPreferences.getInt(userCountKey, 0);
        currentUsersTV = findViewById(R.id.textView);
        saveUsersBtn = findViewById(R.id.button_save_user);

        saveUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MySolutionActivity.this, "Users in : "+usersIn, Toast.LENGTH_SHORT).show();
                if (usersIn == MAX_USER_COUNT) {
                    Toast.makeText(getApplicationContext(), "Max user count reached.", Toast.LENGTH_LONG).show();
                    clearText();
                } else {
                    String newUser = "Name : " + nameET.getText() + " Number : " + numberET.getText();
                    sharedPreferences.
                            edit()
                            .putString(userKeyPostfix + (usersIn), newUser)
                            .commit();

                    sharedPreferences.edit().putInt(userCountKey, (usersIn + 1))
                    .commit();
                    usersIn++;
                    clearText();
                    displayExistingUsers();
                }
            }
        });
    }

    private void clearText(){
        nameET.setText("");
        numberET.setText("");
    }
    @Override
    protected void onResume() {
        super.onResume();
        displayExistingUsers();
    }

    private void displayExistingUsers() {
        if (usersIn > 0) {
            StringBuilder myUsers = new StringBuilder();
            for (int i = 0; i < usersIn; i++) {
                String user = sharedPreferences.getString(userKeyPostfix + i, "FAILED");
                myUsers.append(user + "\n");
            }
            currentUsersTV.setText(myUsers);
        }
        else{
            currentUsersTV.setText("No users in");
        }
    }
}
