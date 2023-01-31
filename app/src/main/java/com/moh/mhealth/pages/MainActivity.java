package com.moh.mhealth.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moh.mhealth.Global_Helper;
import com.moh.mhealth.PatientDatabase;
import com.moh.mhealth.R;

public class MainActivity extends AppCompatActivity {

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
                
                if (user.equals(Global_Helper.USERNAME) && pass.equals(Global_Helper.PASSWORD)) {
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), PatientList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}