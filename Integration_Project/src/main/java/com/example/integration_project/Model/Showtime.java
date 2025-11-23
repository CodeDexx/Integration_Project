package com.example.integration_project.Model;

import java.time.LocalDateTime;

public class Showtime {
    private LocalDateTime showtime;
    private Movie movie;
    private Showroom showroom;

    public Showtime(Movie movie, LocalDateTime showtime, Showroom showroom) {
        setMovie(movie);
        setShowtime(showtime);
        setShowroom(showroom);
    }

    public LocalDateTime getShowtime() {
        return showtime;
    }

    public Movie getMovie() {
        return movie;
    }

    public Showroom getShowroom() {
        return showroom;
    }

    public void setMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie must not be null");
        }
        this.movie = movie;
    }

    public void setShowtime(LocalDateTime showtime) {
        if (showtime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Showtime must be in the future");
        }
        this.showtime = showtime;
    }

    public void setShowroom(Showroom showroom) {
        if (showroom == null) {
            throw new IllegalArgumentException("Showroom must not be null");
        }
        this.showroom = showroom;
    }

}
