package com.example.whouserapplication.model;

public class CurrentLocation {
    private double latitude;
    private double longitude;

    public CurrentLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
