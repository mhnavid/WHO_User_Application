package com.example.whouserapplication.model;

import com.google.gson.annotations.SerializedName;

public class Contacts {
    @SerializedName("id")
    private int contactID;

    @SerializedName("cname")
    private String contactName;

    @SerializedName("cmobilenumber")
    private String contactMobileNumber;

    @SerializedName("vname")
    private String vaccinatorName;

    @SerializedName("vmobilenumber")
    private String vaccinatorMobileNumber;

    @SerializedName("cmobilenumberbn")
    private String contactMobileNumberBn;

    @SerializedName("vmobilenumberbn")
    private String vaccinatorMobileNumberBn;

    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactMobileNumber() {
        return contactMobileNumber;
    }

    public String getVaccinatorName() {
        return vaccinatorName;
    }

    public String getVaccinatorMobileNumber() {
        return vaccinatorMobileNumber;
    }

    public String getContactMobileNumberBn() {
        return contactMobileNumberBn;
    }

    public String getVaccinatorMobileNumberBn() {
        return vaccinatorMobileNumberBn;
    }
}
