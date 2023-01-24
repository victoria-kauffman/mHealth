package com.moh.mhealth;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "patients")
public class Patient {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    private String name;
    private char gender;
    private Date dob;

    private String village;
    private int distance_traveled;
    private String distance_unit;
    private int[] time_waited; // hour, minute

    

    public Patient(String name, char gender, Date dob) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public int getDistance_traveled() {
        return distance_traveled;
    }

    public void setDistance_traveled(int distance_traveled) {
        this.distance_traveled = distance_traveled;
    }

    public String getDistance_unit() {
        return distance_unit;
    }

    public void setDistance_unit(String distance_unit) {
        this.distance_unit = distance_unit;
    }

    public int[] getTime_waited() {
        return time_waited;
    }

    public void setTime_waited(int[] time_waited) {
        this.time_waited = time_waited;
    }
}
