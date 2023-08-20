package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.moh.mhealth.Global_Helper
import com.moh.mhealth.Header
import com.moh.mhealth.R

// Set weight, height, and temperature
class Diagnostics1 : AppCompatActivity(), Header {
    var height1Picker: NumberPicker? = null
    var height2Picker: NumberPicker? = null
    var tempField: EditText? = null
    var weightField: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnostics1)
        height1Picker = findViewById<View>(R.id.diag1_height1_num) as NumberPicker
        height2Picker = findViewById<View>(R.id.diag1_height2_num) as NumberPicker
        height1Picker!!.minValue = 0
        height1Picker!!.maxValue = 250
        height1Picker!!.value = 175
        tempField = findViewById<View>(R.id.diag1_temp_num) as EditText
        weightField = findViewById<View>(R.id.diag1_weight_num) as EditText
        if (!Global_Helper.isMetric()) {
            setImperial()
        }
        loadPatientData()
        val nextBtn = findViewById<View>(R.id.diag1_next) as Button
        nextBtn.setOnClickListener {
            updatePatientData()
            startActivity(Intent(applicationContext, Global_Helper.nextDiag()))
        }
    }

    private fun setImperial() {
        // Set kg to lbs
        (findViewById<View>(R.id.diag1_weight_unit_text) as TextView).text = "lbs"

        // Set cm to ft
        (findViewById<View>(R.id.diag1_height1_unit_text) as TextView).text = "ft"

        // Change max values of the two number pickers
        height1Picker!!.maxValue = 10
        height1Picker!!.value = 5
        height2Picker!!.maxValue = 11

        // Show the inches layout
        (findViewById<View>(R.id.diag1_height2) as LinearLayout).visibility =
            View.VISIBLE

        // Set C to F
        (findViewById<View>(R.id.diag1_temp_unit_text) as TextView).text = "\u00B0F"
    }

    private fun updatePatientData() {
        val weight = Global_Helper.getDoubleFromEditText(weightField)
        val height1 = height1Picker!!.value
        val temp = Global_Helper.getDoubleFromEditText(tempField)
        if (!Global_Helper.isMetric()) { // Gonna have to convert
            Global_Helper.addDiag1Imperial(
                weight,
                height1,
                height2Picker!!.value,
                temp
            )
        } else {
            Global_Helper.addDiag1Metric(
                weight,
                height1,
                temp
            )
        }
    }

    private fun loadPatientData() {
        val patient = Global_Helper.getCurrentPatient()
        var weight = patient.weight
        var height = patient.height
        var temp = patient.temp
        if (!Global_Helper.isMetric()) { // Gonna have to convert
            if (weight >= 0) {
                weight = Global_Helper.getKgToLb(weight)
            }
            if (height > 0) {
                val heightInches = Math.round(Global_Helper.getCmToInch(height.toDouble())).toInt()
                height = heightInches / 12
                height2Picker!!.value = heightInches % 12
            }
            if (temp >= 0) {
                temp = Global_Helper.getCToF(temp)
            }
        }
        val patient_name = findViewById<View>(R.id.header_name) as TextView
        patient_name.text = patient.name
        if (weight >= 0) {
            weightField!!.setText(weight.toString() + "")
        }
        height1Picker!!.value = height
        if (temp >= 0) {
            tempField!!.setText(temp.toString() + "")
        }
    }

    override fun cancel(view: View) {
        startActivity(Intent(applicationContext, Global_Helper.endPatient()))
    }

    override fun moveBack(view: View) {
        startActivity(Intent(applicationContext, Global_Helper.prevDiag()))
    }
}