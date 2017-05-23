package com.example.praktikant.addressfinder.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Praktikant on 23.5.2017..
 */


public class ResponseData {

    @SerializedName("spatialReference")
    @Expose
    private SpatialReference spatialReference;
    @SerializedName("candidates")
    @Expose
    private List<Candidate> candidates = null;

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}