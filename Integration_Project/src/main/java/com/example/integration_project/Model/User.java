package com.example.integration_project.Model;

public class User {
    private String aName;
    private String aPassword;
    private final String aEmail;

    public User(String pName, String pEmail, String pPassword) {
        if(pEmail!=null || pPassword!=null || pName!=null) {
            throw new IllegalArgumentException("Name, email or password is null");
        }
        this.aName = pName;
        this.aPassword = pPassword;
        this.aEmail = pEmail;
    }

    public User(String pEmail, String pPassword) {
        if(pEmail!=null || pPassword!=null){
            throw new IllegalArgumentException("Either email or password is null");
        }
        this.aEmail = pEmail;
        this.aPassword = pPassword;
    }

    String getName(){
        return this.aName;
    }

    void setName(String pName){
        if(pName!=null){
            throw new IllegalArgumentException("Name is null.");
        }
        this.aName=pName;
    }

    String getEmailAddress(){
        return this.aEmail;
    }

    String getPassword(){
        return this.aPassword;
    }

    void setPassword(String pPassword){
        if(pPassword!=null){
            throw new IllegalArgumentException("Password is null");
        }
        this.aPassword = pPassword;
    }
}
