package com.moh.mhealth

import androidx.room.TypeConverter
import java.time.LocalDate
import java.util.*

object Converters {
    @JvmStatic
    @TypeConverter
    fun toLocalDate(epochDay: Long): LocalDate {
        return LocalDate.ofEpochDay(epochDay)
    }

    @JvmStatic
    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @JvmStatic
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @JvmStatic
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @JvmStatic
    @TypeConverter
    fun toArray(intStr: String): IntArray {
        val arr = intStr.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val arrInt = IntArray(arr.size)
        for (i in arr.indices) {
            arrInt[i] = arr[i].toInt()
        }
        return arrInt
    }

    @JvmStatic
    @TypeConverter
    fun fromArray(arrInt: IntArray): String {
        if (arrInt.size == 0) {
            return ""
        }
        var str = arrInt[0].toString() + ""
        for (i in 1 until arrInt.size) {
            str += "," + arrInt[i]
        }
        return str
    }

    @JvmStatic
    @TypeConverter
    fun fromLocation(loc: SimpleLocation?): String {
        return if (loc == null) {
            ""
        } else loc.latitude.toString() + ";" + loc.longitude
    }

    @JvmStatic
    @TypeConverter
    fun toLocation(locStr: String): SimpleLocation? {
        val latLong = locStr.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (locStr.length < 2) {
            null
        } else SimpleLocation(
            latLong[0].toDouble(),
            latLong[1].toDouble()
        )
    }
}