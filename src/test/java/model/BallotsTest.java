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

    @Test
    public void numOfBallotsTest_1() throws Exception {
        Ballots ballots = new Ballots();

        ballots.addBallot("101");
        ballots.addBallot("102");
        ballots.addBallot("103");
        ballots.addBallot("101");
        assertEquals(4, ballots.numOfBallots());
        ballots.removeAllBallot();
        assertEquals(0, ballots.numOfBallots());
    }

    @Test
    public void numOfBallotsTest_2() throws Exception {
        Ballots ballots = new Ballots();

        assertEquals(0, ballots.numOfBallots());
        ballots.removeAllBallot();
        assertEquals(0, ballots.numOfBallots());
        ballots.addBallot("101");
        ballots.addBallot("102");
        ballots.addBallot("101");
        assertEquals(3, ballots.numOfBallots());
    }

    @Test
    public void getBallotsTest_1() throws Exception {
        Ballots ballots = new Ballots();

        String ballotsUUID1 =  ballots.addBallot("101");
        String ballotsUUID2 = ballots.addBallot("102");
        String ballotsUUID3 = ballots.addBallot("103");
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