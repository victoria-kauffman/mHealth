package com.moh.mhealth.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moh.mhealth.*
import com.moh.mhealth.objects.Patient
import com.moh.mhealth.patientdatabase.PatientViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


class PatientPdf : AppCompatActivity() {
    val REQUEST_CODE = 1232
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
            generatePDF()
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

    private fun generatePDF() {

    }


    private fun createPDF() {
        val document = PdfDocument()
        val pageInfo = PageInfo.Builder(1080, 1920, 1).create()
        val page = document.startPage(pageInfo)

        val canvas = page.canvas

        val paint = Paint()
        paint.color = Color.RED
        paint.textSize = 42f

        val text = "Hello, World"
        val x = 500f
        val y = 900f

        canvas.drawText(text, x, y, paint)
        document.finishPage(page)

        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "example.pdf"
        val file = File(downloadsDir, fileName)
        try {
            val fos = FileOutputStream(file)
            document.writeTo(fos)
            document.close()
            fos.close()
            Toast.makeText(this, "Written Successfully!!!", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            Log.d("mylog", "Error while writing " + e.toString())
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}