package com.moh.mhealth.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moh.mhealth.Global_Helper;
import com.moh.mhealth.Header;
import com.moh.mhealth.Patient;
import com.moh.mhealth.R;

public class Diagnostics4 extends AppCompatActivity implements Header {

    EditText diagField, treatField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostics4);

        diagField = (EditText) findViewById(R.id.diag4_diag);
        treatField = (EditText) findViewById(R.id.diag4_diag);

        loadPatientData();

        Button nextBtn = (Button) findViewById(R.id.diag4_finish);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePatientData();
                savePatient();

                startActivity(new Intent(getApplicationContext(), Global_Helper.endPatient()));
            }
        });
    }

    private void updatePatientData() {
        Global_Helper.addDiag4(diagField.getText().toString(),
                               treatField.getText().toString());
    }

    private void savePatient() {

    }

    private void loadPatientData() {
        Patient patient = Global_Helper.getCurrentPatient();

        TextView patient_name = (TextView) findViewById(R.id.header_name);
        patient_name.setText(patient.getName());

        diagField.setText(patient.getDiag());
        treatField.setText(patient.getTreat());
    }


    @Override
    public void cancel(View view) {
        startActivity(new Intent(getApplicationContext(), Global_Helper.endPatient()));
    }

    @Override
    public void moveBack(View view) {
        startActivity(new Intent(getApplicationContext(), Global_Helper.prevDiag()));
    }
}