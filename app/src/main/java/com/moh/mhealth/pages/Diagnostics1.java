package com.moh.mhealth.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.moh.mhealth.Global_Helper;
import com.moh.mhealth.Header;
import com.moh.mhealth.Patient;
import com.moh.mhealth.R;

import org.w3c.dom.Text;

// Set weight, height, and temperature
public class Diagnostics1 extends AppCompatActivity implements Header {

    NumberPicker height1Picker, height2Picker;

    EditText tempField, weightField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostics1);


        height1Picker = (NumberPicker) findViewById(R.id.diag1_height1_num);
        height2Picker = (NumberPicker) findViewById(R.id.diag1_height2_num);

        height1Picker.setMinValue(0);
        height1Picker.setMaxValue(250);
        height1Picker.setValue(175);

        tempField = (EditText) findViewById(R.id.diag1_temp_num);
        weightField = (EditText) findViewById(R.id.diag1_weight_num);

        if (! Global_Helper.isMetric()) {
            setImperial();
        }

        loadPatientData();

        Button nextBtn = (Button) findViewById(R.id.diag1_next);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePatientData();

                startActivity(new Intent(getApplicationContext(), Global_Helper.nextDiag()));
            }
        });
    }

    private void setImperial() {
        // Set kg to lbs
        ((TextView) findViewById(R.id.diag1_weight_unit_text)).setText("lbs");

        // Set cm to ft
        ((TextView) findViewById(R.id.diag1_height1_unit_text)).setText("ft");

        // Change max values of the two number pickers
        height1Picker.setMaxValue(10);
        height1Picker.setValue(5);
        height2Picker.setMaxValue(11);

        // Show the inches layout
        ((LinearLayout) findViewById(R.id.diag1_height2)).setVisibility(View.VISIBLE);

        // Set C to F
        ((TextView) findViewById(R.id.diag1_temp_unit_text)).setText("\u00B0F");
    }

    private void updatePatientData() {
        double weight = Global_Helper.getDoubleFromEditText(weightField);

        int height1 = height1Picker.getValue();

        double temp = Global_Helper.getDoubleFromEditText(tempField);

        if (! Global_Helper.isMetric()) { // Gonna have to convert
            Global_Helper.addDiag1Imperial(weight,
                                           height1,
                                           height2Picker.getValue(),
                                           temp);
        } else {
            Global_Helper.addDiag1Metric(weight,
                                         height1,
                                         temp);
        }
    }

    private void loadPatientData() {
        Patient patient = Global_Helper.getCurrentPatient();

        double weight = patient.getWeight();
        int height = patient.getHeight();
        double temp = patient.getTemp();

        if (! Global_Helper.isMetric()) { // Gonna have to convert
            if (weight >= 0) {
                weight = Global_Helper.getKgToLb(weight);
            }

            /*

            125.9
            6' 8''
            102.5
             */
            if (height > 0) {
                int heightInches = (int) Math.round(Global_Helper.getCmToInch(height));

                height = heightInches / 12;

                height2Picker.setValue(heightInches % 12);
            }

            if (temp >= 0) {
                temp = Global_Helper.getCToF(temp);
            }
        }

        TextView patient_name = (TextView) findViewById(R.id.header_name);
        patient_name.setText(patient.getName());

        if (weight >= 0) {
            weightField.setText(weight + "");
        }
        height1Picker.setValue(height);

        if (temp >= 0) {
            tempField.setText(temp + "");
        }
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