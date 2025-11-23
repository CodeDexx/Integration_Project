package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Showtime {
    private Date showtime;
    private final ObservableList<Showtime> showtimes = FXCollections.observableArrayList();

    public ObservableList<Showtime> getShowtimes() {
        return showtimes;
    }

    /* public Showtime(Date date) {
        setMovieName(movieName);
    }*/

   /* public String getName() {
        return movieName;
    }*/
    /*public void setMovieName(String movieName) {
        if (movieName == null || movieName.isBlank()) {
            throw new IllegalArgumentException("Movie name must not be empty");
        }
        this.movieName = movieName.trim();
    }*/
    public Date addShowtime (Showtime showtime) {
        showtimes.add(showtime);
        return showtime;
    }
    public String removeShowtime (Showtime showtime) {
        showtimes.remove(showtime);
        return showtime;
    }
    public String editShowtime () {
        return showtime;
    }
}
