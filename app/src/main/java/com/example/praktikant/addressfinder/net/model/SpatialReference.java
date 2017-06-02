package com.example.praktikant.addressfinder.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SpatialReference {

    @SerializedName("wkid")
    @Expose
    private Integer wkid;

    public Integer getWkid() {
        return wkid;
    }

    public void setWkid(Integer wkid) {
        this.wkid = wkid;
    }

}