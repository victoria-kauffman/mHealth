package com.moh.mhealth.pages

import android.annotation.SuppressLint
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

class Diagnostics2 : AppCompatActivity(), Header {
    private var bp1Field: EditText? = null
    private var bp2Field: EditText? = null
    private var pField: EditText? = null
    private var rrField: EditText? = null
    private var muacField: EditText? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnostics2)
        if (!GlobalHelper.isMetric) {
            (findViewById<View>(R.id.diag2_muac_unit) as TextView).text = " inches"
        }
        bp1Field = findViewById<View>(R.id.diag2_bp1) as EditText
        bp2Field = findViewById<View>(R.id.diag2_bp2) as EditText
        pField = findViewById<View>(R.id.diag2_p) as EditText
        rrField = findViewById<View>(R.id.diag2_rr) as EditText
        muacField = findViewById<View>(R.id.diag2_muac_num) as EditText
        loadPatientData()
        val nextBtn = findViewById<View>(R.id.diag2_next) as Button
        nextBtn.setOnClickListener {
            updatePatientData()
            startActivity(Intent(applicationContext, GlobalHelper.nextDiag()))
        }
    }

    private fun updatePatientData() {
        val sys = GlobalHelper.getIntFromEditText(bp1Field)
        val dias = GlobalHelper.getIntFromEditText(bp2Field)
        val pulse = GlobalHelper.getIntFromEditText(pField)
        val rr = GlobalHelper.getIntFromEditText(rrField)
        var muac = GlobalHelper.getDoubleFromEditText(muacField)
        if (!GlobalHelper.isMetric) { // Gonna have to convert
            muac = GlobalHelper.getInchToCm(muac)
        }
        GlobalHelper.addDiag2(
            intArrayOf(sys, dias),
            pulse,
            rr,
            muac
        )
    }

    private fun loadPatientData() {
        val patient = GlobalHelper.currentPatient!!
        val bp = patient.bp
        val p = patient.p
        val rr = patient.rr
        var muac = patient.muac
        if (!GlobalHelper.isMetric && muac != null) { // Gonna have to convert
            muac = GlobalHelper.getCmToInch(muac)
        }
        val patientName = findViewById<View>(R.id.header_name) as TextView
        patientName.text = patient.name
        if (rr != null) {
            rrField!!.setText(rr.toString())
        }
        if (bp != null) {
            bp1Field!!.setText(bp[0])
            bp2Field!!.setText(bp[1])
        }
        if (p != null) {
            pField!!.setText(p)
        }
        if (muac != null) {
            muacField!!.setText(muac.toString())
        }
    }

    override fun cancel(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.endPatient()))
    }

    override fun moveBack(view: View) {
        startActivity(Intent(applicationContext, GlobalHelper.prevDiag()))
    }
}