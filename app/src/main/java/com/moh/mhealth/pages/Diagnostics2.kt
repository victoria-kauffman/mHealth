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

class Diagnostics2 : AppCompatActivity(), Header {
    var bp1Field: EditText? = null
    var bp2Field: EditText? = null
    var pField: EditText? = null
    var rrField: EditText? = null
    var muacField: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnostics2)
        if (!Global_Helper.isMetric()) {
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
            startActivity(Intent(applicationContext, Global_Helper.nextDiag()))
        }
    }

    private fun updatePatientData() {
        val sys = Global_Helper.getIntFromEditText(bp1Field)
        val dias = Global_Helper.getIntFromEditText(bp2Field)
        val pulse = Global_Helper.getIntFromEditText(pField)
        val rr = Global_Helper.getIntFromEditText(rrField)
        var muac = Global_Helper.getDoubleFromEditText(muacField)
        if (!Global_Helper.isMetric()) { // Gonna have to convert
            muac = Global_Helper.getInchToCm(muac)
        }
        Global_Helper.addDiag2(
            intArrayOf(sys, dias),
            pulse,
            rr,
            muac
        )
    }

    private fun loadPatientData() {
        val patient = Global_Helper.getCurrentPatient()
        val bp = patient.bp
        val p = patient.p
        val rr = patient.rr
        var muac = patient.muac
        if (!Global_Helper.isMetric() && muac > 0) { // Gonna have to convert
            muac = Global_Helper.getCmToInch(muac)
        }
        val patient_name = findViewById<View>(R.id.header_name) as TextView
        patient_name.text = patient.name
        if (rr >= 0) {
            rrField!!.setText(rr)
        }
        if (bp!![0] >= 0) {
            bp1Field!!.setText(bp!![0])
            bp2Field!!.setText(bp!![1])
        }
        if (p >= 0) {
            pField!!.setText(p)
        }
        if (muac >= 0) {
            muacField!!.setText(muac.toString() + "")
        }
    }

    override fun cancel(view: View?) {
        startActivity(Intent(applicationContext, Global_Helper.endPatient()))
    }

    override fun moveBack(view: View?) {
        startActivity(Intent(applicationContext, Global_Helper.prevDiag()))
    }
}