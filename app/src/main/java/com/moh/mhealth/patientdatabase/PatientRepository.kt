package com.moh.mhealth.patientdatabase

import androidx.annotation.WorkerThread
import com.moh.mhealth.objects.PatientListTuple
import com.moh.mhealth.objects.Patient
import kotlinx.coroutines.flow.Flow

class PatientRepository(private val patientDao: PatientDao) {

    val allPatients: Flow<List<Patient>> = patientDao.getAllPatients()
    val allPatientIntro: Flow<List<PatientListTuple>> = patientDao.loadPatients()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(patient: Patient) {
        patientDao.insert(patient)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePatient(patient: Patient) {
        patientDao.updatePatient(patient)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun loadPatients(name: String):List<PatientListTuple> {
        return patientDao.loadPatients(name)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPatient(pid: Int):List<Patient> {
        return patientDao.getPatient(pid)
    }
}
