package com.moh.mhealth.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moh.mhealth.Patient_start;
import com.moh.mhealth.R;

// Header of each page other than MainActivity / NewPatient

public class Header extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header);

        Button prevBtn = (Button) findViewById(R.id.header_prev);
        Button cancelBtn = (Button) findViewById(R.id.header_cancel);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Patient_start.prev_diag());
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Patient_start.cancel();
                Intent intent = new Intent(getApplicationContext(), NewPatient.class);
                startActivity(intent);
            }
        });


        TextView patient_name = (TextView) findViewById(R.id.header_name);
        patient_name.setText(Patient_start.get_current_patient());
    }


}