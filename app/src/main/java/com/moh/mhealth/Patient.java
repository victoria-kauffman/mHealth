package com.moh.mhealth;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "patients")
public class Patient {

    @PrimaryKey(autoGenerate = true)
    private int pid;

    private String name;
    private char gender;
    private LocalDate dob;

    private String village;
    private int[] distance_traveled; // int[0] = days, int[1] = hours
    private int[] time_waited; // int[0] = hours, int[1] = minutes

    private double weight;
    private int height;
    @ColumnInfo(name = "temperature")
    private double temp;

    @ColumnInfo(name = "blood_pressure")
    private int[] bp;
    @ColumnInfo(name = "pulse")
    private int p;
    @ColumnInfo(name = "respiratory_rate")
    private int rr;
    @ColumnInfo(name = "middle_upper_arm_circumference")
    private double muac;

    @ColumnInfo(name = "presentation")
    private String pres;
    @ColumnInfo(name = "assessment")
    private String assess;

    @ColumnInfo(name = "diagnosis")
    private String diag;
    @ColumnInfo(name = "treatment_plan")
    private String treat;

    private SimpleLocation location;

    private Date time_saved;

    public Patient(String name, char gender, LocalDate dob) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;

        this.village = "";
        this.distance_traveled = new int[] {0,0};
        this.time_waited = new int[] {0,0};

        this.weight = -1;
        this.height = 175;
        this.temp = -1;

        this.bp = new int[] {-1, -1};
        this.rr = -1;
        this.p = -1;
        this.muac = -1;


        this.pres = "";
        this.assess = "";

        this.diag = "";
        this.treat = "";
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public int[] getDistance_traveled() {
        return distance_traveled;
    }

    public void setDistance_traveled(int[] distance_traveled) {
        this.distance_traveled = distance_traveled;
    }

    public int[] getTime_waited() {
        return time_waited;
    }

    public void setTime_waited(int[] time_waited) {
        this.time_waited = time_waited;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int[] getBp() {
        return bp;
    }

    public void setBp(int[] bp) {
        this.bp = bp;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getRr() {
        return rr;
    }

    public void setRr(int rr) {
        this.rr = rr;
    }

    public double getMuac() {
        return muac;
    }

    public void setMuac(double muac) {
        this.muac = muac;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public String getDiag() {
        return diag;
    }

    public void setDiag(String diag) {
        this.diag = diag;
    }

    public String getTreat() {
        return treat;
    }

    public void setTreat(String treat) {
        this.treat = treat;
    }

    public SimpleLocation getLocation() {
        return location;
    }

    public void setLocation(SimpleLocation location) {
        this.location = location;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getTime_saved() {
        return time_saved;
    }

    public void setTime_saved(Date time_saved) {
        this.time_saved = time_saved;
    }
}
