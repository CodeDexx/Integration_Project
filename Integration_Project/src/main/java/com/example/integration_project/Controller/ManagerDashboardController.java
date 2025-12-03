package com.example.integration_project.Controller;

import java.io.IOException;
import java.util.Objects;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Helpers.ImportHelper;
import com.example.integration_project.Model.Movie;
import com.example.integration_project.Model.MovieManager;
import com.example.integration_project.Model.Showroom;
import com.example.integration_project.Model.ShowroomManager;
import com.example.integration_project.Model.Showtime;
import com.example.integration_project.Model.ShowtimeManager;
import com.example.integration_project.Model.Manager;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Controller for manager-dashboard.fxml (Manager Dashboard) in the Movie Theater Management System.
 *
 * <p>This controller coordinates the main manager UI where Movies / Showtimes / Showrooms
 * are listed. It supports switching views between entity types, selecting items, and
 * launching the shared Add/Edit dialog implemented by {@link AddEditController}.</p>
 *
 * <p>Important behaviour:
 * <ul>
 *   <li>The {@code aListView} stores domain objects directly (Movie / Showtime / Showroom).</li>
 *   <li>Opening Add/Edit dialogs passes the managers and the selected object (or null for Add)
 *       to .</li>
 *   <li>After any dialog closes the list view is refreshed automatically.</li>
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

    /// TODO Ticket number tickets sold per showtime and per movie
    /// Consult the number of tickets sold by showtime and by movie
    public enum DashboardView {
        MOVIES,
        SHOWTIME,
        SHOWROOMS,
        TICKETS
    }
    /** Label that displays the page title (set dynamically to the current view) */
    @FXML
    private Label aPageTitle;

    /** Logout button defined in FXML */
    @FXML
    private Button aLogoutButton;

    /** Primary list view that displays Movies, Showtimes, or Showrooms (stores objects) */
    @FXML
    private ListView<Object> aListView;

    /** The currently logged-in manager (maybe null if not logged in) */
    private Manager aManager;

    /** Stage reference used for closing the window on logout */
    private Stage aStage;

    /** The current view name: "Movies", "Showtime", or "Showrooms" */
    private DashboardView aCurrentView = DashboardView.MOVIES;

    /** The currently selected item from the list view (a Movie, Showtime, or Showroom) */
    private Object aSelectedItem;

    /** Managers for each domain type; instantiated here but can be injected if desired */
    private MovieManager aMovieManager;//= MovieManager.getMovieManagerInstance();
    private ShowtimeManager aShowtimeManager; //= ShowtimeManager.getShowtimeManagerInstance();
    private ShowroomManager aShowroomManager;// = ShowroomManager.getShowroomManagerInstance();

    /**
     * JavaFX initialize method. Sets up selection listener and refreshes initial view.
     * This method is called automatically by the FXMLLoader after FXML fields are injected.
     */
    @FXML
    private void initialize() {
        ImportHelper.loadMovies();
        ImportHelper.loadShowrooms();
        aMovieManager = MovieManager.getMovieManagerInstance();
        aShowroomManager = ShowroomManager.getShowroomManagerInstance();
        aShowtimeManager = ShowtimeManager.getShowtimeManagerInstance();

        setupListSelectionListener();
        refreshView(aCurrentView);
    }

    /**
     * Sets the current manager (logged-in user). Call this from the code that opens the dashboard.
     *
     * @param pManager the Manager who is logged in
     */
    public void setManager(Manager pManager) {
        this.aManager = pManager;
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
     * @param pViewName the view to display ("Movies", "Showtime", "Showrooms")
     */
    private void refreshView(DashboardView pViewName) {
        // update title label where present
        if (Objects.nonNull(aPageTitle)) {
            aPageTitle.setText(pViewName.toString());
        }

        aListView.getItems().clear();
        aSelectedItem = null;

        switch (pViewName) {
            case MOVIES -> {
                if (!aMovieManager.getMovies().isEmpty()) {
                    aListView.getItems().addAll(aMovieManager.getMovies());
                } else {
                    // show friendly placeholder if empty
                    aListView.getItems().add("No movies available. Click Add to create one.");
                }
            }
            case SHOWTIME -> {
                if (!aShowtimeManager.getShowtimes().isEmpty()) {
                    ImportHelper.loadShowtime(aMovieManager.getMovies(),aShowroomManager.getShowrooms());

                    aListView.getItems().addAll(aShowtimeManager.getShowtimes());
                } else {
                    aListView.getItems().add("No showtimes available. Click Add to create one.");
                }
            }
            case SHOWROOMS-> {
                if (!aShowroomManager.getShowrooms().isEmpty()) {
                    aListView.getItems().addAll(aShowroomManager.getShowrooms());
                } else {
                    aListView.getItems().add("No showrooms available. Click Add to create one.");
                }
            }
//            case TICKETS -> {
//                if (!aTicketManager.getShowrooms().isEmpty()) {
//                    aListView.getItems().addAll(aTicketManager.getShowrooms());
//                } else {
//                    aListView.getItems().add("No tickets available. Click Add to create one.");
//                }
//            }
            default -> {
                AlertHelper.showErrorAlert("View Error", "Unknown View", "Unrecognized view: " + pViewName);
            }
        }
    }

    /**
     * Switch to Movies view.
     *
     * @param pEvent the ActionEvent triggered by the Movies button
     */
    @FXML
    private void onMoviesButtonClick(ActionEvent pEvent) {
        this.aCurrentView = DashboardView.MOVIES;
        refreshView(this.aCurrentView);
    }

    /**
     * Switch to Showtime view.
     *
     * @param pEvent the ActionEvent triggered by the Showtime button
     */
    @FXML
    private void onShowtimeButtonClick(ActionEvent pEvent) {
        this.aCurrentView = DashboardView.SHOWTIME;
        refreshView(this.aCurrentView);
    }

    /**
     * Switch to Ticket view.
     *
     * @param pEvent the ActionEvent triggered by the Ticket button
     */
    @FXML
    private void onTicketsButtonClick(ActionEvent pEvent) {
        this.aCurrentView = DashboardView.TICKETS;
        refreshView(this.aCurrentView);
    }

    /**
     * Switch to Showrooms view.
     *
     * @param pEvent the ActionEvent triggered by the Showrooms button
     */
    @FXML
    private void onShowroomsButtonClick(ActionEvent pEvent) {
        this.aCurrentView = DashboardView.SHOWROOMS;
        refreshView(this.aCurrentView);
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
                case SHOWROOMS -> openAddEditView(AddEditController.FormMode.ADD_ROOM, null);
                default -> AlertHelper.showErrorAlert("Add Error", "Invalid View", "Cannot add to view: " + aCurrentView);
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
                case SHOWROOMS -> {
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
                case SHOWTIME-> {
                    if (aSelectedItem instanceof Showtime showtime) {
                        aShowtimeManager.removeShowtime(showtime);
                        AlertHelper.showInfoAlert("Delete", "Showtime Deleted", "Showtime has been deleted successfully.");
                    } else {
                        AlertHelper.showErrorAlert("Type Error", "Invalid selection", "Selected item is not a Showtime.");
                    }
                }
                case SHOWROOMS -> {
                    if (aSelectedItem instanceof Showroom room) {
                        aShowroomManager.removeShowroom(room);
                        AlertHelper.showInfoAlert("Delete", "Showroom Deleted", "Showroom has been deleted successfully.");
                    } else {
                        AlertHelper.showErrorAlert("Type Error", "Invalid selection", "Selected item is not a Showroom.");
                    }
                }
//                case TICKETS -> {
//                    if (aSelectedItem instanceof Showroom room) {
//                        aTicketManager.removeShowroom(room);
//                        AlertHelper.showInfoAlert("Delete", "Showroom Deleted", "Showroom has been deleted successfully.");
//                    } else {
//                        AlertHelper.showErrorAlert("Type Error", "Invalid selection", "Selected item is not a Showroom.");
//                    }
//                }
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
     * @param pMode the FormMode describing the operation (add/edit + entity type)
     * @param pObject the object to edit, or null for Add
     * @throws IOException if the FXML cannot be loaded
     */
    private void openAddEditView(AddEditController.FormMode pMode, Object pObject) throws IOException {
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
