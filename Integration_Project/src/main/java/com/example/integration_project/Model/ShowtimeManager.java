package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShowtimeManager {
    private final ObservableList<Showtime> showtimes = FXCollections.observableArrayList();

    public ObservableList<Showtime> getShowtimes() {
        return showtimes;
    }

    public void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
    }

    public void removeShowtime(Showtime showtime) {
        showtimes.remove(showtime);
    }
}
