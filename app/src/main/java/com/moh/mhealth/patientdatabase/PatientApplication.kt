package com.moh.mhealth.patientdatabase

import android.app.Application

class PatientApplication : Application() {
    private val database by lazy { PatientDatabase.DatabaseBuilder.getDbInstance(this) }
    val repository by lazy { PatientRepository(database.patientDao()) }
}