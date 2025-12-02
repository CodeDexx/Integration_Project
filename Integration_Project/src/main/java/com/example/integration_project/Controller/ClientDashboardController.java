package com.example.integration_project.Controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Model.Movie;
import com.example.integration_project.Model.MovieManager;
import com.example.integration_project.Model.Showroom;
import com.example.integration_project.Model.ShowroomManager;
import com.example.integration_project.Model.Showtime;
import com.example.integration_project.Model.ShowtimeManager;
import com.example.integration_project.Model.Ticket;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ClientDashboardController {

    @FXML
    private ListView<Movie> aMoviesListView;

    @FXML
    private ListView<String> aDetailsListView;

    private MovieManager aMovieManager;// = MovieManager.getMovies();
    private ShowtimeManager aShowtimeManager; //= ShowtimeManager.getShowtimes();
    private ShowroomManager aShowroomManager;// = ShowroomManager.getShowrooms();

    private Movie aSelectedMovie;
    private Showtime aSelectedShowtime;
    private Showroom aSelectedShowroom;

    /** Showtime , Showroom */
    private final HashMap<Showtime, Showroom> aShowTimeRoom = new HashMap<>();


    @FXML
    private void initialize() {
        aMoviesListView.setItems(aMovieManager.getMovies());
        setupMovieSelection();
        setupDetailSelection();
    }


    private void setupMovieSelection() {
        aMoviesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldMovie, newMovie) -> {
            aSelectedMovie = newMovie;
            loadPairsForMovie(newMovie);
        });
    }


    private void loadPairsForMovie(Movie movie) {
        aDetailsListView.getItems().clear();
        aShowTimeRoom.clear();
        aSelectedShowtime = null;
        aSelectedShowroom = null;

        if (movie == null) return;

        List<Movie> movies = aMovieManager.getMovies();
        List<Showtime> showtimes = aShowtimeManager.getShowtimes();
        List<Showroom> showrooms = aShowroomManager.getShowrooms();

        for (int i = 0; i < movies.size(); i++) {
            if (!movies.get(i).getName().equals(movie.getName()))
                continue;

            Showtime showtime = showtimes.get(i);
            Showroom sr = showrooms.get(i);

            aShowTimeRoom.put(showtime, sr);

            aDetailsListView.getItems().add(
                    showtime.toString() +
                            "  |  Room " + sr.getRoomNumber() +
                            "  |  Seats Left: " + sr.getCapacity() /** To be calculated*/
            );
        }
    }


    private void setupDetailSelection() {
        aDetailsListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int index = newVal.intValue();

            if (index < 0 || index >= aShowTimeRoom.size()) {
                aSelectedShowtime = null;
                aSelectedShowroom = null;
                return;
            }

            Showtime st = aShowTimeRoom.keySet().stream().toList().get(index);
            Showroom sr = aShowTimeRoom.get(st);

            aSelectedShowtime = st;
            aSelectedShowroom = sr;
        });
    }



    @FXML
    private void onBookButtonClick() {

        if (aSelectedMovie == null) {
            AlertHelper.showErrorAlert("Booking Error", "Movie Missing", "Please select a movie first.");
            return;
        }

        if (aSelectedShowtime == null || aSelectedShowroom == null) {
            AlertHelper.showErrorAlert("Booking Error", "Selection Missing", "Select a showtime and showroom.");
            return;
        }

        /** Check seat availability
        if (aSelectedShowroom.getRemainingSeats() <= 0) {
            AlertHelper.showErrorAlert("Sold Out", "No seats left", "Please choose a different showtime.");
            return;
        }
        */
        // Reduce seat
        //aSelectedShowroom.reduceOneSeat();

        // Generate Ticket
        Ticket ticket = new Ticket(
                UUID.randomUUID().toString(),
                aSelectedMovie.getName(),
                aSelectedShowtime.toString(),  // Showtime as String  // to be formatted
                LocalDate.now()
        );

        // Show ticket popup
        AlertHelper.showInfoAlert("Booking Confirmed", "Your ticket is ready!", ticket.toString());

        // Refresh UI
        loadPairsForMovie(aSelectedMovie);
    }

    // Method to calculate remaining seats in Showroom at a given Showtime for a Movie
    public int calculateRemainingSeats(Showroom showroom, Showtime showtime) {
        // For simplicity, we assume the showroom's capacity is the total seats available
        // In a real application, you would check booked tickets for that showtime
        // and subtract from the showroom's capacity
        int availableSeats = /** showroom.getCapacity() - showroom.getBookedSeats();  // to be done*/ showroom.getCapacity();
        return availableSeats;
    }
}
