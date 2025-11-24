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

    // List to store the clients managed by this manager
    private final List<Client> aClient;

    /**
     * Constructor to create a Manager with specified email and password.
     * @param pEmail
     * @param pPassword
     */
    public Manager(String pEmail, String pPassword) {
        super(pEmail, pPassword); // Call the constructor of the superclass User
        this.aClient = new ArrayList<>(); // Initialize the client list
    }

   
     /**
     * Method to add a client to the manager's list
     * @throws IllegalArgumentException if the client is null
     * @param pClient the client to be added
     * 
     *  */ 
    public void addUser(Client pClient) {
        if(pClient == null){
            throw new IllegalArgumentException("User does not exist"); // Check for null client
        }
        this.aClient.add(pClient); // Add client to the list
    }

    /**<p>
     * Method to remove a client from the manager's list
     * </p>
     * @throws IllegalArgumentException if the client is null
     * @param pClient the client to be removed
     *  */ 
    public void deleteUser(Client pClient) {
        if(pClient == null){
            throw new IllegalArgumentException("User does not exist"); // Check for null client
        }
        this.aClient.remove(pClient); // Remove client from the list
    }

    /**<p>
     * Method to get a copy of the list of clients managed by this manager
     * </p>
     * @return a list of clients managed by this manager
     *  */ 
    public List<Client> getClients() {
        return  new ArrayList<>(this.aClient); // Return a new list to prevent external modification
    }
}
