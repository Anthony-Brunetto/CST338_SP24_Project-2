package com.example.tune_trade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.example.tune_trade.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** UserTest
 * Runs Unit tests for User entity object
 * @author Syed Muhammad Ali Hussain Shah
 * @since 05-01-2024
 */

public class UserTest {
    User user;

    @Before
    public void setUp() {
        user = new User("test", "test","test");
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void isAdmin() {
        assertFalse(user.isAdmin());
    }

    @Test
    public void setAdmin() {
        assertFalse(user.isAdmin());
        user.setAdmin(true);
        assertTrue(user.isAdmin());
    }

    @Test
    public void getUsername() {
        assertEquals("test", user.getUsername());
    }

    @Test
    public void setUsername() {
        assertNotEquals("dog", user.getUsername());
        user.setUsername("dog");
        assertEquals("dog", user.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("test", user.getPassword());
    }

    @Test
    public void setPassword() {
        assertNotEquals("pawsome", user.getPassword());
        user.setPassword("pawsome");
        assertEquals("pawsome", user.getPassword());
    }
}
