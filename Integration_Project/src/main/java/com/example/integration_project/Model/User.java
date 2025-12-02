package com.example.integration_project.Model;

/**
 * Represents a user with a name, email address, and password.
 * <p>
 * This class provides constructors for creating a user with either a name, email, and password,
 * or with just an email and password. The email address is immutable once set.
 * </p>
 * <p>
 * Provides methods to retrieve the user's email and password, and to set a new password.
 * </p>
 *
 * @author Dieudonne
 */
public class User {
    private String aName;
    private String aPassword;
    private final String aEmail;

    /**
     * Constructs a User with the specified name, email, and password.
     *
     * @param pName the user's name
     * @param pEmail the user's email address
     * @param pPassword the user's password
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public User(String pName, String pEmail, String pPassword) {
        if (pName == null || pEmail == null || pPassword == null) {
            throw new IllegalArgumentException("Name, email or password is null");
        }
        this.aName = pName;
        this.aPassword = pPassword;
        this.aEmail = pEmail;
    }

    /**
     * Constructs a User with the specified email and password.
     *
     * @param pEmail the user's email address
     * @param pPassword the user's password
     * @throws IllegalArgumentException if either parameter is null
     */
    public User(String pEmail, String pPassword) {
        if (pEmail == null || pPassword == null) {
            throw new IllegalArgumentException("Either email or password is null");
        }
        this.aEmail = pEmail;
        this.aPassword = pPassword;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email address of the user
     */
    String getEmailAddress() {
        return this.aEmail;
    }

    /**
     * Returns the user's password.
     *
     * @return the password of the user
     */
    String getPassword() {
        return this.aPassword;
    }

    /**
     * Sets the user's password.
     *
     * @param pPassword the new password to set
     * @throws IllegalArgumentException if the password is null
     */
    void setPassword(String pPassword) {
        if (pPassword == null) {
            throw new IllegalArgumentException("Password is null");
        }
        this.aPassword = pPassword;
    }

    /**
     * Returns the user's name.
     *
     * @return the name of the user
     */
    String getName() {
        return this.aName;
    }

}
