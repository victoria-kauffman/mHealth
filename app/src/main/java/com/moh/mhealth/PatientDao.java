package com.moh.mhealth;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PatientDao {

    @Insert
    public void insert(Patient patient);

    @Update
    public void updatePatient(Patient patient);

    @Query("SELECT * FROM patients")
    public List<Patient> getAllPatients();

    @Query("SELECT pid, name, dob FROM patients")
    public List<PatientListTuple> loadPatients();

    @Query("SELECT pid, name, dob FROM patients WHERE name LIKE :name")
    public List<PatientListTuple> loadPatients(String name);

    @Query("SELECT * FROM patients WHERE pid = :pid")
    public Patient getPatient(int pid);

}
