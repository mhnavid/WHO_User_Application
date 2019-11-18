package com.example.whouserapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CenterDetails {
    @SerializedName("id")
    private int id;

    @SerializedName("vcname")
    private String vcName;

    @SerializedName("vchousename")
    private String vcHouseName;

    @SerializedName("vchousenumber")
    private String vcHouseNumber;

    @SerializedName("vcroadname")
    private String vcRoadName;

    @SerializedName("vcorganized")
    private String vcOrganized;

    @SerializedName("vcnamebn")
    private String vcNameBn;

    @SerializedName("vchousenamebn")
    private String vcHouseNameBn;

    @SerializedName("vchousenumberbn")
    private String vcHouseNumberBn;

    @SerializedName("vcroadnamebn")
    private String vcRoadNameBn;

    @SerializedName("vcorganizedbn")
    private String vcOrganizedBn;

    @SerializedName("vcnametype")
    private int vcNameType;

    @SerializedName("vcward")
    private int vcWard;

    @SerializedName("vczone")
    private int vcZone;

    @SerializedName("vccitycorporation")
    private String vcCityCorporation;

    @SerializedName("vclattitude")
    private double vcLattitude;

    @SerializedName("vclonitude")
    private double vcLongitude;

    @SerializedName("vcapprovalstatus")
    private int vcApprovalStatus;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("userId")
    private int userId;

    @SerializedName("days")
    private ArrayList<Days> days;

    @SerializedName("contacts")
    private ArrayList<Contacts> contacts;

    public int getId() {
        return id;
    }

    public String getVcName() {
        return vcName;
    }

    public String getVcHouseName() {
        return vcHouseName;
    }

    public String getVcHouseNumber() {
        return vcHouseNumber;
    }

    public String getVcRoadName() {
        return vcRoadName;
    }

    public String getVcOrganized() {
        return vcOrganized;
    }

    public String getVcNameBn() {
        return vcNameBn;
    }

    public String getVcHouseNameBn() {
        return vcHouseNameBn;
    }

    public String getVcHouseNumberBn() {
        return vcHouseNumberBn;
    }

    public String getVcRoadNameBn() {
        return vcRoadNameBn;
    }

    public String getVcOrganizedBn() {
        return vcOrganizedBn;
    }

    public int getVcNameType() {
        return vcNameType;
    }

    public int getVcWard() {
        return vcWard;
    }

    public int getVcZone() {
        return vcZone;
    }

    public String getVcCityCorporation() {
        return vcCityCorporation;
    }

    public Double getVcLattitude() {
        return vcLattitude;
    }

    public Double getVcLongitude() {
        return vcLongitude;
    }

    public int getVcApprovalStatus() {
        return vcApprovalStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<Days> getDays() {
        return days;
    }

    public ArrayList<Contacts> getContacts() {
        return contacts;
    }
}