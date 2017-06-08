package com.example.praktikant.addressfinder;


import com.example.praktikant.addressfinder.helpers.CandidateHelper;
import com.example.praktikant.addressfinder.model.Bookmark;
import com.example.praktikant.addressfinder.net.model.Candidate;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CandidateHelperTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getBestCandidateAddressExists() {

        List<Candidate> candidates = new ArrayList<>();
        Candidate candidate = new Candidate("148 E 45th St, New York, NY, 10017");
        Candidate candidate1 = new Candidate("149 E 45th St, New York, NY, 10017");
        Candidate candidate2 = new Candidate("150 E 45th St, New York, NY, 10017");
        Candidate candidate3 = new Candidate("151 E 45th St, New York, NY, 10017");

        candidates.add(candidate);
        candidates.add(candidate1);
        candidates.add(candidate2);
        candidates.add(candidate3);

        Bookmark bookmark = new Bookmark();
        bookmark.setAddress("149 E 45th St");
        bookmark.setCity("New York");
        bookmark.setState("NY");
        bookmark.setPostal("10017");

        Candidate expectedCandidate = CandidateHelper.getBestCandidate(candidates, bookmark);
        Assert.assertEquals(candidate1, expectedCandidate);


    }
    @Test
    public void getBestCandidateAddressExistsDoesNotExist() {
        List<Candidate> candidates = new ArrayList<>();
        Candidate candidate = new Candidate("148 E 45th St, New York, NY, 10017");
        Candidate candidate1 = new Candidate("149 E 45th St, New York, NY, 10017");
        Candidate candidate2 = new Candidate("150 E 45th St, New York, NY, 10017");
        Candidate candidate3 = new Candidate("151 E 45th St, New York, NY, 10017");

        candidates.add(candidate);
        candidates.add(candidate1);
        candidates.add(candidate2);
        candidates.add(candidate3);

        Bookmark bookmark = new Bookmark();
        bookmark.setAddress("147 E 45th St");
        bookmark.setCity("New York");
        bookmark.setState("NY");
        bookmark.setPostal("10017");

        Candidate expectedCandidate = CandidateHelper.getBestCandidate(candidates, bookmark);
        Assert.assertEquals(candidate, expectedCandidate);

    }
    @Test
    public void getBestCandidateIllegalArguments() throws Exception {
        List<Candidate> candidates = null;
        Bookmark bookmark = null;

        exception.expect(RuntimeException.class);
        Candidate expectedCandidate = CandidateHelper.getBestCandidate(candidates, bookmark);


    }
    @Test
    public void getBestCandidateEmptyArguments() throws Exception {
        List<Candidate> candidates = new ArrayList<>();
        Bookmark bookmark = new Bookmark();

        exception.expect(IndexOutOfBoundsException.class);
        Candidate expectedCandidate = CandidateHelper.getBestCandidate(candidates, bookmark);


    }




}
