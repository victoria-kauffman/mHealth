package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.moh.mhealth.GlobalHelper
import com.moh.mhealth.Header
import com.moh.mhealth.R

class Diagnostics3 : AppCompatActivity(), Header {
    private var presField: EditText? = null
    private var assessField: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnostics3)
        presField = findViewById<View>(R.id.diag3_pres) as EditText
        assessField = findViewById<View>(R.id.diag3_assess) as EditText
        loadPatientData()
        val nextBtn = findViewById<View>(R.id.diag3_next) as Button
        nextBtn.setOnClickListener {
            updatePatientData()
            startActivity(Intent(applicationContext, GlobalHelper.nextDiag()))
        }
    }

    private fun updatePatientData() {
        GlobalHelper.addDiag3(
            presField!!.text.toString(),
            assessField!!.text.toString()
        )
    }

    private fun loadPatientData() {
        val patient = GlobalHelper.currentPatient!!
        val patientName = findViewById<View>(R.id.header_name) as TextView
        patientName.text = patient.name
        presField!!.setText(patient.pres)
        assessField!!.setText(patient.assess)
    }

    override fun cancel(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.endPatient()))
    }

    override fun moveBack(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.prevDiag()))
    }
}