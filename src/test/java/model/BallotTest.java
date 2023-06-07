package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BallotTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCandidateUUIDTest_1() throws Exception {
        Ballot ballot1 = new Ballot("abc");
        assertEquals("abc", ballot1.getCandidateUUID());
    }

    @Test
    public void getCandidateUUIDTest_2() throws Exception {
        Ballot ballot1 = new Ballot("abc");
        assertEquals("abc", ballot1.getCandidateUUID());
        ballot1.setCandidateUUID("qwe");
        assertEquals("qwe", ballot1.getCandidateUUID());
    }

    @Test
    public void getCandidateUUIDTest_3() throws Exception {
        Ballot ballot1 = new Ballot("");
        assertEquals("", ballot1.getCandidateUUID());
    }

    @Test
    public void getCandidateUUIDTest_4() throws Exception {
        Ballot ballot1 = new Ballot(null);
        assertEquals(null, ballot1.getCandidateUUID());
    }
}
