package com.example.whouserapplication.model;

import java.io.Serializable;

public class Center implements Serializable {
    private int centerID;
    private String centerName;
    private String centerNameBn;
    private String centerLocation;
    private String centerLocationBn;
    private String vaccinatorContactNo;
    private String vaccinatorContactNoBn;
    private String contactPersonContactNo;
    private String contactPersonContactNoBn;
    private String daysNineToThree;
    private String daysFiveToSeven;
    private String organized;
    private String organizedBn;
    private double centerLatitude;
    private double centerLongitude;

    public Center(int centerID, String centerName, String centerNameBn, String centerLocation, String centerLocationBn, String vaccinatorContactNo, String vaccinatorContactNoBn, String contactPersonContactNo, String contactPersonContactNoBn, String daysNineToThree, String daysFiveToSeven, String organized, String organizedBn, double centerLatitude, double centerLongitude) {
        this.centerID = centerID;
        this.centerName = centerName;
        this.centerNameBn = centerNameBn;
        this.centerLocation = centerLocation;
        this.centerLocationBn = centerLocationBn;
        this.vaccinatorContactNo = vaccinatorContactNo;
        this.vaccinatorContactNoBn = vaccinatorContactNoBn;
        this.contactPersonContactNo = contactPersonContactNo;
        this.contactPersonContactNoBn = contactPersonContactNoBn;
        this.daysNineToThree = daysNineToThree;
        this.daysFiveToSeven = daysFiveToSeven;
        this.organized = organized;
        this.organizedBn = organizedBn;
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
    }

    public int getCenterID() {
        return centerID;
    }

    public String getCenterName() {
        return centerName;
    }

    public String getCenterNameBn() {
        return centerNameBn;
    }

    public String getCenterLocation() {
        return centerLocation;
    }

    public String getCenterLocationBn() {
        return centerLocationBn;
    }

    public String getVaccinatorContactNo() {
        return vaccinatorContactNo;
    }

    public String getVaccinatorContactNoBn() {
        return vaccinatorContactNoBn;
    }

    public String getContactPersonContactNo() {
        return contactPersonContactNo;
    }

    public String getContactPersonContactNoBn() {
        return contactPersonContactNoBn;
    }

    public String getDaysNineToThree() {
        return daysNineToThree;
    }

    public String getDaysFiveToSeven() {
        return daysFiveToSeven;
    }

    public String getOrganized() {
        return organized;
    }

    public String getOrganizedBn() {
        return organizedBn;
    }

    public double getCenterLatitude() {
        return centerLatitude;
    }

    public double getCenterLongitude() {
        return centerLongitude;
    }
}