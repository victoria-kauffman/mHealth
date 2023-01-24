package com.moh.mhealth;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PatientDao {

    @Insert
    void insertAll(Patient... patients);

    @Query("SELECT * FROM patients")
    List<Patient> getAllPatients();

}
