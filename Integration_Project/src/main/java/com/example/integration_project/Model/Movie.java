package com.example.integration_project.Model;

/**
 * Represents a movie in the theater system.
 * Encapsulates movie information including its name.
 * 
 * @author Ian
 * @version 1.0
 */
public class Movie {
    private String movieName;

    /**
     * Constructs a Movie with the specified name.
     * 
     * @param movieName the name of the movie
     * @throws IllegalArgumentException if movieName is null or blank
     */
    public Movie(String movieName) {
        setMovieName(movieName);
    }

    /**
     * Gets the name of the movie.
     * 
     * @return the name of the movie
     */
    public String getName() {
        return movieName;
    }

    /**
     * Sets the name of the movie with validation.
     * 
     * @param movieName the name to set for the movie
     * @throws IllegalArgumentException if movieName is null or blank
     */
    public void setMovieName(String movieName) {
        if (movieName == null || movieName.isBlank()) {
            throw new IllegalArgumentException("Movie name must not be empty");
        }
        this.movieName = movieName.trim();
    }

    @Override
    public String toString() {
        return movieName;
    }

}
