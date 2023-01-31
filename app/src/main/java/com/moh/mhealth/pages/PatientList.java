package com.moh.mhealth.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.moh.mhealth.Global_Helper;
import com.moh.mhealth.PatientDatabase;
import com.moh.mhealth.R;

public class PatientList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        Button newPatient = (Button) findViewById(R.id.patient_list_new_patient_button);

        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Global_Helper.nextDiag()));
            }
        });

        loadPatientList();
    }

    private void loadPatientList() {
        PatientDatabase db = PatientDatabase.getDbInstance(getApplicationContext());

    }
}