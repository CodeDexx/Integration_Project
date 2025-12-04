package com.example.integration_project.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Model.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Controller for manager-dashboard.fxml (Manager Dashboard) in the Movie Theater Management System.
 *
 * <p>This controller coordinates the main manager UI where Movies / Showtime / Showroom
 * are listed. It supports switching views between entity types, selecting items, and
 * launching the shared Add/Edit dialog implemented by {@link AddEditController}.</p>
 *
 * <p>Important behaviour:
 * <ul>
 * <li>The {@code aListView} stores domain objects directly (Movie / Showtime / Showroom).</li>
 * <li>Opening Add/Edit dialogs passes the managers and the selected object (or null for Add)
 * to .</li>
 * <li>After any dialog closes the list view is refreshed automatically.</li>
 * </ul>
 * </p>
 *
 * @author Dieudonné
 * @version 1.0
 * @see AddEditController
 * @see MovieManager
 * @see ShowtimeManager
 * @see ShowroomManager
 */

public class ManagerDashboardController {

    /**
     * Enumeration defining the different views/tabs available in the dashboard.
     */
    public enum DashboardView {
        MOVIES,
        SHOWTIME,
        SHOWROOM,
        TICKETS
    }

    /**
     * Label that displays the page title (set dynamically to the current view)
     */
    @FXML
    private Label aPageTitle;

    /**
     * Logout button defined in FXML
     */
    @FXML
    private Button aLogoutButton;

    /**
     * Primary list view that displays Movies, Showtime, or Showroom (stores objects)
     */
    @FXML
    private ListView<Object> aListView;

    /**
     * Stage reference used for closing the window on logout
     */
    private Stage aStage;

    /**
     * The current view name: "Movies", "Showtime", "Showroom", or "Tickets"
     */
    private DashboardView aCurrentView = DashboardView.MOVIES;

    /**
     * The currently selected item from the list view (a Movie, Showtime, or Showroom)
     */
    private Object aSelectedItem;

    /**
     * Manager for handling Movie entities.
     */
    private MovieManager aMovieManager;

    /**
     * Manager for handling Showtime entities.
     */
    private ShowtimeManager aShowtimeManager;

    /**
     * Manager for handling Showroom entities.
     */
    private ShowroomManager aShowroomManager;

    /**
     * Local copy of the complete list of all tickets for reporting/counting.
     */
    private List<Ticket> aTicketList;

    /**
     * JavaFX initialize method. Sets up selection listener and refreshes initial view.
     * This method is called automatically by the FXMLLoader after FXML fields are injected.
     */
    @FXML
    private void initialize() {
        try {

            aMovieManager = MovieManager.getMovieManagerInstance();
            aShowroomManager = ShowroomManager.getShowroomManagerInstance();
            aShowtimeManager = ShowtimeManager.getShowtimeManagerInstance();

            aTicketList = TicketManager.getInstance().getTickets();

            refreshView(aCurrentView);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error" + e.getMessage());
        }
    }

