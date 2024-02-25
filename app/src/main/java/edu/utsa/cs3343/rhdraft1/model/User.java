package edu.utsa.cs3343.rhdraft1.model;

import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The User class represents a user in the application, including information
 * such as username, password, real name, and a list of roles.
 * Implements Serializable to allow the User class to be passed directly between activities.
 * @author Chaz Ortiz (zse260)
 * UTSA CS 3443 - Lab 5
 * Fall 2023
 */
public class User implements Serializable {
    // instance variables
    private String userName;
    private String password;
    private String realName;
    private ArrayList<String> roles;  // String ArrayList for roles

    /**
     * Constructs a User object with the specified username, password, real name, and roles.
     *
     * @param userName The username of the user.
     * @param password The password associated with the user.
     * @param realName The real name of the user.
     * @param roles    A list of roles associated with the user.
     */
    public User(String userName, String password, String realName, ArrayList<String> roles) {
        this.userName = userName;
        this.password = password;
        this.realName = realName;
        this.roles = roles;
    }

    /**
     * Validates the provided username and password against the user's credentials.
     *
     * @param userName The username to validate.
     * @param password The password to validate.
     * @return True if the provided username and password match the user's credentials; otherwise, false.
     */
    public boolean validate(String userName, String password) {
        // Check if the provided username and password match the valid credentials
        return userName.equals(this.userName) && password.equals(this.password);
    }

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user.
     *
     * @param userName The new username.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the real name of the user.
     *
     * @return The real name.
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Gets the list of roles associated with the user.
     *
     * @return A list of roles.
     */
    public ArrayList<String> getRoles() {
        return roles;
    }

    /**
     * Serialization version UID to avoid warnings.
     */
    private static final long serialVersionUID = 123L;
}