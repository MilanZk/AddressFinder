package com.example.praktikant.addressfinder.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Candidate {
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("attributes")
    @Expose
    private Attributes attributes;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

}