package com.example.integration_project.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Manager who extends the User class and manages a list of clients.
 * Provides functionality to add, remove, and retrieve clients managed by this manager.
 * 
 * @author Dieudonne
 */
public class Manager extends User{

    /**
     * Constructor to create a Manager with specified email and password.
     * @param pEmail the manager's email address
     * @param pPassword the manager's password
     */
    public Manager(String pEmail, String pPassword) {
        super(pEmail, pPassword); // Call the constructor of the superclass User
    }
}
