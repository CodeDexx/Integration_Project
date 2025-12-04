package com.example.integration_project.Model;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {

    private static ClientManager instance;
    private final List<Client> clients = new ArrayList<>();

    private ClientManager() {}

    public static ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addClient(Client client) {
        clients.add(client);
    }
}

