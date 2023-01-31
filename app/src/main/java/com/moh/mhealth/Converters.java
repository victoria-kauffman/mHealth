package com.moh.mhealth;

import android.content.Intent;
import android.location.Location;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Converters {

    @TypeConverter
    public static LocalDate toLocalDate(long epochDay) {
        return LocalDate.ofEpochDay(epochDay);
    }

    @TypeConverter
    public static long fromLocalDate(LocalDate date) {
        return date.toEpochDay();
    }

    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static int[] toArray(String intStr) {
        String[] arr = intStr.split(",");

        int[] arrInt = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrInt[i] = Integer.parseInt(arr[i]);
        }

        return arrInt;
    }

    @TypeConverter
    public static String fromArray(int[] arrInt) {
        if (arrInt.length == 0) {
            return "";
        }
        String str = arrInt[0] + "";

        for (int i = 1; i < arrInt.length; i++) {
            str += ("," + arrInt[i]);
        }

        return str;
    }


    @TypeConverter
    public static String fromLocation(SimpleLocation loc) {
        return loc.getLatitude() + ";" + loc.getLongitude();
    }

    @TypeConverter
    public static SimpleLocation toLocation(String locStr) {
        String[] latLong = locStr.split(";");
        return new SimpleLocation(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]));
    }
}
