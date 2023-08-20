package com.moh.mhealth.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.moh.mhealth.*
import java.time.format.DateTimeFormatter
import kotlin.math.truncate

class PatientPdf : AppCompatActivity() {

    private val patientViewModel: PatientViewModel by viewModels {
        PatientViewModelFactory(( application as PatientApplication ).repository)
    }

    var switchUnit : Button? = null
    var patient : Patient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_pdf)

        var isMetric : Boolean = true

        patientViewModel.patientById.observe(this, Observer {
            patient = patientViewModel.patientById.value!![0]
            setPdfInfo(patient!!)
            setMetric(patient!!)
        })
        patientViewModel.getPatient(intent.extras!!.getInt("pid"))


        val backButton : Button = findViewById(R.id.return_to_pl)
        backButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, PatientList::class.java))
        })

        switchUnit = findViewById(R.id.switch_units)
        switchUnit!!.setOnClickListener(View.OnClickListener {
            isMetric = ! isMetric

            if (isMetric) {
                setMetric(patient)
            } else {
                setImperial(patient)
            }
        })


        val generatePDF : Button = findViewById(R.id.generate_pdf)
        generatePDF.setOnClickListener(View.OnClickListener {

        })
    }

    private fun setPdfInfo(patient : Patient) {
        findViewById<TextView>(R.id.pdf_name).text = patient.name
        findViewById<TextView>(R.id.pdf_gender).append(patient.gender.toString())
        findViewById<TextView>(R.id.pdf_dob).append(patient.dob.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")))

        findViewById<TextView>(R.id.pdf_village).append(patient.village)
        findViewById<TextView>(R.id.pdf_distance_travled).append(patient.distance_traveled[0].toString() + " days, " +
                patient.distance_traveled[1].toString() + " hours")
        findViewById<TextView>(R.id.pdf_time_waited).append(patient.time_waited[0].toString() + " hours, " +
                patient.time_waited[1].toString() + " minutes")



        findViewById<TextView>(R.id.pdf_bp).append(patient.bp[0].toString() + " / " + patient.bp[1].toString())
        findViewById<TextView>(R.id.pdf_pulse).append(patient.p.toString())
        findViewById<TextView>(R.id.pdf_rr).append(patient.rr.toString())

        findViewById<TextView>(R.id.pdf_pres).text = (patient.pres)
        findViewById<TextView>(R.id.pdf_assess).text = (patient.assess)

        findViewById<TextView>(R.id.pdf_diag).text = (patient.diag)
        findViewById<TextView>(R.id.pdf_treat).text = (patient.treat)
    }

    private fun setMetric(patient : Patient?) {
        if (patient == null) {
            return
        }
        findViewById<TextView>(R.id.pdf_weight).text = "Weight: " + (patient.weight.toString() + " kg")
        findViewById<TextView>(R.id.pdf_height).text = "Height: " + (patient.height.toString() + " cm")
        findViewById<TextView>(R.id.pdf_temp).text = "Temperature: " + (patient.temp.toString() + " °C")

        findViewById<TextView>(R.id.pdf_muac).text = "Middle Upper Arm Circumference: "  + (patient.muac.toString() + " cm")
    }

    private fun setImperial(patient : Patient?) {
        if (patient == null) {
            return
        }
        findViewById<TextView>(R.id.pdf_weight).text = "Weight: " + (Global_Helper.getKgToLb(patient.weight).toString() + " lbs")

        val patientHeight = Global_Helper.getCmToInch(patient.height.toDouble())
        findViewById<TextView>(R.id.pdf_height).text = "Height: " + (patientHeight / 12).toInt().toString() + "\'" + Math.round(patientHeight % 12).toInt().toString() + "\""
        findViewById<TextView>(R.id.pdf_temp).text = "Temperature: " + (Global_Helper.getCToF(patient.temp).toString()) + " °F"

        findViewById<TextView>(R.id.pdf_muac).text = "Middle Upper Arm Circumference: "  + (Global_Helper.getCmToInch(patient.muac).toString()) + " inches"
    }
}