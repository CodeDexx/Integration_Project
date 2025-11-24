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
    private final ObservableList<Showtime> showtimes = FXCollections.observableArrayList();

    /**
     * Gets the list of all showtimes.
     * 
     * @return an ObservableList containing all showtimes
     */
    public ObservableList<Showtime> getShowtimes() {
        return showtimes;
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
        showtimes.add(showtime);
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
        showtimes.remove(showtime);
    }
}
