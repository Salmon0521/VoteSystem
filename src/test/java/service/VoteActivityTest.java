package service;

import model.Ballot;
import model.Candidate;
import org.junit.Assert;
import service.VoteActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class VoteActivityTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setStatusTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();

        assertEquals(false, voteActivity.getStatus());
        voteActivity.setStatus(true);
        assertEquals(true, voteActivity.getStatus());
    }

    @Test
    public void addCandidateTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        voteActivity.addCandidate(candidate1);
        List<Candidate> candidates = voteActivity.getCandidates();
        assertEquals(1, candidates.size());

    }

    @Test
    public void deleteCandidateTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        voteActivity.addCandidate(candidate1);
        List<Candidate> candidates = voteActivity.getCandidates();
        assertEquals(1, candidates.size());
        voteActivity.deleteCandidate("123");
        assertEquals(0, candidates.size());
    }

    @Test
    public void VoteTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        String ballotUUID;

        ballotUUID = voteActivity.vote("123");
        assertEquals(1, voteActivity.countBallot());
        String candidateName = voteActivity.getVotedBallot(ballotUUID);
        assertEquals("123", candidateName);
    }

    @Test
    public void voteProcessTest() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        UserService userService = new UserService();

        String ballotUUID = voteActivity.vote("abc");
        userService.updateUserVoted("100", ballotUUID);


        assertEquals(true, userService.getUserVoted("100"));
        assertEquals(ballotUUID, userService.getUserBallotUUID("100"));
        assertEquals(1, voteActivity.countBallot());
        assertEquals("abc", voteActivity.getVotedBallot(ballotUUID));
    }

    @Test
    public void resetTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        voteActivity.addCandidate(candidate1);
        voteActivity.vote("123");
        voteActivity.setStatus(true);
        assertEquals(1, voteActivity.countBallot());
        List<Candidate> candidates = voteActivity.getCandidates();
        assertEquals(1, candidates.size());
        assertEquals(true, voteActivity.getStatus());
        voteActivity.reset();
        assertEquals(0, voteActivity.countBallot());
        assertEquals(0, candidates.size());
        assertEquals(false, voteActivity.getStatus());
    }
}