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

public class Diagnostics3 extends AppCompatActivity implements Header {

    EditText presField, assessField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostics3);

        presField = (EditText) findViewById(R.id.diag3_pres);
        assessField = (EditText) findViewById(R.id.diag3_assess);

        loadPatientData();

        Button nextBtn = (Button) findViewById(R.id.diag3_next);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePatientData();

                startActivity(new Intent(getApplicationContext(), Global_Helper.nextDiag()));
            }
        });
    }

    private void updatePatientData() {
        Global_Helper.addDiag3(presField.getText().toString(),
                               assessField.getText().toString());
    }

    private void loadPatientData() {
        Patient patient = Global_Helper.getCurrentPatient();
        TextView patient_name = (TextView) findViewById(R.id.header_name);
        patient_name.setText(patient.getName());

        presField.setText(patient.getPres());
        assessField.setText(patient.getAssess());
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