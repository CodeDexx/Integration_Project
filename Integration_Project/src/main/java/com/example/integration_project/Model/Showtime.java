package com.example.integration_project.Model;

import java.time.LocalDateTime;

/**
 * Represents a showtime (screening) in the theater system.
 * Encapsulates information about when a movie is shown in a specific showroom.
 * 
 * @author Ian
 * @version 1.0
 */
public class Showtime {
    private LocalDateTime showtime;
    private Movie movie;
    private Showroom showroom;

    /**
     * Constructs a Showtime with the specified movie, date/time, and showroom.
     * 
     * @param movie    the movie to be shown
     * @param showtime the date and time of the screening (must be in the future)
     * @param showroom the showroom where the movie will be shown
     * @throws IllegalArgumentException if movie or showroom is null, or if showtime
     *                                  is in the past
     */
    public Showtime(Movie movie, LocalDateTime showtime, Showroom showroom) {
        setMovie(movie);
        setShowtime(showtime);
        setShowroom(showroom);
    }

    /**
     * Gets the date and time of the showtime.
     * 
     * @return the LocalDateTime of the showtime
     */
    public LocalDateTime getShowtime() {
        return showtime;
    }

    /**
     * Gets the movie that is being shown.
     * 
     * @return the Movie object
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Gets the showroom where the movie is being shown.
     * 
     * @return the Showroom object
     */
    public Showroom getShowroom() {
        return showroom;
    }

    /**
     * Sets the movie for this showtime with validation.
     * 
     * @param movie the movie to set
     * @throws IllegalArgumentException if movie is null
     */
    public void setMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie must not be null");
        }
        this.movie = movie;
    }

    /**
     * Sets the date and time of the showtime with validation.
     * 
     * @param showtime the date and time to set (must be in the future)
     * @throws IllegalArgumentException if showtime is in the past
     */
    public void setShowtime(LocalDateTime showtime) {
        if (showtime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Showtime must be in the future");
        }
        this.showtime = showtime;
    }

    /**
     * Sets the showroom for this showtime with validation.
     * 
     * @param showroom the showroom to set
     * @throws IllegalArgumentException if showroom is null
     */
    public void setShowroom(Showroom showroom) {
        if (showroom == null) {
            throw new IllegalArgumentException("Showroom must not be null");
        }
        this.showroom = showroom;
    }

}
