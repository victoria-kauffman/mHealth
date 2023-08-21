package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moh.mhealth.*
import com.moh.mhealth.objects.PatientListTuple
import com.moh.mhealth.patientdatabase.PatientViewModel
import java.time.format.DateTimeFormatter

class PatientList : AppCompatActivity() {

    private val patientViewModel: PatientViewModel by viewModels { PatientViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_list)

        val newPatient = findViewById<View>(R.id.patient_list_new_patient_button) as Button
        newPatient.setOnClickListener {

            startActivity(
                Intent(
                    applicationContext,
                    GlobalHelper.nextDiag()
                )
            )
        }

        loadPatientList()
    }

    private fun loadPatientList() {
        // PatientDatabase db = PatientDatabase.getDbInstance(getApplicationContext());
        val patientList : LinearLayout = findViewById(R.id.patient_list)

        patientViewModel.allPatientsIntro.observe(this) {
            val patients: List<PatientListTuple>? = patientViewModel.allPatientsIntro.value

            if (patients != null) {
                for (p in patients) {
                    val pLayout = LinearLayout(this)
                    pLayout.setOnClickListener {
                        // Want to open up an editable pdf page for this patient
                        val i = Intent(applicationContext, PatientPdf::class.java)
                        i.putExtra("pid", p.pid)
                        startActivity(i)
                    }

                    val pName = TextView(this)
                    val pDob = TextView(this)

                    pName.text = p.name
                    pName.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        0.3f
                    )
                    pName.textSize = 25F
                    pName.setTextColor(0x000000)

                    pDob.text = p.dob!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                    pDob.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        0.7f
                    )
                    pDob.textSize = 25F
                    pDob.setTextColor(0x000000)

                    pLayout.addView(pName)
                    pLayout.addView(pDob)
                    patientList.addView(pLayout)
                }
            }

        }
    }
}