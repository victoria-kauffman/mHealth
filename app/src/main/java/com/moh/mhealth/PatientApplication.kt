package com.moh.mhealth

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PatientApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { PatientDatabase.DatabaseBuilder.getDbInstance(this, applicationScope) }
    val repository by lazy { PatientRepository(database.patientDao()) }
}