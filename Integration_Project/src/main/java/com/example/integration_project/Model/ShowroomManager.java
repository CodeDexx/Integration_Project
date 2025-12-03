package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manages the collection of Showroom in the theater system.
 * Handles adding, removing, and retrieving Showroom.
 * 
 * @author Ian
 * @version 1.0
 */
public class ShowroomManager {
    private static ObservableList<Showroom> Showroom;

    private static ShowroomManager aInstance;

    public ShowroomManager() {
        Showroom = FXCollections.observableArrayList();
    }

    public static ShowroomManager getShowroomManagerInstance() {
        if (aInstance == null) {
            aInstance = new ShowroomManager();
        }
        return aInstance;
    }
    /**
     * Gets the list of all Showroom.
     * 
     * @return an ObservableList containing all Showroom
     */
    public static ObservableList<Showroom> getShowroom() {
        return Showroom;
    }

    /**
     * Adds a showroom to the collection.
     * 
     * @param showroom the showroom to add
     * @throws IllegalArgumentException if showroom is null
     */
    public void addShowroom(Showroom showroom) {
        if (showroom == null) {
            throw new IllegalArgumentException("Showroom cannot be null");
        }
        Showroom.add(showroom);
    }

    /**
     * Removes a showroom from the collection.
     * 
     * @param showroom the showroom to remove
     * @throws IllegalArgumentException if showroom is null
     */
    public void removeShowroom(Showroom showroom) {
        if (showroom == null) {
            throw new IllegalArgumentException("Showroom cannot be null");
        }
        Showroom.remove(showroom);
    }
}
