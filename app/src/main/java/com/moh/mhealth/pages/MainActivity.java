package com.moh.mhealth.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moh.mhealth.PatientDatabase;
import com.moh.mhealth.R;

public class MainActivity extends AppCompatActivity {

    String USERNAME = "user"; // Gonna need to fix
    String PASSWORD = "1234"; // Gonna need to fix

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username, password;
        username = (EditText) findViewById(R.id.main_username);
        password = (EditText) findViewById(R.id.main_password);

        Button loginBtn = (Button) findViewById(R.id.main_login_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();
                
                if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                    Toast.makeText(MainActivity.this, "Welcome, " + user, Toast.LENGTH_SHORT).show();

                    // Initialize database
                    PatientDatabase db = Room.databaseBuilder(getApplicationContext(),
                                                              PatientDatabase.class,
                                                              "patient-database").build();

                    Intent intent = new Intent(getApplicationContext(), NewPatient.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}