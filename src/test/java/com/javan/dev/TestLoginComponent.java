package com.javan.dev;

/**
 * Include necessary libraries
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
class TestLoginComponent {

    private LoginComponent loginComponent;

    /**
     * Create a LoginComponent instance each time a test is run
     */
    @BeforeEach
    void setUp() {
        loginComponent = LoginComponent.getInstance();
    }

    /**
     * Method to check that singleton instance is not null and that it returns the same instance
     */
    @Test
    @DisplayName("Should return same instance")
    void testGetInstanceReturnsSameInstance() {
        LoginComponent instance1 = LoginComponent.getInstance();
        LoginComponent instance2 = LoginComponent.getInstance();
        assertEquals(instance1, instance2);
    }

    /**
     * Method to test that main panel is returned and non-null
     */
    @Test
    @DisplayName("Should return main panel")
    void testGetMainPanel() {
        JPanel mainPanel = loginComponent.getMainPanel();
        assertNotNull(mainPanel);
    }

    /**
     * Method to test that login panel is returned and non-null
     */
    @Test
    @DisplayName("Should return login panel")
    void testGetLoginPanel() {
        JPanel loginPanel = loginComponent.getLoginPanel();
        assertNotNull(loginPanel);
    }

    /**
     * Method to test that create account panel is returned and non-null
     */
    @Test
    @DisplayName("Should return create account panel")
    void testGetCreateAccountPanel() {
        JPanel createAccountPanel = loginComponent.getCreateAccountPanel();
        assertNotNull(createAccountPanel);
    }

    /**
     * Method to test that current panel is returned and non-null
     */
    @Test
    @DisplayName("Should return current panel")
    void testGetCurrentPanel() {
        JPanel currentPanel = loginComponent.getCurrentPanel();
        assertNotNull(currentPanel);
    }

    /**
     * Method to test that login status is returned and non-null
     */
    @Test
    @DisplayName("Should return login status")
    void testGetLoginStatus() {
        boolean isLoggedIn = loginComponent.getLoginStatus();
        assertFalse(isLoggedIn);
    }

    /**
     * Method to test that login status is set to true
     */
    @Test
    @DisplayName("Should set login status")
    void testSetLoginStatus() {
        loginComponent.setLoginStatus(true);
        assertTrue(loginComponent.getLoginStatus());
    }

    /**
     * Method to test that login panel is opened
     */
    @Test
    @DisplayName("Should open login panel")
    void testOpenLoginPanel() {
        loginComponent.openLoginPanel();
        assertTrue(loginComponent.getCurrentPanel() == loginComponent.getLoginPanel());
    }

    /**
     * Method to test that create account panel is opened
     */
    @Test
    @DisplayName("Should open create account panel")
    void testOpenCreateAccountPanel() {
        loginComponent.openCreateAccountPanel();
        assertTrue(loginComponent.getCurrentPanel() == loginComponent.getCreateAccountPanel());
    }

    /**
     * Method to test that login button is created and non-null
     */
    @Test
    @DisplayName("Should create login button")
    void testCreateLoginButton() {
        JButton loginButton = loginComponent.createButton("Login");
        assertNotNull(loginButton);
    }

    /**
     * Method to test that create account button is created and non-null
     */
    @Test
    @DisplayName("Should create create account button")
    void testCreateCreateAccountButton() {
        JButton createAccountButton = loginComponent.createButton("Create Account");
        assertNotNull(createAccountButton);
    }

    /**
     * Method to test that username text field is created and non-null
     */
    @Test
    @DisplayName("Should create username text field")
    void testCreateUsernameTextField() {
        JTextField usernameTextField = loginComponent.createTextField("Username");
        assertNotNull(usernameTextField);
    }

    /**
     * Method to test that password text field is created and non-null
     */
    @Test
    @DisplayName("Should create password text field")
    void testCreatePasswordTextField() {
        JPasswordField passwordTextField = new JPasswordField();
        assertNotNull(passwordTextField);
    }

    /**
     * Method to test that confirm password text field is created and non-null
     */
    @Test
    @DisplayName("Should create confirm password text field")
    void testCreateConfirmPasswordTextField() {
        JPasswordField confirmPasswordTextField = new JPasswordField();
        assertNotNull(confirmPasswordTextField);
    }
}
