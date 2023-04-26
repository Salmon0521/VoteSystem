package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BallotsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

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
}