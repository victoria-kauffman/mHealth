package com.moh.mhealth.pages

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.moh.mhealth.*
import com.moh.mhealth.objects.Patient
import com.moh.mhealth.patientdatabase.PatientViewModel
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class PatientPdf : AppCompatActivity() {

    private val patientViewModel: PatientViewModel by viewModels { PatientViewModel.Factory }

    private var switchUnit : Button? = null
    var patient : Patient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_pdf)

        var isMetric = true

        patientViewModel.patientById.observe(this) {
            patient = patientViewModel.patientById.value!![0]
            setPdfInfo(patient!!)
            setMetric(patient!!)
        }
        patientViewModel.getPatient(intent.extras!!.getInt("pid"))


        val backButton : Button = findViewById(R.id.return_to_pl)
        backButton.setOnClickListener {
            startActivity(Intent(applicationContext, PatientList::class.java))
        }

        switchUnit = findViewById(R.id.switch_units)
        switchUnit!!.setOnClickListener {
            isMetric = !isMetric

            if (isMetric) {
                setMetric(patient)
            } else {
                setImperial(patient)
            }
        }


        val generatePDF : Button = findViewById(R.id.generate_pdf)
        generatePDF.setOnClickListener {

        }
    }

    private fun setPdfInfo(patient : Patient) {
        findViewById<TextView>(R.id.pdf_name).text = patient.name
        findViewById<TextView>(R.id.pdf_gender).append(patient.gender.toString())
        if (patient.dob != null) {
            findViewById<TextView>(R.id.pdf_dob).append(patient.dob!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        }
        if (patient.distanceTraveled != null) {
            findViewById<TextView>(R.id.pdf_village).append(patient.village)
            findViewById<TextView>(R.id.pdf_distance_travled).append(
                patient.distanceTraveled!![0].toString() + " days, " +
                        patient.distanceTraveled!![1].toString() + " hours"
            )
        }

        if (patient.timeWaited != null) {
            findViewById<TextView>(R.id.pdf_time_waited).append(
                patient.timeWaited!![0].toString() + " hours, " +
                        patient.timeWaited!![1].toString() + " minutes"
            )
        }

        if (patient.bp != null) {
            findViewById<TextView>(R.id.pdf_bp).append(patient.bp!![0].toString() + " / " + patient.bp!![1].toString())
        }
        findViewById<TextView>(R.id.pdf_pulse).append(patient.p.toString())
        findViewById<TextView>(R.id.pdf_rr).append(patient.rr.toString())

        findViewById<TextView>(R.id.pdf_pres).text = (patient.pres)
        findViewById<TextView>(R.id.pdf_assess).text = (patient.assess)

        findViewById<TextView>(R.id.pdf_diag).text = (patient.diag)
        findViewById<TextView>(R.id.pdf_treat).text = (patient.treat)
    }

    @SuppressLint("SetTextI18n")
    private fun setMetric(patient : Patient?) {
        if (patient == null) {
            return
        }
        findViewById<TextView>(R.id.pdf_weight).text = "Weight: " + (patient.weight.toString() + " kg")
        findViewById<TextView>(R.id.pdf_height).text = "Height: " + (patient.height.toString() + " cm")
        findViewById<TextView>(R.id.pdf_temp).text = "Temperature: " + (patient.temp.toString() + " °C")

        findViewById<TextView>(R.id.pdf_muac).text = "Middle Upper Arm Circumference: "  + (patient.muac.toString() + " cm")
    }

    @SuppressLint("SetTextI18n")
    private fun setImperial(patient : Patient?) {
        if (patient == null) {
            return
        }
        if (patient.weight != null) {
            findViewById<TextView>(R.id.pdf_weight).text =
                "Weight: " + (GlobalHelper.getKgToLb(patient.weight!!).toString() + " lbs")
        }
        if (patient.height != null) {
            val patientHeight = GlobalHelper.getCmToInch(patient.height!!.toDouble())
            findViewById<TextView>(R.id.pdf_height).text =
                "Height: " + (patientHeight / 12).toInt()
                    .toString() + "\'" + (patientHeight % 12).roundToInt().toString() + "\""
        }
        if (patient.temp != null) {
            findViewById<TextView>(R.id.pdf_temp).text =
                "Temperature: " + (GlobalHelper.getCToF(patient.temp!!).toString()) + " °F"
        }
        if (patient.muac != null) {
            findViewById<TextView>(R.id.pdf_muac).text =
                "Middle Upper Arm Circumference: " + (GlobalHelper.getCmToInch(patient.muac!!)
                    .toString()) + " inches"
        }
    }
}