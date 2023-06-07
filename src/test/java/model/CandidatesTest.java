package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
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
}