    /**
     * Sets up a listener on the ListView selection model so that {@code aSelectedItem}
     * always contains the currently selected object (or null).
     */
    private void setupListSelectionListener() {
        aListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            this.aSelectedItem = newVal;
        });
    }

    /**
     * Refreshes the ListView contents according to the currently selected view.
     * Uses the domain managers to retrieve the current collections.
     *
     * @param pViewName the view to display (e.g., {@code DashboardView.MOVIES})
     */
    private void refreshView(DashboardView pViewName) {
        // update title label where present
        if (Objects.nonNull(aPageTitle)) {
            aPageTitle.setText(pViewName.toString());
        }
        // Local variable to escape from being cleared
        Object aSelectedItem = aListView.getSelectionModel().getSelectedItem();
        setupListSelectionListener();
        aListView.getItems().clear();

        switch (pViewName) {
            case MOVIES -> {
                // CORRECTED: Use aMovieManager.getMovies() instead of static method
                if (!MovieManager.getMovies().isEmpty()) {
                    aListView.getItems().clear();
                    aListView.getItems().addAll(MovieManager.getMovies());
                } else {
                    AlertHelper.showErrorAlert("Movies Error", "Add Movies", "No Movies exist for this Movie Theater. \n Click Add to create some!");
                }
            }
            case SHOWTIME -> {

                // Must select a movie first
                if (!(aSelectedItem instanceof Movie selectedMovie)) {
                    // No selected Movie; back to the Movie view
                    aListView.getItems().add("Select a Movie please");

                    onMoviesButtonClick();

                    return;
                }

                aListView.getItems().clear();

                // CORRECTED: Use aShowtimeManager.getShowtime() instead of static method
                ObservableList<Showtime> allShowtime = ShowtimeManager.getShowtime();
                List<Showtime> filtered = new ArrayList<>();

                for (Showtime st : allShowtime) {
                    if (st.getMovie() == selectedMovie) {
                        filtered.add(st);
                    }
                }

                if (filtered.isEmpty()) {
                    return;
                }
                aListView.getItems().addAll(filtered);
            }
            case SHOWROOM -> {
                aListView.getItems().clear();

                // User must have selected a showtime
                if (aSelectedItem instanceof Showtime selectedShowtime) {

                    Showroom room = selectedShowtime.getShowroom();

                    if (room != null) {
                        aListView.getItems().add(room);
                    } else {
                        aListView.getItems().add("No showroom assigned.");
                        AlertHelper.showErrorAlert("Showroom Error",
                                "Missing Showroom",
                                "This showtime has no showroom assigned.");
                    }

                } else {
                    aListView.getItems().add("Select a showtime first.");
                    AlertHelper.showErrorAlert("Selection Required",
                            "Select a Showtime",
                            "You need to select a showtime before viewing its showroom.");
                }
            }
            case TICKETS -> {
                aListView.getItems().clear();

                // CORRECTED: Use aMovieManager.getMovies() instead of static method
                if (!MovieManager.getMovies().isEmpty()) {
                    // CORRECTED: Use aMovieManager.getMovies() instead of static method
                    for (Movie movie : MovieManager.getMovies()) {
                        long soldByMovie = TicketManager.countByMovie(aTicketList, movie.getName());
                        aListView.getItems().add(
                                "Movie: " + movie.getName() + " | Tickets Sold: " + soldByMovie
                        );

                        // CORRECTED: Use aShowtimeManager.getShowtime() instead of static method
                        for (Showtime st : ShowtimeManager.getShowtime()) {
                            if (st.getMovie() == movie) {
                                long soldByShowtime = TicketManager.countByShowtime(aTicketList, st.toString());
                                aListView.getItems().add(
                                        "  Showtime: " + st.getShowtime() + " | Tickets Sold: " + soldByShowtime
                                );
                            }
                        }
                    }
                } else {
                    aListView.getItems().add("No movies or tickets available.");
                }
            }

            default -> {
                AlertHelper.showErrorAlert("View Error", "Unknown View", "Unrecognized view: " + pViewName);
            }
        }
    }

    /**
     * Switch to Movies view.
     *
     */
    @FXML
    private void onMoviesButtonClick() {
        try {
            this.aCurrentView = DashboardView.MOVIES;
            refreshView(this.aCurrentView);
        }catch (Exception e){
            AlertHelper.showErrorAlert("Movie View Error", "Failed to load Movie View", e.getMessage());
        }
    }

    /**
     * Switch to Showtime view.
     *
     * @param pEvent the ActionEvent triggered by the Showtime button
     */
    @FXML
    private void onShowtimeButtonClick(ActionEvent pEvent) {
        try {
            this.aCurrentView = DashboardView.SHOWTIME;
            refreshView(this.aCurrentView);
        }catch (Exception e){
        AlertHelper.showErrorAlert("ShowtimeView Error", "Failed to load showtime View", e.getMessage());
    }
    }

    /**
     * Switch to Ticket view.
     *
     * @param pEvent the ActionEvent triggered by the Ticket button
     */
    @FXML
    private void onTicketsButtonClick(ActionEvent pEvent) {
        try {
            this.aCurrentView = DashboardView.TICKETS;
            refreshView(this.aCurrentView);
        }catch (Exception e){
        AlertHelper.showErrorAlert("Ticket View Error", "Failed to load Ticket View", e.getMessage());
    }
    }

    /**
     * Switch to Showroom view.
     *
     */
    @FXML
    private void onShowroomButtonClick() {
        try{
            this.aCurrentView = DashboardView.SHOWROOM;
            refreshView(this.aCurrentView);
        }catch (Exception e){
            AlertHelper.showErrorAlert("Showroom View Error", "Failed to load showrooms View", e.getMessage());
        }

    }

    /**
     * Handler for the Add button.
     *
     * <p>This method opens the shared add-edit dialog in the appropriate {@code FormMode}
     * with a null object (indicating Add mode). After the dialog closes the list is refreshed.</p>
     */
    @FXML
    private void onAddButtonClick() {
        try {
            switch (this.aCurrentView) {
                case MOVIES -> openAddEditView(AddEditController.FormMode.ADD_MOVIE, null);
                case SHOWTIME -> openAddEditView(AddEditController.FormMode.ADD_SHOWTIME, null);
                case SHOWROOM -> openAddEditView(AddEditController.FormMode.ADD_ROOM, null);
                default ->
                        AlertHelper.showErrorAlert("Add Error", "Invalid View", "Cannot add to view: " + aCurrentView);
            }
        } catch (Exception e) {
            AlertHelper.showErrorAlert("Add Error", "Failed to open dialog", e.getMessage());
        }
    }

    /**
     * Handler for the Update button.
     *
     * <p>If no selection is made an error is shown. Otherwise, opens the shared add-edit dialog
     * in the appropriate edit {@code FormMode} with the selected object to pre-fill fields.</p>
     */
    @FXML
    private void onUpdateButtonClick() {
        if (this.aSelectedItem == null) {
            AlertHelper.showErrorAlert("Update Error", "Nothing Selected", "Please select an item to update.");
            return;
        }

        try {
            switch (this.aCurrentView) {
                case MOVIES -> {
                    if (this.aSelectedItem instanceof Movie movie) {
                        openAddEditView(AddEditController.FormMode.EDIT_MOVIE, movie);
                    } else {
                        AlertHelper.showErrorAlert("Type Error", "Invalid selection", "Selected item is not a Movie.");
                    }
                }
                case SHOWTIME -> {
                    if (this.aSelectedItem instanceof Showtime showtime) {
                        openAddEditView(AddEditController.FormMode.EDIT_SHOWTIME, showtime);
                    } else {
                        AlertHelper.showErrorAlert("Type Error", "Invalid selection", "Selected item is not a Showtime.");
                    }
                }
                case SHOWROOM -> {
                    if (this.aSelectedItem instanceof Showroom room) {
                        openAddEditView(AddEditController.FormMode.EDIT_ROOM, room);
                    } else {
                        AlertHelper.showErrorAlert("Type Error", "Invalid selection", "Selected item is not a Showroom.");
                    }
                }
                default -> AlertHelper.showErrorAlert("Update Error", "Invalid View", aCurrentView.toString());
            }
        } catch (Exception e) {
            AlertHelper.showErrorAlert("Update Error", "Failed to open dialog", e.getMessage());
        }
    }

    /**
     * Handler for the Delete button (wired in FXML).
     *
     * <p>Validates the selection type matches the current view, calls the proper manager delete method,
     * and refreshes the view. Shows alerts on success or failure.</p>
     */
    @FXML
    private void onDeleteButtonClick() {
        if (this.aSelectedItem == null) {
            AlertHelper.showErrorAlert("Delete Error", "Nothing Selected", "Please select an item to delete.");
            return;
        }

        try {
            switch (this.aCurrentView) {
                case MOVIES -> {
                    if (aSelectedItem instanceof Movie movie) {
                        aMovieManager.removeMovie(movie);
                        AlertHelper.showInfoAlert("Delete", "Movie Deleted", "Movie has been deleted successfully.");
                    } else {
                        AlertHelper.showErrorAlert("Type Error", "Invalid selection", "Selected item is not a Movie.");
                    }
                }
                case SHOWTIME -> {
                    if (aSelectedItem instanceof Showtime showtime) {
                        aShowtimeManager.removeShowtime(showtime);
                        AlertHelper.showInfoAlert("Delete", "Showtime Deleted", "Showtime has been deleted successfully.");
                    } else {
                        AlertHelper.showErrorAlert("Type Error", "Invalid selection", "Selected item is not a Showtime.");
                    }
                }
                case SHOWROOM -> {
                    if (aSelectedItem instanceof Showroom room) {
                        aShowroomManager.removeShowroom(room);
                        AlertHelper.showInfoAlert("Delete", "Showroom Deleted", "Showroom has been deleted successfully.");
                    } else {
                        AlertHelper.showErrorAlert("Type Error", "Invalid selection", "Selected item is not a Showroom.");
                    }
                }
                default -> AlertHelper.showErrorAlert("Delete Error", "Invalid View", aCurrentView.toString());
            }
        } catch (Exception e) {
            AlertHelper.showErrorAlert("Delete Error", "Failed to delete item", e.getMessage());
        } finally {
            refreshView(this.aCurrentView);
        }
    }

    /**
     * Opens the shared Add/Edit dialog (add-edit-view.fxml) and initializes it with the
     * appropriate FormMode, selected object (or null for add), and the three managers.
     *
     * <p>The AddEditController is expected to expose the method:
     * {@code initializeForm(FormMode, Object, MovieManager, ShowtimeManager, ShowroomManager)}</p>
     *
     * @param pMode   the FormMode describing the operation (add/edit + entity type)
     * @param pObject the object to edit, or null for Add
     * @throws IOException if the FXML cannot be loaded
     */
    private void openAddEditView(AddEditController.FormMode pMode, Object pObject) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integration_project/add-edit-view.fxml"));
            Parent root = loader.load();

            // Retrieve controller and call initializeForm(...)
            Object controller = loader.getController();
            if (controller instanceof AddEditController addEdit) {
                addEdit.initializeForm(pMode, pObject, aMovieManager, aShowtimeManager, aShowroomManager);
            } else {
                // Defensive: controller is not the expected type
                throw new IllegalStateException("add-edit-view.fxml controller is not AddEditController");
            }

            Stage stage = new Stage();
            stage.setTitle(switch (pMode) {
                case ADD_MOVIE -> "Add Movie";
                case EDIT_MOVIE -> "Edit Movie";
                case ADD_SHOWTIME -> "Add Showtime";
                case EDIT_SHOWTIME -> "Edit Showtime";
                case ADD_ROOM -> "Add Room";
                case EDIT_ROOM -> "Edit Room";
            });

            stage.setScene(new Scene(root));
            stage.showAndWait(); // modal - wait for the user to close

            // After dialog closes refresh the current view so new/updated data appears
            refreshView(this.aCurrentView);
        }catch (Exception e) {
            AlertHelper.showErrorAlert("Add or Edit Error", "Failed to open dialog", e.getMessage());
        }
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
