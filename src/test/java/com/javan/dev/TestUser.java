package com.javan.dev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

public class TestUser {
    User user = User.getInstance();

    /**
     * Method to test that makeAdmin sets the user admin status appropriately
     */
    @Test
    @DisplayName("Should confirm that makeAdmin makes the user admin status true")
    public void testMakeAdmin() {
        user.setIsAdmin(false);
        assertFalse(user.getIsAdmin());

        user.makeAdmin();
        assertTrue(user.getIsAdmin());
    }

    /**
     * Method to test that the user instance is the same across multiple variabels
     */
    @Test
    @DisplayName("Should confirm that both user objects are the same")
    public void testGetInstance() {
        User user1 = User.getInstance();
        User user2 = User.getInstance();

        assertSame(user1, user2);
    }
    

    /**
     * Method to test user getter and setter methods
     */
    @Test
    @DisplayName("Should confirm that the relevant getters and setters are working")
    public void testGettersAndSetters() {
        user.setUserID(123);
        user.setUsername("username");
        user.setIsAdmin(false);

        assertEquals("username", user.getUsername());
        assertEquals(123, user.getUserID());
        assertFalse(user.getIsAdmin());

        user.setUsername("newUsername");
        user.setUserID(456);
        user.setIsAdmin(true);

        assertEquals("newUsername", user.getUsername());
        assertEquals(456, user.getUserID());
        assertTrue(user.getIsAdmin());
    }
}
