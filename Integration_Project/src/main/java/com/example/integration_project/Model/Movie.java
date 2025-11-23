package com.example.integration_project.Model;

public class Movie {
    private String movieName;

    public Movie(String movieName) {
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

}
