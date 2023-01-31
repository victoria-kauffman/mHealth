package com.moh.mhealth;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Patient.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class PatientDatabase extends RoomDatabase {
    public abstract PatientDao patientDao();

    private static PatientDatabase INSTANCE;

    public static PatientDatabase getDbInstance(Context context) {

        if (INSTANCE == null) {
            // Initialize database
            INSTANCE = Room.databaseBuilder(context,
                    PatientDatabase.class,
                    "patients").build();
        }

        return INSTANCE;
    }

}
