package com.example.integration_project.Model;

/**
 * @author Emmanuelle
 * This class inherits the user properties such as name, email and password.
 */
public class Client extends User{

    /**
     * Creates a new client object
     * @param pName the client's name
     * @param pEmail the client's Email
     * @param pPassword the client's password
     */
    public Client (String pName, String pEmail,  String pPassword) {
        super(pName, pEmail, pPassword);
    }
}
