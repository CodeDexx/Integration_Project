package com.example.integration_project.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a showtime (screening) in the theater system.
 * Encapsulates information about when a movie is shown in a specific showroom.
 * 
 * @author Ian
 * @version 1.0
 */
public class Showtimes {
    private LocalDateTime showtime;
    private Movie movie;
    private Showrooms showroom;

    /**
     * Constructs a Showtime with the specified movie, date/time, and showroom.
     * 
     * @param movie    the movie to be shown
     * @param showtime the date and time of the screening (must be in the future)
     * @param showroom the showroom where the movie will be shown
     * @throws IllegalArgumentException if movie or showroom is null, or if showtime
     *                                  is in the past
     */
    public Showtimes(Movie movie, LocalDateTime showtime, Showrooms showroom) {
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
    public Showrooms getShowroom() {
        return showroom;
    }

    public LocalDate getDate() {
        return this.showtime.toLocalDate();
    }

    // Existing methods in the Showtime class

    /**
     * Gets the time of the showtime.
     * @return the time as a String
     */
    public String getTime() {
        return this.showtime.toLocalTime().toString();
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
    public void setShowroom(Showrooms showroom) {
        if (showroom == null) {
            throw new IllegalArgumentException("Showroom must not be null");
        }
        this.showroom = showroom;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        // Combine with existing time
        LocalTime currentTime = this.showtime.toLocalTime();
        setShowtime(LocalDateTime.of(date, currentTime));
    }
    
    public void setTime(String timeString) {
        if (timeString == null || timeString.isBlank()) {
            throw new IllegalArgumentException("Time cannot be empty");
        }
        try {
            LocalTime time = LocalTime.parse(timeString); // "HH:mm" format
            LocalDate currentDate = this.showtime.toLocalDate();
            setShowtime(LocalDateTime.of(currentDate, time));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid time format. Use HH:mm");
        }
    }

    @Override
    public String toString() {
        return movie.getName() + " - " + showtime.toLocalDate() + " " + showtime.toLocalTime() + " (Room " + showroom.getRoomNumber() + ")";
    }

}
