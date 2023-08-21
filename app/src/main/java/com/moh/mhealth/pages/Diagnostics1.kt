package com.moh.mhealth.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.moh.mhealth.GlobalHelper
import com.moh.mhealth.Header
import com.moh.mhealth.R
import kotlin.math.roundToInt

// Set weight, height, and temperature
class Diagnostics1 : AppCompatActivity(), Header {
    private var height1Picker: NumberPicker? = null
    private var height2Picker: NumberPicker? = null
    private var tempField: EditText? = null
    private var weightField: EditText? = null
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
        if (!GlobalHelper.isMetric) {
            setImperial()
        }
        loadPatientData()
        val nextBtn = findViewById<View>(R.id.diag1_next) as Button
        nextBtn.setOnClickListener {
            updatePatientData()
            startActivity(Intent(applicationContext, GlobalHelper.nextDiag()))
        }
    }

    @SuppressLint("SetTextI18n")
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
        val weight = GlobalHelper.getDoubleFromEditText(weightField)
        val height1 = height1Picker!!.value
        val temp = GlobalHelper.getDoubleFromEditText(tempField)
        if (!GlobalHelper.isMetric) { // Gonna have to convert
            GlobalHelper.addDiag1Imperial(
                weight,
                height1,
                height2Picker!!.value,
                temp
            )
        } else {
            GlobalHelper.addDiag1Metric(
                weight,
                height1,
                temp
            )
        }
    }

    private fun loadPatientData() {
        val patient = GlobalHelper.currentPatient!!
        var weight = patient.weight
        var height = patient.height
        var temp = patient.temp
        if (!GlobalHelper.isMetric) { // Gonna have to convert
            if (weight != null) {
                weight = GlobalHelper.getKgToLb(weight)
            }
            if (height != null) {
                val heightInches = GlobalHelper.getCmToInch(height.toDouble()).roundToInt()
                height = heightInches / 12
                height2Picker!!.value = heightInches % 12
            }
            if (temp != null) {
                temp = GlobalHelper.getCToF(temp)
            }
        }
        val patientName = findViewById<View>(R.id.header_name) as TextView
        patientName.text = patient.name
        if (weight != null) {
            weightField!!.setText(weight.toString())
        }
        if (height != null) {
            height1Picker!!.value = height
        }
        if (temp != null) {
            tempField!!.setText(temp.toString())
        }
    }

    override fun cancel(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.endPatient()))
    }

    override fun moveBack(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.prevDiag()))
    }
}