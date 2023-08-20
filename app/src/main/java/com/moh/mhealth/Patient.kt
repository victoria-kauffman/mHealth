package com.moh.mhealth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "patients")
class Patient(var name: String?, var gender: Char, var dob: LocalDate?) {
    @PrimaryKey(autoGenerate = true)
    var pid = 0
    var village: String? = ""
    var distance_traveled // int[0] = days, int[1] = hours
            : IntArray?
    var time_waited // int[0] = hours, int[1] = minutes
            : IntArray?
    var weight: Double
    var height: Int

    @ColumnInfo(name = "temperature")
    var temp: Double

    @ColumnInfo(name = "blood_pressure")
    var bp: IntArray?

    @ColumnInfo(name = "pulse")
    var p: Int

    @ColumnInfo(name = "respiratory_rate")
    var rr: Int

    @ColumnInfo(name = "middle_upper_arm_circumference")
    var muac: Double

    @ColumnInfo(name = "presentation")
    var pres: String?

    @ColumnInfo(name = "assessment")
    var assess: String?

    @ColumnInfo(name = "diagnosis")
    var diag: String?

    @ColumnInfo(name = "treatment_plan")
    var treat: String?
    var location: SimpleLocation? = null
    var time_saved: Date? = null

    init {
        distance_traveled = intArrayOf(0, 0)
        time_waited = intArrayOf(0, 0)
        weight = -1.0
        height = 175
        temp = -1.0
        bp = intArrayOf(-1, -1)
        rr = -1
        p = -1
        muac = -1.0
        pres = ""
        assess = ""
        diag = ""
        treat = ""
    }
}