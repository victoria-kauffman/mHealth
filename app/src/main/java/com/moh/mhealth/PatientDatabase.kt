package com.moh.mhealth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Patient::class], version = 1)
@TypeConverters(Converters::class)
abstract class PatientDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao

    object DatabaseBuilder {
        private var INSTANCE: PatientDatabase? = null
        fun getDbInstance(context: Context, scope: CoroutineScope): PatientDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    PatientDatabase::class.java,
                    "patients").build()
                INSTANCE!!
            }
        }
    }
}