package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.moh.mhealth.Global_Helper
import com.moh.mhealth.Header
import com.moh.mhealth.R

class Diagnostics3 : AppCompatActivity(), Header {
    var presField: EditText? = null
    var assessField: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnostics3)
        presField = findViewById<View>(R.id.diag3_pres) as EditText
        assessField = findViewById<View>(R.id.diag3_assess) as EditText
        loadPatientData()
        val nextBtn = findViewById<View>(R.id.diag3_next) as Button
        nextBtn.setOnClickListener {
            updatePatientData()
            startActivity(Intent(applicationContext, Global_Helper.nextDiag()))
        }
    }

    private fun updatePatientData() {
        Global_Helper.addDiag3(
            presField!!.text.toString(),
            assessField!!.text.toString()
        )
    }

    private fun loadPatientData() {
        val patient = Global_Helper.getCurrentPatient()
        val patient_name = findViewById<View>(R.id.header_name) as TextView
        patient_name.text = patient.name
        presField.setText(patient.pres)
        assessField.setText(patient.assess)
    }

    override fun cancel(view: View?) {
        startActivity(Intent(applicationContext, Global_Helper.endPatient()))
    }

    override fun moveBack(view: View?) {
        startActivity(Intent(applicationContext, Global_Helper.prevDiag()))
    }
}