package com.example.integration_project.Model;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User{

    private final List<Client> aClient;

    public Manager(String pEmail, String pPassword) {
        super(pEmail, pPassword);
        this.aClient = new ArrayList<>();
    }

    public void addUser(Client pClient) {
        if(pClient!=null){
            throw new IllegalArgumentException("User does not exist");
        }
        this.aClient.add(pClient);
    }

    public void deleteUser(Client pClient) {
        if(pClient!=null){
            throw new IllegalArgumentException("User does not exist");
        }
        this.aClient.remove(pClient);
    }

    public List<Client> getClients() {
        return  new ArrayList<>(this.aClient);
    }
}
