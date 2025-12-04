package com.example.integration_project.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

import com.example.integration_project.Helpers.*;
import com.example.integration_project.Model.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * <h1>ClientDashboardController</h1>
 * Controller for the client-dashboard.fxml view.
 * <p>Manages the client-facing interface allowing users to view available movies,
 * select showtime, and book tickets.</p>
 *
 * <p>It uses synchronized internal lists ({@code aShowtimeList} and {@code aShowroomList})
 * to correctly map the selected detail string in {@code aDetailsListView} back to its
 * corresponding {@link Showtime} and {@link Showroom} objects.</p>
 *
 * @author Dieudonné
 * @version 1.0
 * @see MovieManager
 * @see ShowtimeManager
 * @see TicketManager
 */
public class ClientDashboardController {

    /** The ListView displaying available {@link Movie} objects. */
    @FXML
    private ListView<Movie> aMoviesListView;

    /** The ListView displaying formatted showtime details (time, room, seats left) for the selected movie. */
    @FXML
    private ListView<String> aDetailsListView;

    /** The button used to log out and close the dashboard window. */
    @FXML
    private Button aLogoutButton;

    /** The currently selected {@link Movie} object from the movies list. */
    private Movie aSelectedMovie;

    /** The currently selected {@link Showtime} object (paired with the selected detail string). */
    private Showtime aSelectedShowtime;

    /** The {@link Showroom} object corresponding to the selected showtime. */
    private Showroom aSelectedShowroom;

    /** Ordered list of {@link Showtime} objects corresponding to the items displayed in {@code aDetailsListView}. */
    private final List<Showtime> aShowtimeList = new ArrayList<>();

    /** Ordered list of {@link Showroom} objects corresponding to the items displayed in {@code aDetailsListView}. */
    private final List<Showroom> aShowroomList = new ArrayList<>();

    /** The stage/window hosting this controller's view. Used primarily for closing the window. */
    private Stage aStage;

    /**
     * JavaFX initialize method.
     * Called automatically by the FXMLLoader after FXML fields are injected.
     * Initializes the movie list view and sets up selection listeners.
     */
    @FXML
    private void initialize() {
        try{
            // Populates the movie list with all available movies
            aMoviesListView.setItems(MovieManager.getMovies());

            setupMovieSelection();
            setupDetailSelection();
        }catch (Exception e){
            AlertHelper.showErrorAlert("Initialize Error", "Failed to initialize View", e.getMessage());
        }
    }

    /**
     * Sets up a listener for the movie selection.
     * When a new movie is selected, it triggers {@code loadPairsForMovie} to update the details list.
     */
    private void setupMovieSelection() {
        aMoviesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldMovie, newMovie) -> {
            aSelectedMovie = newMovie;
            loadPairsForMovie(newMovie);
        });
    }

    /**
     * Populates the details list with showtime and showroom information for the given movie.
     * It clears and repopulates the synchronized lists ({@code aShowtimeList}, {@code aShowroomList}).
     *
     * @param movie The {@link Movie} for which to load showtime.
     */
    private void loadPairsForMovie(Movie movie) {
        try {
            aDetailsListView.getItems().clear();
            aShowtimeList.clear();
            aShowroomList.clear();
            aSelectedShowtime = null;
            aSelectedShowroom = null;

            if (movie == null) return;

            // Iterate through all showtime to find matches for the selected movie
            for (Showtime st : ShowtimeManager.getShowtime()) {
                if (st.getMovie() == movie) {  // compare object references
                    Showroom room = st.getShowroom();

                    // Add to synchronized lists
                    aShowtimeList.add(st);
                    aShowroomList.add(room);

                    int remaining = calculateRemainingSeats(room, st);

                    // Add formatted string to details list
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
        }catch (Exception e){
            AlertHelper.showErrorAlert("Loading Showtimes Error", "Failed to load Showtimes through loadPairsForMovies", e.getMessage());
        }
    }

    /**
     * Sets up a listener for the showtime/detail selection.
     * It uses the selected index to retrieve the corresponding {@link Showtime} and {@link Showroom}
     * objects from the internal ordered lists, setting {@code aSelectedShowtime} and {@code aSelectedShowroom}.
     */
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

    /**
     * Handler for the Book button.
     * Validates movie and showtime selections, books a seat in the showroom, generates a new {@link Ticket},
     * adds the ticket to the {@link TicketManager}, and updates the UI.
     */
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

            // Book seat (logic delegated to Showroom)
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

            // Refresh UI to show updated seat count
            loadPairsForMovie(aSelectedMovie);

        } catch (Exception e) {
            AlertHelper.showErrorAlert("Booking Error", e.getMessage(), e.toString());
        }
    }

    /**
     * Calculates the number of remaining available seats for a specific showtime in a showroom.
     *
     * @param showroom The {@link Showroom} object where the show is held.
     * @param showtime The specific {@link Showtime} event.
     * @return The number of seats remaining (Showroom Capacity - Booked Seats).
     */
    public int calculateRemainingSeats(Showroom showroom, Showtime showtime) {
        int booked = showroom.getBookedSeats(showtime);
        return showroom.getCapacity() - booked;
    }

    /**
     * Closes the dashboard window (logout).
     */
    @FXML
    private void onLogoutButtonClick() {
        try {
            // Retrieve the stage from the button's scene
            aStage = (Stage) aLogoutButton.getScene().getWindow();
            aStage.close();
        }catch (Exception e){
            AlertHelper.showErrorAlert("Stage Error", "Failed to load the stage in Logout method", e.getMessage());
        }
    }
}
