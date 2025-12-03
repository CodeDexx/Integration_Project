package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manages the collection of showtimes in the theater system.
 * Handles adding, removing, and retrieving showtimes.
 * 
 * @author Ian
 * @version 1.0
 */
public class ShowtimeManager {
    private final ObservableList<Showtimes> showtime;

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
     * Gets the list of all showtimes.
     * 
     * @return an ObservableList containing all showtimes
     */
    public ObservableList<Showtimes> getShowtimes() {
        return showtime;
    }

    /**
     * Adds a showtime to the collection.
     * 
     * @param showtime the showtime to add
     * @throws IllegalArgumentException if showtime is null
     */
    public void addShowtime(Showtimes showtime) {
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
    public void removeShowtime(Showtimes showtime) {
        if (showtime == null) {
            throw new IllegalArgumentException("Showtime cannot be null");
        }
        this.showtime.remove(showtime);
    }
}
