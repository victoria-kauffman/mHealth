package com.moh.mhealth;

import com.moh.mhealth.pages.Diagnostics1;
import com.moh.mhealth.pages.Diagnostics2;
import com.moh.mhealth.pages.Diagnostics3;
import com.moh.mhealth.pages.Diagnostics4;
import com.moh.mhealth.pages.NewPatient;
import com.moh.mhealth.pages.PatientSurvey;

public class Patient_start {
    public static final Class[] diag_order = { NewPatient.class,
            PatientSurvey.class,
            Diagnostics1.class,
            Diagnostics2.class,
            Diagnostics3.class,
            Diagnostics4.class
    };

    private static int current_diag = 0;
    private static String current_patient;
    private static char gender;
    private static int[] date_ints; // day, month, year

    private static String village;
    private static int distance_traveled;
    private static String distance_unit;
    private static int[] time_waited; // hour, minute


    public static String get_current_patient() {
        return current_patient;
    }

    public static char get_gender() {
        return gender;
    }

    public static int[] get_date_ints() {
        return date_ints;
    }

    public static void set_new_patient(String new_patient, char new_gender, int[] new_date_ints) {
        current_patient = new_patient;
        gender = new_gender;
        date_ints = new_date_ints;
    }

    public static void set_patient_survey(String new_village, int new_distance_traveled, String new_distance_unit, int[] new_time_waited) {
         village = new_village;
         distance_traveled = new_distance_traveled;
         distance_unit = new_distance_unit;
         time_waited = new_time_waited;
    }

    public static Class next_diag() {
        assert (current_diag < 5);
        return diag_order[current_diag++];
    }

    public static Class prev_diag() {
        assert (current_diag > 0);
        return diag_order[current_diag--];
    }

    public static void cancel() {
        current_diag = 0;
        current_patient = "";
    }
}
