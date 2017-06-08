package com.example.praktikant.addressfinder.helpers;


import com.example.praktikant.addressfinder.model.Bookmark;
import com.example.praktikant.addressfinder.net.model.Candidate;

import java.util.List;

public class CandidateHelper {

    public static Candidate getBestCandidate(List<Candidate> candidateList, Bookmark bookmark) {
        if (candidateList == null || bookmark == null) {
            throw new NullPointerException();
        }

        for (Candidate candidate : candidateList) {
            if (candidate.getAddress().equalsIgnoreCase(
                    bookmark.getAddress() + ", " + bookmark.getCity() + ", "
                            + bookmark.getState() + ", " + bookmark.getPostal())) {
                return candidate;
            }
        }

            return candidateList.get(0);


    }
}