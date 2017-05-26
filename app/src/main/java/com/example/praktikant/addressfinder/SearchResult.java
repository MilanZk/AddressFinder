package com.example.praktikant.addressfinder;


import com.example.praktikant.addressfinder.net.model.Candidate;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class SearchResult {

    public static Candidate getBestCandidate(List<Candidate> candidateList, String address){
        for (Candidate candidate: candidateList){
            if (candidate.getAddress().equalsIgnoreCase(address)){
                return candidate;
            }
        }
        return candidateList.get(0);
    }

}
