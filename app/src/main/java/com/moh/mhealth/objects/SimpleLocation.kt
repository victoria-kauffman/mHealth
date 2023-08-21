package com.moh.mhealth.objects

import android.location.Location

class SimpleLocation {
    var latitude: Double
        private set
    var longitude: Double
        private set

    constructor(loc: Location) {
        latitude = loc.latitude
        longitude = loc.longitude
    }

    constructor(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
    }
}