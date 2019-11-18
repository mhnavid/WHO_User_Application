package com.example.whouserapplication.model;

import com.google.gson.annotations.SerializedName;

public class ImageFind {
    @SerializedName("id")
    private int imageID;

    @SerializedName("images")
    private String imageLocation;

    public ImageFind(int imageID, String imageLocation) {
        this.imageID = imageID;
        this.imageLocation = imageLocation;
    }

    public int getImageID() {
        return imageID;
    }

    public String getImageLocation() {
        return imageLocation;
    }
}
