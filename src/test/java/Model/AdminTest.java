package Model;

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
        Admin admin = new Admin("qwe", "123", 2, "abc@gmail.com");

        assertEquals("qwe", admin.getAccount());
        assertEquals("123", admin.getPassword());
        assertEquals(2, admin.getPrivilege());
        assertEquals("abc@gmail.com", admin.getEmail());
    }

    @Test
    public void AdminTest_2() throws  Exception {
        Admin admin = new Admin("qwe", "123", 2, "abc@gmail.com");

        assertEquals("qwe", admin.getAccount());
        assertEquals("123", admin.getPassword());
        assertEquals(2, admin.getPrivilege());
        assertEquals("abc@gmail.com", admin.getEmail());
        admin.setEmail("abc@yahoo.com");
        assertEquals("abc@yahoo.com", admin.getEmail());
    }
}
