package com.example.integration_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovieManager {
    private final ObservableList<Movie> movies = FXCollections.observableArrayList();

    public ObservableList<Movie> getMovies() {
        return movies;
    }
    public void addMovie (Movie movie) {
        movies.add(movie);
    }
    public void removeMovie (Movie movie) {
        movies.remove(movie);
    }
}
