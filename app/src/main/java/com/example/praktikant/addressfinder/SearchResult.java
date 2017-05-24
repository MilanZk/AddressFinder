package com.example.praktikant.addressfinder;


import com.example.praktikant.addressfinder.net.model.Candidate;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class SearchResult {

    public static Candidate getCandidate(List<Candidate> candidateList, String address){
        for (Candidate candidate: candidateList){
            if (candidate.getAddress().equals(address)){
                return candidate;
            }
        }
        return candidateList.get(0);
    }
    public static LatLng getLatLng(Candidate candidate){
        return new LatLng(candidate.getLocation().getY(),candidate.getLocation().getX());
    }
}
