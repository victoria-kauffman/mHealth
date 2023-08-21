package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.moh.mhealth.GlobalHelper
import com.moh.mhealth.R
import java.time.LocalDate

// Page to enter basic Patient information: Name, Gender, D.O.B.; and the desired measurement system
class NewPatient : AppCompatActivity() {
    private val displayedGenders = arrayOf("F", "M", "O")
    private val displayedUnits = arrayOf("Metric", "Imperial")
    private var genderPicker: NumberPicker? = null
    private var nameField: EditText? = null
    private var dobPicker: DatePicker? = null
    private var unitsPicker: NumberPicker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_patient)
        genderPicker = findViewById<View>(R.id.np_gender) as NumberPicker
        genderPicker!!.minValue = 0
        genderPicker!!.maxValue = displayedGenders.size - 1
        genderPicker!!.displayedValues = displayedGenders
        nameField = findViewById<View>(R.id.np_name) as EditText
        dobPicker = findViewById<View>(R.id.np_dob) as DatePicker
        dobPicker!!.init(2000, 0, 1, null)
        unitsPicker = findViewById<View>(R.id.np_units) as NumberPicker
        unitsPicker!!.minValue = 0
        unitsPicker!!.maxValue = 1
        unitsPicker!!.displayedValues = displayedUnits
        loadPatientData()
        val saveBtn = findViewById<View>(R.id.np_create_patient) as Button
        saveBtn.setOnClickListener {
            savePatientData()
            startActivity(Intent(applicationContext, GlobalHelper.nextDiag()))
        }
    }

    private fun savePatientData() {
        val patient = GlobalHelper.currentPatient
        if (patient == null) {
            GlobalHelper.createNewPatient(
                nameField!!.text.toString(),
                displayedGenders[genderPicker!!.value][0],
                LocalDate.of(
                    dobPicker!!.year,
                    dobPicker!!.month + 1,
                    dobPicker!!.dayOfMonth
                )
            )
        } else {
            patient.name = nameField!!.text.toString()
            patient.gender = displayedGenders[genderPicker!!.value][0]
            patient.dob = LocalDate.of(
                dobPicker!!.year,
                dobPicker!!.month + 1,
                dobPicker!!.dayOfMonth
            )
        }
        GlobalHelper.isMetric = (unitsPicker!!.value == 0)
    }

    private fun loadPatientData() {
        val patient = GlobalHelper.currentPatient
            ?: return  // Nothing to load
        nameField!!.setText(patient.name) // Set name
        when (patient.gender) {
            'M' -> {
                assert(displayedGenders[1] === "M")
                genderPicker!!.value = 1
            }
            'O' -> {
                assert(displayedGenders[2] === "O")
                genderPicker!!.value = 2
            }
            else -> {
                assert(displayedGenders[0] === "F")
                genderPicker!!.value = 0
            }
        }
        val dob = patient.dob
        dobPicker!!.init(dob!!.year, dob.monthValue - 1, dob.dayOfMonth, null)
        if (GlobalHelper.isMetric) {
            assert(displayedUnits[0] === "Metric")
            unitsPicker!!.value = 0
        } else {
            assert(displayedUnits[1] === "Imperial")
            unitsPicker!!.value = 1
        }
    }

    fun cancel(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.endPatient()))
    }
}