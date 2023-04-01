package com.javan.dev;

/**
 * Include necessary libraries
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JFrame;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
class TestUserHelp {

    private UserHelp userHelp;

    @BeforeEach
    void setUp() {
        userHelp = UserHelp.getInstance();
    }

    /**
     * This method is called to check that the UserHelp singleton instance is not null and that it is the same instance
     */
    @Test
    void testSingleton() {
        UserHelp userHelp2 = UserHelp.getInstance();
        assertSame(userHelp, userHelp2);
    }

    /**
     * This method is called to check that the frame is not null
     */
    @Test
    void testJFrameNotNull() {
        JFrame frame = userHelp.getFrame();
        assertNotNull(frame);
    }

    /**
     * This method is called to check that the frame is visible when the menu is opened
     */
    @Test
    void testOpenHelpMenu() {
        userHelp.openHelpMenu();
        assertTrue(userHelp.getFrame().isVisible());
    }
}
