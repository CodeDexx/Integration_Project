package com.example.integration_project.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.example.integration_project.Model.Movie;
import com.example.integration_project.Model.MovieManager;
import com.example.integration_project.Model.Showroom;
import com.example.integration_project.Model.ShowroomManager;
import com.example.integration_project.Model.Showtime;
import com.example.integration_project.Model.ShowtimeManager;

/**
 * Controller for the add-edit-view.fxml
 * Handles adding and editing of Movies, Showtimes, and Showrooms.
 * Uses a single view with conditional logic based on FormMode.
 * 
 * @author Ian
 * @version 1.0
 */
public class AddEditController {
    
    /**
     * Enum representing the different modes this controller can operate in.
     */
    public enum FormMode {
        ADD_MOVIE, EDIT_MOVIE,
        ADD_SHOWTIME, EDIT_SHOWTIME,
        ADD_ROOM, EDIT_ROOM
    }
    
    private FormMode aMode;
    private Object aCurrentObject; // Can be Movie, Showtime, or Showroom
    
    private MovieManager aMovieManager;
    private ShowtimeManager aShowtimeManager;
    private ShowroomManager aShowroomManager;
    
    @FXML
    private Label aTitleLabel;
    
    @FXML
    private TextField aNameTextField;
    
    @FXML
    private ChoiceBox<Movie> aMovieChoiceBox;

    @FXML
    private TextField aCapacityTextField;
    
    @FXML
    private DatePicker aDatePicker;
    
    @FXML
    private ChoiceBox<Showtime> aShowtimeChoiceBox;
    
    @FXML
    private ChoiceBox<Showroom> aRoomChoiceBox;
    
    @FXML
    private Button aSaveButton;
    
    @FXML
    private Button aCancelButton;

    @FXML private Label aNameLabel;

    @FXML private Label aMovieLabel;

    @FXML private Label aDateLabel;

    @FXML private Label aShowtimeLabel;

    @FXML private Label aRoomLabel;

    @FXML private Label aCapacityLabel;
    
    @FXML
    private TextField aTimeTextField;
    
    @FXML
    private Label aTimeLabel;
    
    /**
     * Initializes the controller with the necessary data and managers.
     * This method should be called after the FXML is loaded but before the stage is shown.
     * 
     * @param pMode the FormMode indicating what operation to perform
     * @param pCurrentObject the object being edited (null if adding new)
     * @param pMovieManager the MovieManager instance for movie operations
     * @param pShowtimeManager the ShowtimeManager instance for showtime operations
     * @param pShowroomManager the ShowroomManager instance for showroom operations
     */
    public void initializeForm(FormMode pMode, Object pCurrentObject, MovieManager pMovieManager, ShowtimeManager pShowtimeManager, ShowroomManager pShowroomManager) {
        this.aMode = pMode;
        this.aCurrentObject = pCurrentObject;
        this.aMovieManager = pMovieManager;
        this.aShowtimeManager = pShowtimeManager;
        this.aShowroomManager = pShowroomManager;
        
        // Setup the UI based on the mode
        setupUI();
        populateChoiceBoxes();
        populateFields();
    }
    
