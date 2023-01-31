package com.moh.mhealth.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.moh.mhealth.Global_Helper;
import com.moh.mhealth.Patient;
import com.moh.mhealth.R;

import java.time.LocalDate;
import java.util.Date;

// Page to enter basic Patient information: Name, Gender, D.O.B.; and the desired measurement system

public class NewPatient extends AppCompatActivity {

    private String[] displayedGenders = {"F", "M", "O"};
    private String[] displayedUnits = {"Metric", "Imperial"};
    NumberPicker genderPicker;
    EditText nameField;
    DatePicker dobPicker;
    NumberPicker unitsPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        genderPicker = (NumberPicker) findViewById(R.id.np_gender);
        genderPicker.setMinValue(0);
        genderPicker.setMaxValue(displayedGenders.length - 1);
        genderPicker.setDisplayedValues(displayedGenders);

        nameField = (EditText) findViewById(R.id.np_name);

        dobPicker = (DatePicker) findViewById(R.id.np_dob);
        dobPicker.init(2000, 0, 1, null);

        unitsPicker = (NumberPicker) findViewById(R.id.np_units);
        unitsPicker.setMinValue(0);
        unitsPicker.setMaxValue(1);
        unitsPicker.setDisplayedValues(displayedUnits);


        loadPatientData();

        Button saveBtn = (Button) findViewById(R.id.np_create_patient);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePatientData();

                startActivity(new Intent(getApplicationContext(), Global_Helper.nextDiag()));
            }
        });
    }

    private void savePatientData() {

        Patient patient = Global_Helper.getCurrentPatient();
        if (patient == null) {
            Global_Helper.createNewPatient(nameField.getText().toString(),
                                           displayedGenders[genderPicker.getValue()].charAt(0),
                                           LocalDate.of(dobPicker.getYear(),
                                                 dobPicker.getMonth() + 1,
                                                        dobPicker.getDayOfMonth()));
        } else {
            patient.setName(nameField.getText().toString());
            patient.setGender(displayedGenders[genderPicker.getValue()].charAt(0));
            patient.setDob(LocalDate.of(dobPicker.getYear(),
                                 dobPicker.getMonth() + 1,
                                        dobPicker.getDayOfMonth()));
        }

        Global_Helper.setUnits(unitsPicker.getValue());
    }

    private void loadPatientData() {
        Patient patient = Global_Helper.getCurrentPatient();

        if (patient == null) {
            return; // Nothing to load
        }

        nameField.setText(patient.getName()); // Set name

        switch (patient.getGender()) { // Set gender
            case 'M':
                assert(displayedGenders[1] == "M");
                genderPicker.setValue(1);
                break;
            case 'O':
                assert(displayedGenders[2] == "O");
                genderPicker.setValue(2);
                break;
            default:
                assert(displayedGenders[0] == "F");
                genderPicker.setValue(0);
        }

        LocalDate dob = patient.getDob();
        dobPicker.init(dob.getYear(), dob.getMonthValue() - 1, dob.getDayOfMonth(), null);


        if (Global_Helper.isMetric()) {
            assert(displayedUnits[0] == "Metric");
            unitsPicker.setValue(0);
        } else {
            assert(displayedUnits[1] == "Imperial");
            unitsPicker.setValue(1);
        }
    }

    public void cancel(View view) {
        startActivity(new Intent(getApplicationContext(), Global_Helper.endPatient()));
    }
}