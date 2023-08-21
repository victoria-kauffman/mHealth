package com.moh.mhealth.patientdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moh.mhealth.Converters
import com.moh.mhealth.objects.Patient

@Database(entities = [Patient::class], version = 1)
@TypeConverters(Converters::class)
abstract class PatientDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao

    object DatabaseBuilder {
        private var INSTANCE: PatientDatabase? = null
        fun getDbInstance(context: Context): PatientDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    PatientDatabase::class.java,
                    "patients").build()
                INSTANCE!!
            }
        }
    }
}