    /**
     * Sets up the UI by showing/hiding fields based on the current mode.
     */
    private void setupUI() {
        switch (aMode) {
            case ADD_MOVIE:
                aTitleLabel.setText("Add Movie");
                aNameTextField.setVisible(true);
                aMovieChoiceBox.setVisible(false);
                aDatePicker.setVisible(false);
                aTimeTextField.setVisible(false);
                aShowtimeChoiceBox.setVisible(false);
                aRoomChoiceBox.setVisible(false);
                aNameLabel.setVisible(true);
                aMovieLabel.setVisible(false);
                aDateLabel.setVisible(false);
                aTimeLabel.setVisible(false);
                aShowtimeLabel.setVisible(false);
                aRoomLabel.setVisible(false);
                aCapacityLabel.setVisible(false);
                aCapacityTextField.setVisible(false);
                break;
            case EDIT_MOVIE:
                aTitleLabel.setText("Edit Movie");
                aNameTextField.setVisible(true);
                aMovieChoiceBox.setVisible(false);
                aDatePicker.setVisible(false);
                aTimeTextField.setVisible(false);
                aShowtimeChoiceBox.setVisible(false);
                aRoomChoiceBox.setVisible(false);
                aNameLabel.setVisible(true);
                aMovieLabel.setVisible(false);
                aDateLabel.setVisible(false);
                aTimeLabel.setVisible(false);
                aShowtimeLabel.setVisible(false);
                aRoomLabel.setVisible(false);
                aCapacityLabel.setVisible(false);
                aCapacityTextField.setVisible(false);
                break;
            case ADD_SHOWTIME:
                aTitleLabel.setText("Add Showtime");
                aNameTextField.setVisible(false);
                aMovieChoiceBox.setVisible(true);
                aDatePicker.setVisible(true);
                aTimeTextField.setVisible(true);
                aShowtimeChoiceBox.setVisible(false);
                aRoomChoiceBox.setVisible(true);
                aNameLabel.setVisible(false);
                aMovieLabel.setVisible(true);
                aDateLabel.setVisible(true);
                aTimeLabel.setVisible(true);
                aShowtimeLabel.setVisible(false);
                aRoomLabel.setVisible(true);
                aCapacityLabel.setVisible(false);
                aCapacityTextField.setVisible(false);
                break;
            case EDIT_SHOWTIME:
                aTitleLabel.setText("Edit Showtime");
                aMovieChoiceBox.setVisible(true);
                aDatePicker.setVisible(true);
                aTimeTextField.setVisible(true);
                aShowtimeChoiceBox.setVisible(false);
                aRoomChoiceBox.setVisible(true);
                aNameTextField.setVisible(false);
                aNameLabel.setVisible(false);
                aMovieLabel.setVisible(true);
                aDateLabel.setVisible(true);
                aTimeLabel.setVisible(true);
                aShowtimeLabel.setVisible(false);
                aRoomLabel.setVisible(true);
                aCapacityLabel.setVisible(false);
                aCapacityTextField.setVisible(false);
                break;
            case ADD_ROOM:
                aTitleLabel.setText("Add Room");
                aNameTextField.setVisible(true);
                aMovieChoiceBox.setVisible(false);
                aDatePicker.setVisible(false);
                aTimeTextField.setVisible(false);
                aShowtimeChoiceBox.setVisible(false);
                aRoomChoiceBox.setVisible(false);
                aNameLabel.setText("Number");
                aNameLabel.setVisible(true);
                aMovieLabel.setVisible(false);
                aDateLabel.setVisible(false);
                aTimeLabel.setVisible(false);
                aShowtimeLabel.setVisible(false);
                aRoomLabel.setVisible(false);
                aCapacityLabel.setVisible(true);
                aCapacityTextField.setVisible(true);
                break;
            case EDIT_ROOM:
                aTitleLabel.setText("Edit Room");
                aNameTextField.setVisible(true);
                aMovieChoiceBox.setVisible(false);
                aDatePicker.setVisible(false);
                aTimeTextField.setVisible(false);
                aShowtimeChoiceBox.setVisible(false);
                aRoomChoiceBox.setVisible(false);
                aNameLabel.setText("Number");
                aNameLabel.setVisible(true);
                aMovieLabel.setVisible(false);
                aDateLabel.setVisible(false);
                aTimeLabel.setVisible(false);
                aShowtimeLabel.setVisible(false);
                aRoomLabel.setVisible(false);
                aCapacityLabel.setVisible(true);
                aCapacityTextField.setVisible(true);
                break;
            default:
                break;
        }
    }
    
    /**
     * Populates the ChoiceBoxes with data from the managers.
     */
    private void populateChoiceBoxes() {
        switch(aMode) {
            case ADD_MOVIE, EDIT_MOVIE:
                // No choice boxes to populate
                break;
            case ADD_SHOWTIME, EDIT_SHOWTIME:
                if (aMovieManager != null && !aMovieManager.getMovies().isEmpty()) {
                    aMovieChoiceBox.getItems().addAll(aMovieManager.getMovies());
            }
                if (aShowroomManager != null && !aShowroomManager.getShowrooms().isEmpty()) {
                    aRoomChoiceBox.getItems().addAll(aShowroomManager.getShowrooms());
                }
                if (aShowtimeManager != null && !aShowtimeManager.getShowtimes().isEmpty()) {
                    aShowtimeChoiceBox.getItems().addAll(aShowtimeManager.getShowtimes());
                }
                break;
            case ADD_ROOM, EDIT_ROOM:
                if (aShowroomManager != null && !aShowroomManager.getShowrooms().isEmpty()) {
                    aRoomChoiceBox.getItems().addAll(aShowroomManager.getShowrooms());
                }
                break;
        }
    }
    
    /**
     * Pre-fills the form fields if editing an existing object.
     */
    private void populateFields() {
        if (aCurrentObject == null) {
            return; // Adding new, leave empty
        }
            
        switch(aMode) {
            case EDIT_MOVIE:
                Movie movie = (Movie) aCurrentObject;
                aNameTextField.setText(movie.getName());
                break;
            case EDIT_SHOWTIME:
                Showtime showtime = (Showtime) aCurrentObject;
                aMovieChoiceBox.setValue(showtime.getMovie());
                aDatePicker.setValue(showtime.getDate());
                aTimeTextField.setText(showtime.getTime());
                aRoomChoiceBox.setValue(showtime.getShowroom());
                break;
            case EDIT_ROOM:
                Showroom room = (Showroom) aCurrentObject;
                aNameTextField.setText(String.valueOf(room.getRoomNumber()));
                aCapacityTextField.setText(String.valueOf(room.getCapacity()));
                break;
        }
    }
    
    
    /**
     * Handles the Save button click event.
     */
    @FXML
    private void handleSaveButtonClick() {
        // TODO: Implement save logic
    }
    
    /**
     * Handles the Cancel button click event.
     */
    @FXML
    private void handleCancelButtonClick() {
        // TODO: Implement cancel logic
    }
}
