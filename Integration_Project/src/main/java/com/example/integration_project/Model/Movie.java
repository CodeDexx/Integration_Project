package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Movie {
    private String movieName;
    private final ObservableList<Movie> movies = FXCollections.observableArrayList();

    public ObservableList<Movie> getMovies() {
        return movies;
    }

    public Movie(String name) {
        setMovieName(movieName);
    }

    public String getName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        if (movieName == null || movieName.isBlank()) {
            throw new IllegalArgumentException("Movie name must not be empty");
        }
        this.movieName = movieName.trim();
    }
    public String addMovie (Movie movie) {
        movies.add(movie);
        return movieName;
    }
    public String removeMovie (Movie movie) {
        movies.remove(movie);
        return movieName;
    }
    public String editMovie () {
        return movieName;
    }
}
