package com.example.whouserapplication.model;

public class CenterListDetails {
    private String centerName;
    private String centerLocation;
    private String centerPhoneNo;
    private String vaccinationHour;

    public CenterListDetails(String centerName, String centerLocation, String centerPhoneNo, String vaxinationHour) {
        this.centerName = centerName;
        this.centerLocation = centerLocation;
        this.centerPhoneNo = centerPhoneNo;
        this.vaccinationHour = vaxinationHour;
    }

    public String getCenterName() {
        return centerName;
    }

    public String getCenterLocation() {
        return centerLocation;
    }

    public String getCenterPhoneNo() {
        return centerPhoneNo;
    }

    public String getVaxinationHour() {
        return "vaccination Hour: " + vaccinationHour;
    }
}
