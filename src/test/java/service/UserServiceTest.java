package service;

import model.Participant;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.UserService;

import java.util.*;

import static org.junit.Assert.*;

public class UserServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void loginTest_1() throws Exception {
        UserService userService = new UserService();
        assertEquals( Integer.valueOf(1), userService.login("0", "0"));
    }

    @Test
    public void loginTest_2() throws Exception {
        UserService userService = new UserService();
        assertEquals( Integer.valueOf(0), userService.login("100", "100"));
    }

    @Test
    public void loginTest_3() throws Exception {
        UserService userService = new UserService();
        assertEquals( null, userService.login("200", "200"));
    }


    @Test
    public void updateUserVotingStatusTest_1() throws Exception {
        UserService userService = new UserService();
        assertEquals( false, userService.getUserVoted("100"));
        userService.updateUserVoted("100", "123");
        assertEquals( true, userService.getUserVoted("100"));
    }

    @Test
    public void updateUserVotingStatusTest_2() throws Exception {
        UserService userService = new UserService();
        assertEquals( false, userService.getUserVoted("200"));
        userService.updateUserVoted("200", "123");
        assertEquals( false, userService.getUserVoted("200"));
    }

    @Test
    public void updateUserVotingStatusTest_3() throws Exception {
        UserService userService = new UserService();
        assertEquals( false, userService.getUserVoted("0"));
        userService.updateUserVoted("0", "123");
        assertEquals( false, userService.getUserVoted("0"));
    }

    @Test
    public void getUserBallotUUIDTest_1() throws Exception {
        UserService userService = new UserService();
        assertEquals( "", userService.getUserBallotUUID("100"));
        userService.updateUserVoted("100", "123");
        assertEquals( "123", userService.getUserBallotUUID("100"));
    }

    @Test
    public void getUserBallotUUIDTest_2() throws Exception {
        UserService userService = new UserService();
        assertEquals(null, userService.getUserBallotUUID("0"));
        userService.updateUserVoted("0", "123");
        assertEquals(null, userService.getUserBallotUUID("0"));
    }

    @Test
    public void getUserBallotUUIDTest_3() throws Exception {
        UserService userService = new UserService();
        assertEquals( null, userService.getUserBallotUUID("200"));
        userService.updateUserVoted("200", "123");
        assertEquals( null, userService.getUserBallotUUID("200"));
    }

    @Test
    public void resetUserVotedTest_1() throws Exception {
        UserService userService = new UserService();
        assertEquals( false, userService.getUserVoted("100"));
        userService.updateUserVoted("100", "123");
        assertEquals( true, userService.getUserVoted("100"));
        userService.resetUserVoted();
        assertEquals( false, userService.getUserVoted("100"));
    }

    @Test
    public void resetUserVotedTest_2() throws Exception {
        UserService userService = new UserService();
        assertEquals( false, userService.getUserVoted("200"));
        userService.updateUserVoted("200", "123");
        assertEquals( false, userService.getUserVoted("200"));
        userService.resetUserVoted();
        assertEquals( false, userService.getUserVoted("200"));
    }

    @Test
    public void resetUserVotedTest_3() throws Exception {
        UserService userService = new UserService();
        assertEquals( false, userService.getUserVoted("0"));
        userService.updateUserVoted("0", "123");
        assertEquals( false, userService.getUserVoted("0"));
        userService.resetUserVoted();
        assertEquals( false, userService.getUserVoted("0"));
    }

    @Test
    public void checkAccountTest_1() throws Exception {
        UserService userService = new UserService();
        assertEquals( true, userService.checkAccount("0"));
    }

    @Test
    public void checkAccountTest_2() throws Exception {
        UserService userService = new UserService();
        assertEquals( true, userService.checkAccount("100"));
    }

    @Test
    public void checkAccountTest_3() throws Exception {
        UserService userService = new UserService();
        assertEquals( false, userService.checkAccount("200"));
    }

}
