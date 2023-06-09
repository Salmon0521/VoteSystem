package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AdminTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void AdminTest_1() throws  Exception {
        Admin admin = new Admin("qwe", "123", 2);

        assertEquals("qwe", admin.getAccount());
        assertEquals("123", admin.getPassword());
        assertEquals(2, admin.getPrivilege());
    }

    @Test
    public void AdminTest_2() throws  Exception {
        Admin admin = new Admin("", "", 0);

        assertEquals("", admin.getAccount());
        assertEquals("", admin.getPassword());
        assertEquals(0, admin.getPrivilege());
    }

    @Test
    public void AdminTest_3() throws  Exception {
        Admin admin = new Admin(null, null, 0);

        assertEquals(null, admin.getAccount());
        assertEquals(null, admin.getPassword());
        assertEquals(0, admin.getPrivilege());
    }
}
