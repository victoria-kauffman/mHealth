package com.moh.mhealth;

import android.location.Location;

public class SimpleLocation {
    private double latitude;
    private double longitude;

    public SimpleLocation(Location loc) {
        this.latitude = loc.getLatitude();
        this.longitude = loc.getLongitude();
    }

    public SimpleLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

}
