package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.moh.mhealth.*
import com.moh.mhealth.patientdatabase.PatientViewModel

class Diagnostics4 : AppCompatActivity(), Header {
    private var diagField: EditText? = null
    private var treatField: EditText? = null

    private val patientViewModel: PatientViewModel by viewModels { PatientViewModel.Factory }

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
            startActivity(Intent(applicationContext, GlobalHelper.endPatient()))
        }
    }

    private fun updatePatientData() {
        GlobalHelper.addDiag4(
            diagField!!.text.toString(),
            treatField!!.text.toString()
        )
    }

    private fun savePatient() {
        patientViewModel.insert(GlobalHelper.currentPatient!!)
        Toast.makeText(this, "Patient Saved.", Toast.LENGTH_SHORT).show()
    }

    private fun loadPatientData() {
        val patient = GlobalHelper.currentPatient!!
        val patientName = findViewById<View>(R.id.header_name) as TextView
        patientName.text = patient.name
        diagField!!.setText(patient.diag)
        treatField!!.setText(patient.treat)
    }

    override fun cancel(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.endPatient()))
    }

    override fun moveBack(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.prevDiag()))
    }
}