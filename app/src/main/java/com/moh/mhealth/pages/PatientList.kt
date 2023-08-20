package com.moh.mhealth.pages

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.moh.mhealth.*
import org.w3c.dom.Text
import java.text.DateFormat
import java.time.format.DateTimeFormatter

class PatientList : AppCompatActivity() {

    private val patientViewModel: PatientViewModel by viewModels {
        PatientViewModelFactory(( application as PatientApplication ).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_list)

        val newPatient = findViewById<View>(R.id.patient_list_new_patient_button) as Button
        newPatient.setOnClickListener {

            startActivity(
                Intent(
                    applicationContext,
                    Global_Helper.nextDiag()
                )
            )
        }

        loadPatientList()
    }

    private fun loadPatientList() {
        // PatientDatabase db = PatientDatabase.getDbInstance(getApplicationContext());
        val patient_list : LinearLayout = findViewById(R.id.patient_list)

        patientViewModel.allPatientsIntro.observe(this, Observer {
                val patients : List<PatientListTuple>? = patientViewModel.allPatientsIntro.value

                if (patients != null) { // We know we're good
                    for (p in patients) {
                        val pLayout = LinearLayout(this)
                        pLayout.setOnClickListener {
                            // Want to open up an editable pdf page for this patient
                            val i: Intent = Intent(applicationContext, PatientPdf::class.java)
                            i.putExtra("pid", p.pid)
                            startActivity(i)
                        }

                        val pName = TextView(this)
                        val pDob = TextView(this)

                        pName.text = p.name
                        pName.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                    0.3f)
                        pName.textSize = 25F
                        pName.setTextColor( getResources().getColor(R.color.black))

                        pDob.text = p.dob!!.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))

                        pDob.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            0.7f)
                        pDob.textSize = 25F
                        pDob.setTextColor(getResources().getColor(R.color.black))

                        pLayout.addView(pName)
                        pLayout.addView(pDob)
                        patient_list.addView(pLayout)
                    }
                }
        })
    }
}