package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BallotsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
/*
    @Test
    public void countBallotsTest_1() throws Exception {
        Ballots ballots = new Ballots();
        ballots.addBallot("101");
        assertEquals(1, ballots.countBallots("101"));
    }

    @Test
    public void countBallotsTest_2() throws Exception {
        Ballots ballots = new Ballots();
        assertEquals(0, ballots.countBallots("101"));
    }

    @Test
    public void countBallotsTest_3() throws Exception {
        Ballots ballots = new Ballots();
        assertEquals(0, ballots.countBallots(null));
    }

    @Test (expected = NullPointerException.class)
    public void countBallotsTest_4() throws Exception {
        Ballots ballots = new Ballots();
        ballots.addBallot(null);
        assertEquals(1, ballots.countBallots(null));
    }
    @Test
    public void countBallotsTest_5() throws Exception {
        Ballots ballots = new Ballots();

        ballots.addBallot("101");
        ballots.addBallot("102");
        ballots.addBallot("103");
        ballots.addBallot("101");
        assertEquals(2, ballots.countBallots("101"));
    }

    @Test
    public void countBallotsTest_6() throws Exception {
        Ballots ballots = new Ballots();

        ballots.addBallot("");
        assertEquals(1, ballots.countBallots(""));
    }
*/
    @Test
    public void countBallotsTest_7() throws Exception {
        Ballots ballots = new Ballots();

        ballots.addBallot("101");
        ballots.addBallot("102");
        ballots.addBallot("103");
        ballots.addBallot("101");
        assertEquals(4, ballots.countBallots());
        ballots.removeAllBallot();
        assertEquals(0, ballots.countBallots());
    }

    @Test
    public void getBallotsTest_1() throws Exception {
        Ballots ballots = new Ballots();

        ballots.addBallot("101");
        ballots.addBallot("102");
        ballots.addBallot("103");
        List<Ballot> ballotList = ballots.getBallots();

        assertEquals("101", ballotList.get(0).getCandidateUUID());
        assertEquals("102", ballotList.get(1).getCandidateUUID());
        assertEquals("103", ballotList.get(2).getCandidateUUID());
    }

    @Test
    public void getBallotsTest_2() throws Exception {
        Ballots ballots = new Ballots();

        ballots.addBallot("");
        List<Ballot> ballotList = ballots.getBallots();
        assertEquals("", ballotList.get(0).getCandidateUUID());
    }

    @Test
    public void getBallotsTest_3() throws Exception {
        Ballots ballots = new Ballots();

        ballots.addBallot(null);
        List<Ballot> ballotList = ballots.getBallots();
        assertEquals(null, ballotList.get(0).getCandidateUUID());
    }
}