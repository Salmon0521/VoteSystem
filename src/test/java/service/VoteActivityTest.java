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

    @Test
    public void setVoteActivityTitleTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();

        voteActivity.setVoteActivityTitle("123");
        assertEquals("123", voteActivity.getVoteActivityTitle());
    }

    @Test
    public void setVoteActivityTitleTest_2() throws Exception {
        VoteActivity voteActivity = new VoteActivity();

        voteActivity.setVoteActivityTitle("");
        assertEquals("", voteActivity.getVoteActivityTitle());
    }

    @Test
    public void setVoteActivityTitleTest_3() throws Exception {
        VoteActivity voteActivity = new VoteActivity();

        voteActivity.setVoteActivityTitle(null);
        assertEquals(null, voteActivity.getVoteActivityTitle());
    }

    @Test
    public void getResultTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");
        Map<String, String> candidate2 = new HashMap<>();
        candidate2.put("uuid", "321");
        candidate2.put("name", "cde");
        candidate2.put("introduction", "456");
        candidate2.put("image", "qwe");

        voteActivity.addCandidate(candidate1);
        voteActivity.addCandidate(candidate2);
        voteActivity.vote("123");
        voteActivity.setStatus(true);

        voteActivity.invoicing();
        List<Map<String, String>> results = voteActivity.getResult();
        for (Map<String, String> temp : results) {
            if (temp.get("name").equals("abc")) {
                assertEquals("1", temp.get("countNum"));
            }
            else if (temp.get("name").equals("cde")) {
                assertEquals("0", temp.get("countNum"));
            }
        }
    }

    @Test
    public void getVotedBallotTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        voteActivity.addCandidate(candidate1);
        String uuid = voteActivity.vote("123");
        voteActivity.setStatus(true);

        assertEquals("abc", voteActivity.getVotedBallot(uuid));
    }

    @Test
    public void getCandidateNameTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        voteActivity.addCandidate(candidate1);

        assertEquals("abc", voteActivity.getCandidateName("123"));
    }

    @Test
    public void getCandidateNameTest_2() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        voteActivity.addCandidate(candidate1);

        assertEquals(null, voteActivity.getCandidateName("321"));
    }
    @Test
    public void countBallotTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        voteActivity.addCandidate(candidate1);

        assertEquals(0, voteActivity.countBallot());
    }

    @Test
    public void countBallotTest_2() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        voteActivity.addCandidate(candidate1);
        voteActivity.vote("123");

        assertEquals(1, voteActivity.countBallot());
    }

    @Test
    public void countBallotTest_3() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        voteActivity.addCandidate(candidate1);
        for (int i = 0; i < 10; i++) {
            voteActivity.vote("123");
        }

        assertEquals(10, voteActivity.countBallot());
    }

    @Test
    public void checkCandidatesIsNoneTest_1() throws Exception {
        VoteActivity voteActivity = new VoteActivity();
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        assertEquals(true, voteActivity.checkCandidatesIsNone());
        voteActivity.addCandidate(candidate1);
        assertEquals(false, voteActivity.checkCandidatesIsNone());
    }

}
