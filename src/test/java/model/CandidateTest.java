package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CandidateTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void CandidateTest_1() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        Candidate candidate = new Candidate(candidate1);
        assertEquals("123", candidate.getUUID());
        assertEquals("abc", candidate.getName());
        assertEquals("456", candidate.getIntroduction());
        assertEquals("qwe", candidate.getImage());
    }

    @Test
    public void CandidateTest_2() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        Candidate candidate = new Candidate(candidate1);
        assertEquals("123", candidate.getUUID());
        assertEquals("abc", candidate.getName());
        assertEquals("456", candidate.getIntroduction());
        assertEquals("qwe", candidate.getImage());
        candidate.setUUID("789");
        candidate.setName("zxc");
        candidate.setIntroduction("asd");
        candidate.setImage("321");
        assertEquals("789", candidate.getUUID());
        assertEquals("zxc", candidate.getName());
        assertEquals("asd", candidate.getIntroduction());
        assertEquals("321", candidate.getImage());
    }

    @Test
    public void CandidateTest_3() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "");
        candidate1.put("name", "");
        candidate1.put("introduction", "");
        candidate1.put("image", "");

        Candidate candidate = new Candidate(candidate1);
        assertEquals("", candidate.getUUID());
        assertEquals("", candidate.getName());
        assertEquals("", candidate.getIntroduction());
        assertEquals("", candidate.getImage());
    }

    @Test
    public void CandidateTest_4() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", null);
        candidate1.put("name", null);
        candidate1.put("introduction", null);
        candidate1.put("image", null);

        Candidate candidate = new Candidate(candidate1);
        assertEquals(null, candidate.getUUID());
        assertEquals(null, candidate.getName());
        assertEquals(null, candidate.getIntroduction());
        assertEquals(null, candidate.getImage());
    }

    @Test
    public void CandidateTest_5() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        Candidate candidate = new Candidate(candidate1);
        assertEquals("123", candidate.getUUID());
        assertEquals("abc", candidate.getName());
        assertEquals("456", candidate.getIntroduction());
        assertEquals("qwe", candidate.getImage());
        candidate.setUUID("");
        candidate.setName("");
        candidate.setIntroduction("");
        candidate.setImage("");
        assertEquals("", candidate.getUUID());
        assertEquals("", candidate.getName());
        assertEquals("", candidate.getIntroduction());
        assertEquals("", candidate.getImage());
    }

    @Test
    public void CandidateTest_6() throws Exception {
        Map<String, String> candidate1 = new HashMap<>();
        candidate1.put("uuid", "123");
        candidate1.put("name", "abc");
        candidate1.put("introduction", "456");
        candidate1.put("image", "qwe");

        Candidate candidate = new Candidate(candidate1);
        assertEquals("123", candidate.getUUID());
        assertEquals("abc", candidate.getName());
        assertEquals("456", candidate.getIntroduction());
        assertEquals("qwe", candidate.getImage());
        candidate.setUUID(null);
        candidate.setName(null);
        candidate.setIntroduction(null);
        candidate.setImage(null);
        assertEquals(null, candidate.getUUID());
        assertEquals(null, candidate.getName());
        assertEquals(null, candidate.getIntroduction());
        assertEquals(null, candidate.getImage());
    }
}
