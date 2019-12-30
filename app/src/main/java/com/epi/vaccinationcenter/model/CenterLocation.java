package com.epi.vaccinationcenter.model;

public class CenterLocation {
    private String centerName;
    private double latitude;
    private double longitude;

    public CenterLocation(String centerName, double latitude, double longitude) {
        this.centerName = centerName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCenterName() {
        return centerName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}