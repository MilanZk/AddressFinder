package com.example.praktikant.addressfinder.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Location {


    @SerializedName("x")
    @Expose
    private Double x;
    @SerializedName("y")
    @Expose
    private Double y;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        String sb = "Location{" + "x=" + x +
                ", y=" + y +
                '}';
        return sb;
    }
}
