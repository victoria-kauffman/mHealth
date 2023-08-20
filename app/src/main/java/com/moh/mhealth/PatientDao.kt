package com.moh.mhealth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert
    suspend fun insert(patient: Patient)

    @Update
    suspend fun updatePatient(patient: Patient)

    @Query("SELECT * FROM patients")
    fun getAllPatients(): Flow<List<Patient>>

    @Query("SELECT pid, name, dob FROM patients")
    fun loadPatients(): Flow<List<PatientListTuple>>

    @Query("SELECT pid, name, dob FROM patients WHERE name LIKE :name")
    suspend fun loadPatients(name: String): List<PatientListTuple>

    @Query("SELECT * FROM patients WHERE pid = :pid")
    suspend fun getPatient(pid: Int): List<Patient>
}