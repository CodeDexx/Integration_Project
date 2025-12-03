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
    private final ObservableList<Movie> movies;

    private static MovieManager aInstance;

    public MovieManager() {
        movies = FXCollections.observableArrayList();
    }

    public static MovieManager getMovieManagerInstance() {
        if (aInstance == null) {
            aInstance = new MovieManager();
        }
        return aInstance;
    }
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
     * @throws IllegalArgumentException if movie is null or already exists in the collection
     */
    public void addMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        if (movieExists(movie.getName())) {
            throw new IllegalArgumentException("Movie \"" + movie.getName() + "\" already exists.");
        }
        movies.add(movie);
    }

    /**
     * Checks if a movie with the given name already exists in the collection.
     * 
     * @param movieName the name of the movie to check
     * @return true if a movie with that name exists, false otherwise
     */
    public boolean movieExists(String movieName) {
        for (Movie m : movies) {
            if (m.getName().equalsIgnoreCase(movieName)) {
                return true;
            }
        }
        return false;
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
