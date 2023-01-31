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

public class Diagnostics2 extends AppCompatActivity implements Header {

    EditText bp1Field, bp2Field, pField, rrField, muacField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostics2);

        if (! Global_Helper.isMetric()) {
            ((TextView) findViewById(R.id.diag2_muac_unit)).setText(" inches");
        }

        bp1Field = (EditText) findViewById(R.id.diag2_bp1);
        bp2Field = (EditText) findViewById(R.id.diag2_bp2);
        pField = (EditText) findViewById(R.id.diag2_p);
        rrField = (EditText) findViewById(R.id.diag2_rr);
        muacField = (EditText) findViewById(R.id.diag2_muac_num);

        loadPatientData();

        Button nextBtn = (Button) findViewById(R.id.diag2_next);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePatientData();

                startActivity(new Intent(getApplicationContext(), Global_Helper.nextDiag()));
            }
        });
    }

    private void updatePatientData() {
        int sys = Global_Helper.getIntFromEditText(bp1Field);
        int dias = Global_Helper.getIntFromEditText(bp2Field);
        int pulse = Global_Helper.getIntFromEditText(pField);
        int rr = Global_Helper.getIntFromEditText(rrField);

        double muac = Global_Helper.getDoubleFromEditText(muacField);

        if (! Global_Helper.isMetric()) { // Gonna have to convert
            muac = Global_Helper.getInchToCm(muac);
        }
        Global_Helper.addDiag2(new int[] {sys, dias},
                               pulse,
                               rr,
                               muac);
    }

    private void loadPatientData() {
        Patient patient = Global_Helper.getCurrentPatient();

        int[] bp = patient.getBp();
        int p = patient.getP();
        int rr = patient.getRr();

        double muac = patient.getMuac();

        if (! Global_Helper.isMetric() && muac > 0) { // Gonna have to convert
            muac = Global_Helper.getCmToInch(muac);
        }

        TextView patient_name = (TextView) findViewById(R.id.header_name);
        patient_name.setText(patient.getName());

        if (rr >= 0) {
            rrField.setText(rr);
        }
        if (bp[0] >= 0) {
            bp1Field.setText(bp[0]);
            bp2Field.setText(bp[1]);
        }
        if (p >= 0) {
            pField.setText(p);
        }
        if (muac >= 0) {
            muacField.setText(muac + "");
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