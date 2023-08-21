package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.moh.mhealth.GlobalHelper
import com.moh.mhealth.Header
import com.moh.mhealth.R

// Set village, time traveled, time waited
class PatientSurvey : AppCompatActivity(), Header {
    private var villageField: EditText? = null
    private var distanceDaysPicker: NumberPicker? = null
    private var distanceHoursPicker: NumberPicker? = null
    private var timeHoursPicker: NumberPicker? = null
    private var timeMinPicker: NumberPicker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_survey)
        villageField = findViewById<View>(R.id.survey_village) as EditText
        distanceDaysPicker = findViewById<View>(R.id.survey_distance_walked_days) as NumberPicker
        distanceDaysPicker!!.minValue = 0
        distanceDaysPicker!!.maxValue = 31
        distanceHoursPicker = findViewById<View>(R.id.survey_distance_walked_hours) as NumberPicker
        distanceHoursPicker!!.minValue = 0
        distanceHoursPicker!!.maxValue = 23
        timeHoursPicker = findViewById<View>(R.id.survey_time_waited_hr) as NumberPicker
        timeHoursPicker!!.maxValue = 48
        timeHoursPicker!!.minValue = 0
        timeMinPicker = findViewById<View>(R.id.survey_time_waited_min) as NumberPicker
        timeMinPicker!!.maxValue = 59
        timeMinPicker!!.minValue = 0
        loadPatientData()
        val saveBtn = findViewById<View>(R.id.survey_next) as Button
        saveBtn.setOnClickListener {
            updatePatientData()
            startActivity(Intent(applicationContext, GlobalHelper.nextDiag()))
        }
    }

    private fun updatePatientData() {
        GlobalHelper.addPatientSurvey(
            villageField!!.text.toString(), intArrayOf(
                distanceDaysPicker!!.value,
                distanceHoursPicker!!.value
            ), intArrayOf(
                timeHoursPicker!!.value,
                timeMinPicker!!.value
            )
        )
    }

    private fun loadPatientData() {
        val patient = GlobalHelper.currentPatient!!
        val patientName = findViewById<View>(R.id.header_name) as TextView
        patientName.text = patient.name
        villageField!!.setText(patient.village)
        val daysTraveled = patient.distanceTraveled
        if (daysTraveled != null) {
            distanceDaysPicker!!.value = daysTraveled[0]
            distanceHoursPicker!!.value = daysTraveled[1]
        }
        val timeWaited = patient.timeWaited
        if (timeWaited != null) {
            timeHoursPicker!!.value = timeWaited   [0]
            timeMinPicker!!.value = timeWaited[1]
        }
    }

    override fun cancel(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.endPatient()))
    }

    override fun moveBack(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.prevDiag()))
    }
}