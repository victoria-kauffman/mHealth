package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.moh.mhealth.*

class Diagnostics4 : AppCompatActivity(), Header {
    var diagField: EditText? = null
    var treatField: EditText? = null

    private val patientViewModel: PatientViewModel by viewModels {
        PatientViewModelFactory(( application as PatientApplication ).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnostics4)

        diagField = findViewById<View>(R.id.diag4_diag) as EditText
        treatField = findViewById<View>(R.id.diag4_treat) as EditText

        loadPatientData()

        val nextBtn = findViewById<View>(R.id.diag4_finish) as Button
        nextBtn.setOnClickListener {
            updatePatientData()
            savePatient()
            startActivity(Intent(applicationContext, Global_Helper.endPatient()))
        }
    }

    private fun updatePatientData() {
        Global_Helper.addDiag4(
            diagField!!.text.toString(),
            treatField!!.text.toString()
        )
    }

    private fun savePatient() {
        patientViewModel.insert( Global_Helper.currentPatient)
        Toast.makeText(this, "Patient Saved.", Toast.LENGTH_SHORT).show()
    }

    private fun loadPatientData() {
        val patient = Global_Helper.currentPatient
        val patient_name = findViewById<View>(R.id.header_name) as TextView
        patient_name.text = patient.name
        diagField!!.setText(patient.diag)
        treatField!!.setText(patient.treat)
    }

    override fun cancel(view: View) {
        startActivity(Intent(applicationContext, Global_Helper.endPatient()))
    }

    override fun moveBack(view: View) {
        startActivity(Intent(applicationContext, Global_Helper.prevDiag()))
    }
}