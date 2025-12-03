package com.example.integration_project.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.example.integration_project.Helpers.*;
import com.example.integration_project.Model.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ClientDashboardController {

    @FXML
    private ListView<Movie> aMoviesListView;

    @FXML
    private ListView<String> aDetailsListView;

    @FXML
    private Button aLogoutButton;

    private MovieManager aMovieManager;
    private ShowtimeManager aShowtimeManager;
    private ShowroomManager aShowroomManager;

    private Movie aSelectedMovie;
    private Showtime aSelectedShowtime;
    private Showroom aSelectedShowroom;

    /** Ordered lists to track showtime and showroom correctly */
    private final List<Showtime> aShowtimeList = new ArrayList<>();
    private final List<Showroom> aShowroomList = new ArrayList<>();

    private Stage aStage;

    @FXML
    private void initialize() {
        ImportHelper.loadMovies();
        ImportHelper.loadShowroom();

        aMovieManager = MovieManager.getMovieManagerInstance();
        aShowroomManager = ShowroomManager.getShowroomManagerInstance();
        aShowtimeManager = ShowtimeManager.getShowtimeManagerInstance();
        aMoviesListView.setItems(aMovieManager.getMovies());
        ImportHelper.loadShowtime(aMovieManager.getMovies(), aShowroomManager.getShowroom());

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
        aShowtimeList.clear();
        aShowroomList.clear();
        aSelectedShowtime = null;
        aSelectedShowroom = null;

        if (movie == null) return;

        for (Showtime st : aShowtimeManager.getShowtime()) {
            if (st.getMovie() == movie) {  // compare object references
                Showroom room = st.getShowroom();
                aShowtimeList.add(st);
                aShowroomList.add(room);

                int remaining = calculateRemainingSeats(room, st);

                aDetailsListView.getItems().add(
                        st.getShowtime().toLocalDate() + " " +
                                st.getShowtime().toLocalTime() +
                                " | Room " + room.getRoomNumber() +
                                " | Seats Left: " + remaining
                );
            }
        }

        if (aDetailsListView.getItems().isEmpty()) {
            aDetailsListView.getItems().add("No Showtime available for this movie.");
        }
    }

    private void setupDetailSelection() {
        aDetailsListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int index = newVal.intValue();
            if (index < 0 || index >= aShowtimeList.size()) {
                aSelectedShowtime = null;
                aSelectedShowroom = null;
                return;
            }

            aSelectedShowtime = aShowtimeList.get(index);
            aSelectedShowroom = aShowroomList.get(index);
        });
    }

    @FXML
    private void onBookButtonClick() {
        try {
            if (aSelectedMovie == null) {
                AlertHelper.showErrorAlert("Booking Error", "Movie Missing", "Please select a movie first.");
                return;
            }

            if (aSelectedShowtime == null || aSelectedShowroom == null) {
                AlertHelper.showErrorAlert("Booking Error", "Selection Missing", "Select a showtime and showroom.");
                return;
            }

            // Book seat
            aSelectedShowroom.bookSeat(aSelectedShowtime);

            // Generate Ticket
            Ticket ticket = new Ticket(
                    UUID.randomUUID().toString(),
                    aSelectedMovie.getName(),
                    aSelectedShowtime.toString(),
                    LocalDateTime.now()
            );

            // Add the ticket to TicketManager
            TicketManager.getInstance().addTicket(ticket);

            // Show ticket notification
            AlertHelper.showInfoAlert("Booking Confirmed", "Your ticket is ready!", ticket.toString());

            // Refresh UI
            loadPairsForMovie(aSelectedMovie);

        } catch (Exception e) {
            AlertHelper.showErrorAlert("Booking Error", e.getMessage(), e.toString());
        }
    }

    public int calculateRemainingSeats(Showroom showroom, Showtime showtime) {
        int booked = showroom.getBookedSeats(showtime);
        return showroom.getCapacity() - booked;
    }

    /**
     * Closes the dashboard window (logout).
     */
    @FXML
    private void onLogoutButtonClick() {
        aStage = (Stage) aLogoutButton.getScene().getWindow();
        aStage.close();
    }
}
