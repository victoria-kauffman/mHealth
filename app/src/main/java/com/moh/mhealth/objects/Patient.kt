package com.moh.mhealth.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "patients")
class Patient(var name: String, var gender: Char, var dob: LocalDate?) {
    @PrimaryKey(autoGenerate = true)
    var pid: Int? = null
    var village: String = ""
    var distanceTraveled: IntArray? = null // int[0] = days, int[1] = hours
    var timeWaited: IntArray? = null  // int[0] = hours, int[1] = minutes
    var weight: Double? = null
    var height: Int? = null

    @ColumnInfo(name = "temperature")
    var temp: Double? = null

    @ColumnInfo(name = "blood_pressure")
    var bp: IntArray? = null

    @ColumnInfo(name = "pulse")
    var p: Int? = null

    @ColumnInfo(name = "respiratory_rate")
    var rr: Int? = null

    @ColumnInfo(name = "middle_upper_arm_circumference")
    var muac: Double? = null

    @ColumnInfo(name = "presentation")
    var pres: String = ""

    @ColumnInfo(name = "assessment")
    var assess: String = ""

    @ColumnInfo(name = "diagnosis")
    var diag: String = ""

    @ColumnInfo(name = "treatment_plan")
    var treat: String = ""
    var location: SimpleLocation? = null
    var timeSaved: Date? = null
}