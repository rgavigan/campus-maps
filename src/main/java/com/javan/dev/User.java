package com.javan.dev;

/**
 * User class that stores the instance of a user.
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca], Dylan Sta Ana [dstaana@uwo.ca]
 * @version : 1.1
 * @since : 1.0
 */
public final class User {
    /**
     * Declaring variables of the user class
     */
    private String username;
    private final String password;
    private int userID;
    private boolean isAdmin;

    /**
     * Singleton instance of User
     */
    private static User INSTANCE;

    /**
     * Constructor for the User class to initialize user/pass when account created
     * @param username of the user
     * @param password of the user
     * @param userID of the user
     */
    private User(String username, String password, int userID) {
        this.username = username;
        this.isAdmin = false;
        this.password = password;
        this.userID = userID;
    }

    /**
     * Getter for the instance of user
     * @return user instance
     */
    public static User getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new User("admin", "admin", 1);
        }
        return INSTANCE;
    }

    /**
     * Function to turn a user into an admin
     */
    public void makeAdmin() {
        this.isAdmin = true;
    }

    /**
     * Getter for the username
     * @return username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter for the userID
     * @return userID of the user
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Getter for isAdmin
     * @return isAdmin status of the user
     */
    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    /**
     * Setter for username
     * @param username of the user to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for isAdmim
     * @param adminStatus of the user to be set
     */
    public void setIsAdmin(boolean adminStatus) {
        this.isAdmin = adminStatus;
    }

    /**
     * Setter for userPassword
     * @param userID of the user to be set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
}