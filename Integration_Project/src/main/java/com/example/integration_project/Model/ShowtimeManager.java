package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manages the collection of Showtime in the theater system.
 * Handles adding, removing, and retrieving Showtime.
 * 
 * @author Ian
 * @version 1.0
 */
public class ShowtimeManager {
    private static ObservableList<Showtime> showtime;

    private static ShowtimeManager aInstance;

    public ShowtimeManager() {
        showtime = FXCollections.observableArrayList();
    }

    public static ShowtimeManager getShowtimeManagerInstance() {
        if (aInstance == null) {
            aInstance = new ShowtimeManager();
        }
        return aInstance;
    }

    /**
     * Gets the list of all Showtime.
     * 
     * @return an ObservableList containing all Showtime
     */
    public static ObservableList<Showtime> getShowtime() {
        return showtime;
    }

    /**
     * Adds a showtime to the collection.
     * 
     * @param showtime the showtime to add
     * @throws IllegalArgumentException if showtime is null
     */
    public void addShowtime(Showtime showtime) {
        if (showtime == null) {
            throw new IllegalArgumentException("Showtime cannot be null");
        }
        this.showtime.add(showtime);
    }

    /**
     * Removes a showtime from the collection.
     * 
     * @param showtime the showtime to remove
     * @throws IllegalArgumentException if showtime is null
     */
    public void removeShowtime(Showtime showtime) {
        if (showtime == null) {
            throw new IllegalArgumentException("Showtime cannot be null");
        }
        this.showtime.remove(showtime);
    }
}
