package edu.utsa.cs3343.rhdraft1.model;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import edu.utsa.cs3343.rhdraft1.MainActivity;

/**
 * The UserBank class represents a collection of users and provides methods
 * for loading user data from a file, retrieving the list of users, and adding users.
 * @author Chaz Ortiz (zse260)
 * UTSA CS 3443 - Lab 5
 * Fall 2023
 */
public class UserBank {

    // create an array list of users
    public ArrayList<edu.utsa.cs3343.rhdraft1.model.User> users;

    /**
     * Constructs a UserBank object with an empty list of users.
     */
    public UserBank() {
        users = new ArrayList<edu.utsa.cs3343.rhdraft1.model.User>();
    }

    /**
     * Reads user data from a file and populates the list of users.
     *
     * @param activity The MainActivity context for accessing the asset manager.
     */
    public void loadUsers(MainActivity activity) {
        AssetManager manager = activity.getAssets();
        if (users == null) {
            users = new ArrayList<edu.utsa.cs3343.rhdraft1.model.User>();
        }
        Scanner scan = null;
        String filename = "users.csv";

        try {
            InputStream file = manager.open(filename);
            scan = new Scanner(file);
            String line;
            String[] tokens;

            while (scan.hasNextLine()) {
                line = scan.nextLine();
                tokens = line.split(",");

                if (tokens.length >= 3) {
                    String userName = tokens[0];
                    String password = tokens[1];
                    String realName = tokens[2];
                    ArrayList<String> roles = new ArrayList<>();

                    // Start adding roles from tokens[4] onwards
                    for (int i = 4; i < tokens.length; i++) {
                        roles.add(tokens[i]);
                    }

                    edu.utsa.cs3343.rhdraft1.model.User user = new edu.utsa.cs3343.rhdraft1.model.User(userName, password, realName, roles);
                    addUser(user);
                }
            }
        } catch (IOException e) {
            // Handle exceptions related to file access
            // You can log the exception and/or show a user-friendly message
            e.printStackTrace();
            // Example of a user-friendly message:
            // Toast.makeText(activity, "Error loading user data. Please try again later.", Toast.LENGTH_SHORT).show();
        } finally {
            if (scan != null) {
                scan.close();
            }
        }
    }

    /**
     * Gets the list of users in the UserBank.
     *
     * @return The list of users.
     */
    public ArrayList<edu.utsa.cs3343.rhdraft1.model.User> getUsers() {
        return users;
    }

    /**
     * Sets the list of users in the UserBank.
     *
     * @param users The new list of users.
     */
    public void setUsers(ArrayList<edu.utsa.cs3343.rhdraft1.model.User> users) {
        this.users = users;
    }

    /**
     * Adds a user to the list of users in the UserBank.
     *
     * @param user The user to add.
     */
    private void addUser(edu.utsa.cs3343.rhdraft1.model.User user) {
        users.add(user);
    }

    /**
     * Overrides the default toString method to provide a string representation of the UserBank.
     *
     * @return A string representation of the UserBank.
     */
    @Override
    public String toString() {
        return "UserBank{" +
                "users=" + users +
                '}';
    }
}