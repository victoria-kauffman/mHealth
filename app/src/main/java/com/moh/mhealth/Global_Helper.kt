package com.moh.mhealth

import android.util.Log
import android.widget.EditText
import com.moh.mhealth.pages.*
import java.time.LocalDate

object Global_Helper {
    const val USERNAME = "user" // Gonna need to fix
    const val PASSWORD = "1234" // Gonna need to fix
    private val diagOrder = arrayOf<Class<*>>(
        PatientList::class.java,
        NewPatient::class.java,
        PatientSurvey::class.java,
        Diagnostics1::class.java,
        Diagnostics2::class.java,
        Diagnostics3::class.java,
        Diagnostics4::class.java
    )
    var currentPatient: Patient? = null
        private set
    private var currentDiag = 0
    var isMetric = false
        private set

    fun getLbToKg(lb: Double): Double {
        return Math.round(lb * 0.45359237 * 100.0) / 100.0
    }

    fun getInchToCm(`in`: Double): Double {
        return Math.round(`in` * 2.54 * 100.0) / 100.0
    }

    fun getFToC(f: Double): Double {
        return Math.round((f - 32) * (5 / 9.0) * 100.0) / 100.0
    }

    fun getKgToLb(kg: Double): Double {
        return Math.round(kg / 0.45359237 * 100.0) / 100.0
    }

    fun getCmToInch(cm: Double): Double {
        return Math.round(cm / 2.54 * 100.0) / 100.0
    }

    fun getCToF(c: Double): Double {
        return Math.round((9 / 5.0 * c + 32) * 100.0) / 100.0
    }

    fun getDoubleFromEditText(editText: EditText?): Double {
        val doubleStr = editText!!.text.toString()
        if (!doubleStr.isEmpty()) {
            try {
                return doubleStr.toDouble()
            } catch (e1: Exception) {
                // I don't really care about the error I don't think...
            }
        }
        return (-1).toDouble()
    }

    fun getIntFromEditText(editText: EditText?): Int {
        val intStr = editText!!.text.toString()
        if (!intStr.isEmpty()) {
            try {
                return intStr.toInt()
            } catch (e1: Exception) {
                // I don't really care about the error I don't think...
            }
        }
        return -1
    }

    fun createNewPatient(name: String?, gender: Char, dob: LocalDate?) {
        currentPatient = Patient(name, gender, dob)
    }

    fun addPatientSurvey(village: String?, distanceTraveled: IntArray?, timeWaited: IntArray?) {
        currentPatient.setVillage(village)
        currentPatient.setDistance_traveled(distanceTraveled)
        currentPatient.setTime_waited(timeWaited)
    }

    fun setUnits(unitsInt: Int) {
        isMetric = if (unitsInt == 0) true else false
    }

    fun addDiag1Metric(kg: Double, cm: Int, tempC: Double) {
        currentPatient.setWeight(kg)
        currentPatient.setHeight(cm)
        currentPatient.setTemp(tempC)
    }

    // Wrapper to both save diag1 variables and convert imperial to metric
    fun addDiag1Imperial(lbs: Double, ft: Int, inch: Int, tempF: Double) {
        addDiag1Metric(
            getLbToKg(lbs),
            getInchToCm((ft * 12 + inch).toDouble()).toInt(),
            getFToC(tempF)
        )
    }

    fun addDiag2(bp: IntArray?, p: Int, rr: Int, muacCM: Double) {
        currentPatient.setBp(bp)
        currentPatient.setP(p)
        currentPatient.setRr(rr)
        currentPatient.setMuac(muacCM)
    }

    fun addDiag3(pres: String?, assess: String?) {
        currentPatient.setPres(pres)
        currentPatient.setAssess(assess)
    }

    fun addDiag4(diag: String?, treat: String?) {
        currentPatient.setDiag(diag)
        currentPatient.setTreat(treat)
    }

    fun nextDiag(): Class<*> {
        Log.i("NextDiag", currentDiag.toString() + ":: " + diagOrder[currentDiag].name)
        assert(currentDiag < diagOrder.size - 1)
        currentDiag++
        return diagOrder[currentDiag]
    }

    fun prevDiag(): Class<*> {
        assert(currentDiag > 0)
        if (currentDiag == 1) { // Gonna be returning to the patient list page, so cancel current patient
            currentPatient = null
        }
        currentDiag--
        return diagOrder[currentDiag]
    }

    fun endPatient(): Class<*> {
        currentDiag = 0
        currentPatient = null
        isMetric = true
        return diagOrder[0]
    }
}