package com.example.whouserapplication.model;

public class LastLocation {
    private Double lastLat;
    private Double lastLong;

    public LastLocation(Double lastLat, Double lastLong) {
        this.lastLat = lastLat;
        this.lastLong = lastLong;
    }

    public Double getLastLat() {
        return lastLat;
    }

    public Double getLastLong() {
        return lastLong;
    }
}
