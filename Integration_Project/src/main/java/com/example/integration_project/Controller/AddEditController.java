package com.example.integration_project.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.integration_project.Helpers.AlertHelper;
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
        switch(aMode) {
            case ADD_MOVIE:
                String movieName = aNameTextField.getText().trim();
                if (movieName.isEmpty()) {
                    AlertHelper.showErrorAlert("Validation Error", "Empty Field", "Please enter a movie name");
                    return;
                }
                try {
                    Movie newMovie = new Movie(movieName);
                    aMovieManager.addMovie(newMovie);
                    AlertHelper.showInfoAlert("Success", "Movie Added", "Movie has been added successfully");
                    closeWindow();
                } catch (IllegalArgumentException e) {
                    AlertHelper.showErrorAlert("Error", "Invalid Movie", e.getMessage());
                }
                break;
            case EDIT_MOVIE:
                movieName = aNameTextField.getText().trim();
                if (movieName.isEmpty()) {
                    AlertHelper.showErrorAlert("Validation Error", "Empty Field", 
                        "Please enter a movie name");
                    return;
                }
                try {
                    Movie movie = (Movie) aCurrentObject;
                    movie.setMovieName(movieName);
                    AlertHelper.showInfoAlert("Success", "Movie Updated", 
                        "Movie has been updated successfully");
                    closeWindow();
                } catch (IllegalArgumentException e) {
                    AlertHelper.showErrorAlert("Error", "Invalid Movie", e.getMessage());
                }
                break;
            case ADD_SHOWTIME:
                    try {
                        // Validate selections
                        if (aMovieChoiceBox.getValue() == null) {
                            AlertHelper.showErrorAlert("Validation Error", "Movie Required", 
                                "Please select a movie");
                            return;
                        }
                        if (aRoomChoiceBox.getValue() == null) {
                            AlertHelper.showErrorAlert("Validation Error", "Room Required", 
                                "Please select a room");
                            return;
                        }
                        if (aDatePicker.getValue() == null) {
                            AlertHelper.showErrorAlert("Validation Error", "Date Required", 
                                "Please select a date");
                            return;
                        }
                        if (aTimeTextField.getText().trim().isEmpty()) {
                            AlertHelper.showErrorAlert("Validation Error", "Time Required", 
                                "Please enter a time (HH:mm)");
                            return;
                        }
                        
                        // Create Showtime
                        Movie movie = aMovieChoiceBox.getValue();
                        Showroom room = aRoomChoiceBox.getValue();
                        LocalDate date = aDatePicker.getValue();
                        String timeStr = aTimeTextField.getText().trim();
                        
                        // Combine date and time
                        LocalTime time = LocalTime.parse(timeStr);
                        LocalDateTime dateTime = LocalDateTime.of(date, time);
                        
                        Showtime newShowtime = new Showtime(movie, dateTime, room);
                        aShowtimeManager.addShowtime(newShowtime);
                        AlertHelper.showInfoAlert("Success", "Showtime Added", 
                            "Showtime has been added successfully");
                        closeWindow();
                    } catch (IllegalArgumentException e) {
                        AlertHelper.showErrorAlert("Error", "Invalid Showtime", e.getMessage());
                    } catch (Exception e) {
                        AlertHelper.showErrorAlert("Error", "Parse Error", 
                            "Invalid time format. Use HH:mm");
                    }
                    break;
            case EDIT_SHOWTIME:
                    try {
                        // Validate selections
                        if (aMovieChoiceBox.getValue() == null) {
                            AlertHelper.showErrorAlert("Validation Error", "Movie Required", 
                                "Please select a movie");
                            return;
                        }
                        if (aRoomChoiceBox.getValue() == null) {
                            AlertHelper.showErrorAlert("Validation Error", "Room Required", 
                                "Please select a room");
                            return;
                        }
                        if (aDatePicker.getValue() == null) {
                            AlertHelper.showErrorAlert("Validation Error", "Date Required", 
                                "Please select a date");
                            return;
                        }
                        if (aTimeTextField.getText().trim().isEmpty()) {
                            AlertHelper.showErrorAlert("Validation Error", "Time Required", 
                                "Please enter a time (HH:mm)");
                            return;
                        }
                        
                        Showtime showtime = (Showtime) aCurrentObject;
                        showtime.setMovie(aMovieChoiceBox.getValue());
                        showtime.setShowroom(aRoomChoiceBox.getValue());
                        showtime.setDate(aDatePicker.getValue());
                        showtime.setTime(aTimeTextField.getText().trim());
                        AlertHelper.showInfoAlert("Success", "Showtime Updated", 
                            "Showtime has been updated successfully");
                        closeWindow();
                    } catch (IllegalArgumentException e) {
                        AlertHelper.showErrorAlert("Error", "Invalid Showtime", e.getMessage());
                    } catch (Exception e) {
                        AlertHelper.showErrorAlert("Error", "Parse Error", 
                            "Invalid time format. Use HH:mm");
                    }
                    break;
            case ADD_ROOM:
                try {
                    String roomNumberStr = aNameTextField.getText().trim();
                    String capacityStr = aCapacityTextField.getText().trim();
                    
                    if (roomNumberStr.isEmpty() || capacityStr.isEmpty()) {
                        AlertHelper.showErrorAlert("Validation Error", "Empty Field", 
                            "Please enter both room number and capacity");
                        return;
                    }
                    
                    int roomNumber = Integer.parseInt(roomNumberStr);
                    int capacity = Integer.parseInt(capacityStr);
                    
                    Showroom newRoom = new Showroom(roomNumber, capacity);
                    aShowroomManager.addShowroom(newRoom);
                    AlertHelper.showInfoAlert("Success", "Room Added", 
                        "Room has been added successfully");
                    closeWindow();
                } catch (NumberFormatException e) {
                    AlertHelper.showErrorAlert("Error", "Invalid Number", 
                        "Room number and capacity must be numbers");
                } catch (IllegalArgumentException e) {
                    AlertHelper.showErrorAlert("Error", "Invalid Room", e.getMessage());
                }
                break;
            case EDIT_ROOM:
                try {
                    String roomNumberStr = aNameTextField.getText().trim();
                    String capacityStr = aCapacityTextField.getText().trim();
                    
                    if (roomNumberStr.isEmpty() || capacityStr.isEmpty()) {
                        AlertHelper.showErrorAlert("Validation Error", "Empty Field", 
                            "Please enter both room number and capacity");
                        return;
                    }
                    
                    int roomNumber = Integer.parseInt(roomNumberStr);
                    int capacity = Integer.parseInt(capacityStr);
                    
                    Showroom room = (Showroom) aCurrentObject;
                    room.setRoomNumber(roomNumber);
                    room.setRoomCapacity(capacity);
                    AlertHelper.showInfoAlert("Success", "Room Updated", 
                        "Room has been updated successfully");
                    closeWindow();
                } catch (NumberFormatException e) {
                    AlertHelper.showErrorAlert("Error", "Invalid Number", 
                        "Room number and capacity must be numbers");
                } catch (IllegalArgumentException e) {
                    AlertHelper.showErrorAlert("Error", "Invalid Room", e.getMessage());
                }
                break;
        }
    }
    
    /**
     * Handles the Cancel button click event.
     * Closes the window without saving any changes.
     */
    @FXML
    private void handleCancelButtonClick() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) aSaveButton.getScene().getWindow();
        stage.close();
    }
}
