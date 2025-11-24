package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manages the collection of movies in the theater system.
 * Handles adding, removing, and retrieving movies.
 * 
 * @author Ian
 * @version 1.0
 */
public class MovieManager {
    private final ObservableList<Movie> movies = FXCollections.observableArrayList();

    /**
     * Gets the list of all movies.
     * 
     * @return an ObservableList containing all movies
     */
    public ObservableList<Movie> getMovies() {
        return movies;
    }

    /**
     * Adds a movie to the collection.
     * 
     * @param movie the movie to add
     * @throws IllegalArgumentException if movie is null
     */
    public void addMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        movies.add(movie);
    }

    /**
     * Removes a movie from the collection.
     * 
     * @param movie the movie to remove
     * @throws IllegalArgumentException if movie is null
     */
    public void removeMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        movies.remove(movie);
    }
}
