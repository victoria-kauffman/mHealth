package com.moh.mhealth;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.moh.mhealth.pages.Diagnostics1;
import com.moh.mhealth.pages.Diagnostics2;
import com.moh.mhealth.pages.Diagnostics3;
import com.moh.mhealth.pages.Diagnostics4;
import com.moh.mhealth.pages.NewPatient;
import com.moh.mhealth.pages.PatientList;
import com.moh.mhealth.pages.PatientSurvey;

import java.time.LocalDate;
import java.util.Date;

public class Global_Helper {

    public static final String USERNAME = "user"; // Gonna need to fix
    public static final String PASSWORD = "1234"; // Gonna need to fix

    private static final Class[] diagOrder = {
            PatientList.class,
            NewPatient.class,
            PatientSurvey.class,
            Diagnostics1.class,
            Diagnostics2.class,
            Diagnostics3.class,
            Diagnostics4.class,
    };

    private static Patient currentPatient;

    private static int currentDiag = 0;

    private static boolean isMetric;


    public static double getLbToKg(double lb) {
        return Math.round(lb * 0.45359237 * 100.0) / 100.0;
    }
    public static double getInchToCm(double in) {
        return Math.round(in * 2.54 * 100.0) / 100.0;
    }
    public static double getFToC(double f) {
        return Math.round((f - 32) * (5/9.0) * 100.0) / 100.0;
    }

    public static double getKgToLb(double kg) {
        return Math.round(kg / 0.45359237 * 100.0) / 100.0;
    }
    public static double getCmToInch(double cm) {
        return Math.round(cm / 2.54 * 100.0) / 100.0;
    }
    public static double getCToF(double c) {
        return Math.round(((9/5.0 * c) + 32) * 100.0) / 100.0;
    }

    public static double getDoubleFromEditText(EditText editText) {
        String doubleStr = editText.getText().toString();

        if (!doubleStr.isEmpty()) {
            try {
                return Double.parseDouble(doubleStr);
            } catch (Exception e1) {
                // I don't really care about the error I don't think...
            }
        }
        return -1;
    }

    public static int getIntFromEditText(EditText editText) {
        String intStr = editText.getText().toString();

        if (!intStr.isEmpty()) {
            try {
                return Integer.parseInt(intStr);
            } catch (Exception e1) {
                // I don't really care about the error I don't think...
            }
        }
        return -1;
    }

    public static void createNewPatient(String name, char gender, LocalDate dob) {
        currentPatient = new Patient(name, gender, dob);
    }


    public static void addPatientSurvey(String village, int[] distanceTraveled, int[] timeWaited) {
        currentPatient.setVillage(village);
        currentPatient.setDistance_traveled(distanceTraveled);
        currentPatient.setTime_waited(timeWaited);
    }
    public static void setUnits(int unitsInt) {
        isMetric = (unitsInt == 0) ? true : false;
    }

    public static void addDiag1Metric(double kg, int cm, double tempC) {
        currentPatient.setWeight(kg);
        currentPatient.setHeight(cm);
        currentPatient.setTemp(tempC);
    }
    // Wrapper to both save diag1 variables and convert imperial to metric
    public static void addDiag1Imperial(double lbs, int ft, int inch, double tempF) {
        addDiag1Metric(getLbToKg(lbs), (int) getInchToCm(ft * 12 + inch), getFToC(tempF));
    }

    public static void addDiag2(int[] bp, int p, int rr, double muacCM) {
        currentPatient.setBp(bp);
        currentPatient.setP(p);
        currentPatient.setRr(rr);
        currentPatient.setMuac(muacCM);
    }

    public static void addDiag3(String pres, String assess) {
        currentPatient.setPres(pres);
        currentPatient.setAssess(assess);
    }

    public static void addDiag4(String diag, String treat) {
        currentPatient.setDiag(diag);
        currentPatient.setTreat(treat);
    }

    public static boolean isMetric() {
        return isMetric;
    }

    public static Patient getCurrentPatient() {
        return currentPatient;
    }

    public static Class nextDiag() {
        Log.i("NextDiag", currentDiag + ":: " + diagOrder[currentDiag].getName());
        assert (currentDiag < diagOrder.length - 1);
        currentDiag++;
        return diagOrder[currentDiag];
    }

    public static Class prevDiag() {
        assert (currentDiag > 0);

        if (currentDiag == 1) { // Gonna be returning to the patient list page, so cancel current patient
            currentPatient = null;
        }
        currentDiag--;
        return diagOrder[currentDiag];
    }

    public static Class endPatient() {
        currentDiag = 0;
        currentPatient = null;

        isMetric = true;

        return diagOrder[0];
    }
}
