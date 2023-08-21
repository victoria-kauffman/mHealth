package com.moh.mhealth

import android.util.Log
import android.widget.EditText
import com.moh.mhealth.pages.*
import com.moh.mhealth.objects.Patient
import java.time.LocalDate
import kotlin.math.roundToInt

object GlobalHelper {
    const val USERNAME = "" // Gonna need to fix
    const val PASSWORD = "" // Gonna need to fix
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

    fun getLbToKg(lb: Double): Double {
        return (lb * 0.45359237 * 100.0).roundToInt() / 100.0
    }

    fun getInchToCm(inch: Double): Double {
        return (inch * 2.54 * 100.0).roundToInt() / 100.0
    }

    fun getFToC(f: Double): Double {
        return ((f - 32) * (5 / 9.0) * 100.0).roundToInt() / 100.0
    }

    fun getKgToLb(kg: Double): Double {
        return (kg / 0.45359237 * 100.0).roundToInt() / 100.0
    }

    fun getCmToInch(cm: Double): Double {
        return (cm / 2.54 * 100.0).roundToInt() / 100.0
    }

    fun getCToF(c: Double): Double {
        return ((9 / 5.0 * c + 32) * 100.0).roundToInt() / 100.0
    }

    fun getDoubleFromEditText(editText: EditText?): Double {
        val doubleStr = editText!!.text.toString()
        if (doubleStr.isNotEmpty()) {
            try {
                return doubleStr.toDouble()
            } catch (e1: Exception) {
                // I don't really care about the error I don't think...
            }
        }
        return -1.0
    }

    fun getIntFromEditText(editText: EditText?): Int {
        val intStr = editText!!.text.toString()
        if (intStr.isNotEmpty()) {
            try {
                return intStr.toInt()
            } catch (e1: Exception) {
                // I don't really care about the error I don't think...
            }
        }
        return -1
    }

    fun createNewPatient(name: String, gender: Char, dob: LocalDate?) {
        currentPatient = Patient(name, gender, dob)
    }

    fun addPatientSurvey(village: String, distanceTraveled: IntArray?, timeWaited: IntArray?) {
        currentPatient?.village = village
        currentPatient?.distanceTraveled = distanceTraveled
        currentPatient?.timeWaited = timeWaited
    }

    fun addDiag1Metric(kg: Double, cm: Int, tempC: Double) {
        currentPatient?.weight = kg
        currentPatient?.height = cm
        currentPatient?.temp = tempC
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
        currentPatient?.bp = bp
        currentPatient?.p = p
        currentPatient?.rr = rr
        currentPatient?.muac = muacCM
    }

    fun addDiag3(pres: String, assess: String) {
        currentPatient?.pres = pres
        currentPatient?.assess = assess
    }

    fun addDiag4(diag: String, treat: String) {
        currentPatient?.diag = diag
        currentPatient?.treat = treat
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