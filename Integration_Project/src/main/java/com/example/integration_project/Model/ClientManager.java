package com.example.integration_project.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ClientManager</h1>
 * The ClientManager class implements the **Singleton pattern**
 * to manage a central collection of all {@link Client} objects in the system.
 * It provides controlled access to the list of clients across the application.
 *
 * @author Dieudonné
 * @version 1.0
 * @see Client
 */
public class ClientManager {

    /**
     * The single, static instance of the ClientManager (Singleton implementation).
     */
    private static ClientManager instance;

    /**
     * The list storing all managed {@link Client} objects.
     */
    private final List<Client> clients;

    /**
     * Private constructor to prevent direct instantiation and enforce the Singleton pattern.
     */
    private ClientManager() {
        clients = new ArrayList<>();
    }

    /**
     * Returns the single instance of the ClientManager class.
     * If the instance has not yet been created, it is initialized.
     *
     * @return The sole instance of the ClientManager.
     */
    public static ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    /**
     * Retrieves the list of all clients managed by the system.
     *
     * @return The {@code List<Client>} containing all registered clients.
     */
    public List<Client> getClients() {
        return new ArrayList<>(clients);
    }

    /**
     * Adds a new {@link Client} to the managed list of clients.
     *
     * @param client The {@link Client} object to be added to the system.
     */
    public void addClient(Client client) {
        clients.add(client);
    }
}
