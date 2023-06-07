package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ParticipantTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void Participant_1() throws Exception {
        Participant participant = new Participant("qwe", "123", 2, "abc@gmail.com");

        assertEquals("qwe", participant.getAccount());
        assertEquals("123", participant.getPassword());
        assertEquals(2, participant.getPrivilege());
        assertEquals("abc@gmail.com", participant.getEmail());
    }

    @Test
    public void Participant_2() throws  Exception {
        Participant participant = new Participant("qwe", "123", 2, "abc@gmail.com");

        assertEquals("qwe", participant.getAccount());
        assertEquals("123", participant.getPassword());
        assertEquals(2, participant.getPrivilege());
        assertEquals("abc@gmail.com", participant.getEmail());
        participant.setEmail("abc@yahoo.com");
        assertEquals("abc@yahoo.com", participant.getEmail());
    }

    @Test
    public void setBallotUUIDTest_1() throws  Exception {
        Participant participant = new Participant("qwe", "123", 2, "abc@gmail.com");

        assertEquals("", participant.getBallotUUID());
        participant.setBallotUUID("987");
        assertEquals("987", participant.getBallotUUID());
    }

    @Test
    public void setVotedTest_1() throws  Exception {
        Participant participant = new Participant("qwe", "123", 2, "abc@gmail.com");

        assertEquals(false, participant.getVoted());
        participant.setVoted(true);
        assertEquals(true, participant.getVoted());
    }
}
