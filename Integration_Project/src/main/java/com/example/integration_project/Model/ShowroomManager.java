package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manages the collection of showrooms in the theater system.
 * Handles adding, removing, and retrieving showrooms.
 * 
 * @author Ian
 * @version 1.0
 */
public class ShowroomManager {
    private final ObservableList<Showroom> showrooms = FXCollections.observableArrayList();

    /**
     * Gets the list of all showrooms.
     * 
     * @return an ObservableList containing all showrooms
     */
    public ObservableList<Showroom> getShowrooms() {
        return showrooms;
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
        showrooms.add(showroom);
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
        showrooms.remove(showroom);
    }
}
