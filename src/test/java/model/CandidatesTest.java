package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class CandidatesTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addCandidateTest_1() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");
        Candidates candidates = new Candidates();
        candidates.addCandidate(candidate1);

        List<Candidate> candidateList = candidates.getCandidates();
        assertEquals("123", candidateList.get(0).getUUID());
        assertEquals("abc", candidateList.get(0).getName());
        assertEquals("456", candidateList.get(0).getIntroduction());
        assertEquals("qwe", candidateList.get(0).getImage());
    }


    @Test
    public void addCandidateTest_2() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        Candidates candidates = new Candidates();
        candidates.addCandidate(candidate1);

        List<Candidate> candidateList = candidates.getCandidates();
        assertEquals(1, candidateList.size());
    }

    @Test
    public void deleteCandidateTest_1() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");
        Candidates candidates = new Candidates();
        candidates.addCandidate(candidate1);

        List<Candidate> candidateList = candidates.getCandidates();
        assertEquals(1, candidateList.size());
        candidates.deleteCandidate("123");
        assertEquals(0, candidateList.size());

    }

    @Test
    public void deleteCandidateTest_2() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");
        Candidates candidates = new Candidates();
        candidates.addCandidate(candidate1);

        List<Candidate> candidateList = candidates.getCandidates();
        assertEquals(1, candidateList.size());
        candidates.deleteCandidate("321");
        assertEquals(1, candidateList.size());

    }

    @Test
    public void deleteCandidateTest_3() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");
        Map<String, String> candidate2 = new HashMap<>();
        candidate2.put("uuid", "111");
        candidate2.put("name", "abc");
        candidate2.put("introduction", "456");
        candidate2.put("image", "qwe");
        Candidates candidates = new Candidates();
        candidates.addCandidate(candidate1);
        candidates.addCandidate(candidate2);

        List<Candidate> candidateList = candidates.getCandidates();
        assertEquals(2, candidateList.size());
        candidates.deleteCandidate("321");
        assertEquals(2, candidateList.size());
        candidates.deleteCandidate("123");
        assertEquals(1, candidateList.size());
    }

    @Test
    public void removeAllCandidateTest_1() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");
        Candidates candidates = new Candidates();
        candidates.addCandidate(candidate1);

        List<Candidate> candidateList = candidates.getCandidates();
        assertEquals(1, candidateList.size());
        candidates.removeAllCandidate();
        assertEquals(0, candidateList.size());
    }

    @Test
    public void removeAllCandidateTest_2() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");
        Candidates candidates = new Candidates();


        List<Candidate> candidateList = candidates.getCandidates();
        assertEquals(0, candidateList.size());
        candidates.removeAllCandidate();
        assertEquals(0, candidateList.size());
        candidates.addCandidate(candidate1);
        assertEquals(1, candidateList.size());
    }

    @Test
    public void removeAllCandidateTest_3() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");
        Map<String, String> candidate2 = new HashMap<>();
        candidate2.put("uuid", "111");
        candidate2.put("name", "abc");
        candidate2.put("introduction", "456");
        candidate2.put("image", "qwe");
        Candidates candidates = new Candidates();
        candidates.addCandidate(candidate1);
        candidates.addCandidate(candidate2);


        List<Candidate> candidateList = candidates.getCandidates();
        assertEquals(2, candidateList.size());
        candidates.removeAllCandidate();
        assertEquals(0, candidateList.size());
        candidates.addCandidate(candidate1);
        assertEquals(1, candidateList.size());
    }
}
