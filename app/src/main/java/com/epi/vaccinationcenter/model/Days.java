package com.epi.vaccinationcenter.model;

import com.google.gson.annotations.SerializedName;

public class Days {
    @SerializedName("id")
    private int id;

    @SerializedName("ninetothree")
    private String nineToThree;

    @SerializedName("fivetoseven")
    private String fiveToSeven;

    public int getId() {
        return id;
    }

    public String getNineToThree() {
        return nineToThree;
    }

    public String getFiveToSeven() {
        return fiveToSeven;
    }
}
