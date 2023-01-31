package com.moh.mhealth.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.moh.mhealth.Global_Helper;
import com.moh.mhealth.Header;
import com.moh.mhealth.Patient;
import com.moh.mhealth.R;

// Set village, time traveled, time waited
public class PatientSurvey extends AppCompatActivity implements Header {
    EditText villageField;

    NumberPicker distanceDaysPicker;
    NumberPicker distanceHoursPicker;

    NumberPicker timeHoursPicker;
    NumberPicker timeMinPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_survey);

        villageField = (EditText) findViewById(R.id.survey_village);

        distanceDaysPicker = (NumberPicker) findViewById(R.id.survey_distance_walked_days);
        distanceDaysPicker.setMinValue(0);
        distanceDaysPicker.setMaxValue(31);

        distanceHoursPicker = (NumberPicker) findViewById(R.id.survey_distance_walked_hours);
        distanceHoursPicker.setMinValue(0);
        distanceHoursPicker.setMaxValue(23);

        timeHoursPicker = (NumberPicker) findViewById(R.id.survey_time_waited_hr);
        timeHoursPicker.setMaxValue(48);
        timeHoursPicker.setMinValue(0);

        timeMinPicker = (NumberPicker) findViewById(R.id.survey_time_waited_min);
        timeMinPicker.setMaxValue(59);
        timeMinPicker.setMinValue(0);

        loadPatientData();

        Button saveBtn = (Button) findViewById(R.id.survey_next);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePatientData();

                startActivity(new Intent(getApplicationContext(), Global_Helper.nextDiag()));
            }
        });
    }

    private void updatePatientData() {

        Global_Helper.addPatientSurvey(villageField.getText().toString(),
                                       new int[] {distanceDaysPicker.getValue(),
                                                  distanceHoursPicker.getValue()},
                                       new int[] {timeHoursPicker.getValue(),
                                                  timeMinPicker.getValue()});
    }

    private void loadPatientData() {
        Patient patient = Global_Helper.getCurrentPatient();
        assert(patient != null);

        TextView patient_name = (TextView) findViewById(R.id.header_name);
        patient_name.setText(patient.getName());

        villageField.setText(patient.getVillage());

        int[] daysTraveled = patient.getDistance_traveled();
        distanceDaysPicker.setValue(daysTraveled[0]);
        distanceHoursPicker.setValue(daysTraveled[1]);

        int[] timeWaited = patient.getTime_waited();
        timeHoursPicker.setValue(timeWaited[0]);
        timeMinPicker.setValue(timeWaited[1]);
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