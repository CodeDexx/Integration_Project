package com.example.integration_project.Model;

public class User {
    private String aName;
    private String aPassword;
    private final String aEmail;

    public User(String pName, String pEmail, String pPassword) {
        if(isNullOrEmpty(pName) || isNullOrEmpty(pEmail) || isNullOrEmpty(pPassword)) {
            throw new IllegalArgumentException("Name, email or password is null");
        }
        this.aName = pName;
        this.aPassword = pPassword;
        this.aEmail = pEmail;
    }

    public User(String pEmail, String pPassword) {
        if(isNullOrEmpty(pEmail) || isNullOrEmpty(pPassword)){
            throw new IllegalArgumentException("Either email or password is null");
        }
        this.aEmail = pEmail;
        this.aPassword = pPassword;
    }

    private boolean isNullOrEmpty(String pValue) {
        return pValue == null || pValue.isEmpty();
    }

    public String getName() {
        return aName;
    }

    public String setName(String pName) {
        if(isNullOrEmpty(pName)) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.aName = pName;
        return pName;
    }

    public String getEmailAddress(){
        return this.aEmail;
    }

    public String getPassword(){
        return this.aPassword;
    }

    void setPassword(String pPassword){
        if(isNullOrEmpty(pPassword)) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        this.aPassword = pPassword;
    }
}
