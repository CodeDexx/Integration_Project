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
 * Controller for the add-edit-view.fxml file in the Movie Theater Management System.
 * 
 * This controller handles adding and editing operations for three entity types:
 * Movies, Showtimes, and Showrooms. It implements the MVC pattern by managing the
 * interaction between the FXML view and the model classes through manager instances.
 * 
 * <p>The controller uses a single reusable FXML view with conditional UI logic based
 * on the FormMode enum, reducing code duplication while providing specialized
 * functionality for each entity type.</p>
 * 
 * <p>Key responsibilities include:
 * <ul>
 *   <li>Initializing the form based on the operation mode (add/edit)</li>
 *   <li>Setting up UI visibility based on the type of entity being managed</li>
 *   <li>Populating UI choice boxes with data from managers</li>
 *   <li>Pre-filling form fields when editing existing objects</li>
 *   <li>Validating user input before saving</li>
 *   <li>Creating new objects or updating existing ones through manager classes</li>
 *   <li>Providing user feedback through alert dialogs</li>
 * </ul>
 * </p>
 * 
 * @author Ian
 * @version 1.0
 * @see FormMode
 * @see Movie
 * @see Showtime
 * @see Showroom
 * @see MovieManager
 * @see ShowtimeManager
 * @see ShowroomManager
 * @see AlertHelper
 */
public class AddEditController {
    
    /**
     * Enum representing the different modes this controller can operate in.
     * 
     * <p>Each mode determines which fields are visible, what validation rules apply,
     * and what operation (add or edit) is performed on which entity type.</p>
     * 
     * <ul>
     *   <li>{@code ADD_MOVIE} - Create a new movie</li>
     *   <li>{@code EDIT_MOVIE} - Modify an existing movie</li>
     *   <li>{@code ADD_SHOWTIME} - Create a new showtime</li>
     *   <li>{@code EDIT_SHOWTIME} - Modify an existing showtime</li>
     *   <li>{@code ADD_ROOM} - Create a new showroom</li>
     *   <li>{@code EDIT_ROOM} - Modify an existing showroom</li>
     * </ul>
     */
    public enum FormMode {
        /** Create a new movie */
        ADD_MOVIE,
        /** Modify an existing movie */
        EDIT_MOVIE,
        /** Create a new showtime */
        ADD_SHOWTIME,
        /** Modify an existing showtime */
        EDIT_SHOWTIME,
        /** Create a new showroom */
        ADD_ROOM,
        /** Modify an existing showroom */
        EDIT_ROOM
    }
    
    /** The current operation mode (add or edit, and which entity type) */
    private FormMode aMode;
    
    /** The object being edited. Can be a Movie, Showtime, or Showroom. Null if adding new. */
    private Object aCurrentObject;
    
    /** Manager responsible for Movie persistence and retrieval operations */
    private MovieManager aMovieManager;
    
    /** Manager responsible for Showtime persistence and retrieval operations */
    private ShowtimeManager aShowtimeManager;
    
    /** Manager responsible for Showroom persistence and retrieval operations */
    private ShowroomManager aShowroomManager;
    
    /** Label displaying the title of the dialog (e.g., "Add Movie", "Edit Showtime") */
    @FXML
    private Label aTitleLabel;
    
    /** Text field for entering the name of a Movie or the room number of a Showroom */
    @FXML
    private TextField aNameTextField;
    
    /** Choice box for selecting a Movie when adding or editing a Showtime */
    @FXML
    private ChoiceBox<Movie> aMovieChoiceBox;

    /** Text field for entering the capacity of a Showroom */
    @FXML
    private TextField aCapacityTextField;
    
    /** Date picker for selecting the date of a Showtime */
    @FXML
    private DatePicker aDatePicker;
    
    /** Choice box for selecting a Showroom when adding or editing a Showtime */
    @FXML
    private ChoiceBox<Showroom> aRoomChoiceBox;
    
    /** Button to save changes or create a new object */
    @FXML
    private Button aSaveButton;
    
    /** Button to cancel the operation without saving */
    @FXML
    private Button aCancelButton;

    /** Label for the name field (displays "Name" for Movies or "Number" for Rooms) */
    @FXML
    private Label aNameLabel;

    /** Label for the movie choice box field */
    @FXML
    private Label aMovieLabel;

    /** Label for the date picker field */
    @FXML
    private Label aDateLabel;

    /** Label for the room choice box field */
    @FXML
    private Label aRoomLabel;

    /** Label for the capacity field */
    @FXML
    private Label aCapacityLabel;
    
    /** Text field for entering the time of a Showtime in "HH:mm" format */
    @FXML
    private TextField aTimeTextField;
    
    /** Label for the time text field */
    @FXML
    private Label aTimeLabel;
    
    /**
     * Initializes the controller with the necessary data and managers.
     * 
     * <p>This method should be called after the FXML is loaded but before the stage is shown.
     * It sets up the initial state of the form by storing references to the managers,
     * determining the operation mode, and orchestrating the setup sequence.</p>
     * 
     * <p>The initialization sequence:
     * <ol>
     *   <li>Stores the provided managers and mode information</li>
     *   <li>Calls {@link #setupUI()} to configure field visibility and labels</li>
     *   <li>Calls {@link #populateChoiceBoxes()} to load data from managers</li>
     *   <li>Calls {@link #populateFields()} to pre-fill form fields if editing</li>
     * </ol>
     * </p>
     * 
     * @param pMode the FormMode indicating what operation to perform (add or edit which entity)
     * @param pCurrentObject the object being edited, or {@code null} if adding a new object.
     *                        Can be a Movie, Showtime, or Showroom depending on the mode.
     * @param pMovieManager the MovieManager instance for retrieving/persisting Movie data.
     *                       Must not be null when mode involves movies.
     * @param pShowtimeManager the ShowtimeManager instance for retrieving/persisting Showtime data.
     *                          Must not be null when mode involves showtimes.
     * @param pShowroomManager the ShowroomManager instance for retrieving/persisting Showroom data.
     *                          Must not be null when mode involves showrooms.
     * @see FormMode
     * @see #setupUI()
     * @see #populateChoiceBoxes()
     * @see #populateFields()
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
     * Sets up the UI by showing and hiding fields based on the current operation mode.
     * 
     * <p>This method configures the visibility of all UI components to match the requirements
     * of the current operation. It also sets appropriate labels and titles for each mode.</p>
     * 
     * <p>For each of the six FormModes:
     * <ul>
     *   <li>{@code ADD_MOVIE} - Shows only name field and title "Add Movie"</li>
     *   <li>{@code EDIT_MOVIE} - Shows only name field and title "Edit Movie"</li>
     *   <li>{@code ADD_SHOWTIME} - Shows movie, date, time, and room fields with title "Add Showtime"</li>
     *   <li>{@code EDIT_SHOWTIME} - Shows movie, date, time, and room fields with title "Edit Showtime"</li>
     *   <li>{@code ADD_ROOM} - Shows room number and capacity fields with title "Add Room"</li>
     *   <li>{@code EDIT_ROOM} - Shows room number and capacity fields with title "Edit Room"</li>
     * </ul>
     * </p>
     * 
     * @see FormMode
     */
    private void setupUI() {
        switch (aMode) {
            case ADD_MOVIE:
                aTitleLabel.setText("Add Movie");
                aNameTextField.setVisible(true);
                aMovieChoiceBox.setVisible(false);
                aDatePicker.setVisible(false);
                aTimeTextField.setVisible(false);
                aRoomChoiceBox.setVisible(false);
                aNameLabel.setVisible(true);
                aMovieLabel.setVisible(false);
                aDateLabel.setVisible(false);
                aTimeLabel.setVisible(false);
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
                aRoomChoiceBox.setVisible(false);
                aNameLabel.setVisible(true);
                aMovieLabel.setVisible(false);
                aDateLabel.setVisible(false);
                aTimeLabel.setVisible(false);
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
                aRoomChoiceBox.setVisible(true);
                aNameLabel.setVisible(false);
                aMovieLabel.setVisible(true);
                aDateLabel.setVisible(true);
                aTimeLabel.setVisible(true);
                aRoomLabel.setVisible(true);
                aCapacityLabel.setVisible(false);
                aCapacityTextField.setVisible(false);
                break;
            case EDIT_SHOWTIME:
                aTitleLabel.setText("Edit Showtime");
                aMovieChoiceBox.setVisible(true);
                aDatePicker.setVisible(true);
                aTimeTextField.setVisible(true);
                aRoomChoiceBox.setVisible(true);
                aNameTextField.setVisible(false);
                aNameLabel.setVisible(false);
                aMovieLabel.setVisible(true);
                aDateLabel.setVisible(true);
                aTimeLabel.setVisible(true);
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
                aRoomChoiceBox.setVisible(false);
                aNameLabel.setText("Number");
                aNameLabel.setVisible(true);
                aMovieLabel.setVisible(false);
                aDateLabel.setVisible(false);
                aTimeLabel.setVisible(false);
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
                aRoomChoiceBox.setVisible(false);
                aNameLabel.setText("Number");
                aNameLabel.setVisible(true);
                aMovieLabel.setVisible(false);
                aDateLabel.setVisible(false);
                aTimeLabel.setVisible(false);
                aRoomLabel.setVisible(false);
                aCapacityLabel.setVisible(true);
                aCapacityTextField.setVisible(true);
                break;
            default:
                break;
        }
    }
    
    /**
     * Populates the choice boxes with data retrieved from the manager instances.
     * 
     * <p>This method loads the appropriate data into the choice boxes based on the current
     * operation mode. Different modes require different data:
     * <ul>
     *   <li>Movie modes do not populate any choice boxes</li>
     *   <li>Showtime modes populate:
     *     <ul>
     *       <li>Movie choice box with all available movies</li>
     *       <li>Room choice box with all available showrooms</li>
     *       <li>Showtime choice box with all available showtimes</li>
     *     </ul>
     *   </li>
     *   <li>Room modes populate the room choice box with all available showrooms</li>
     * </ul>
     * </p>
     * 
     * <p>If a manager or its data collection is null or empty, that choice box is not populated,
     * allowing graceful handling of missing data.</p>
     * 
     * @see #initializeForm(FormMode, Object, MovieManager, ShowtimeManager, ShowroomManager)
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
                break;
            case ADD_ROOM, EDIT_ROOM:
                if (aShowroomManager != null && !aShowroomManager.getShowrooms().isEmpty()) {
                    aRoomChoiceBox.getItems().addAll(aShowroomManager.getShowrooms());
                }
                break;
        }
    }
    
    /**
     * Pre-fills the form fields with data from the object being edited.
     * 
     * <p>This method only executes when in edit mode with a non-null object. It extracts
     * relevant data from the object and populates the corresponding UI fields so that
     * the user sees the current values before making modifications.</p>
     * 
     * <p>Field population strategy:
     * <ul>
     *   <li>{@code EDIT_MOVIE} - Populates name field with the movie's name</li>
     *   <li>{@code EDIT_SHOWTIME} - Populates movie choice, date picker, time field, and room choice</li>
     *   <li>{@code EDIT_ROOM} - Populates room number and capacity fields as strings</li>
     *   <li>Add modes - No fields are populated (form remains empty for new entries)</li>
     * </ul>
     * </p>
     * 
     * <p>Note: This method returns early if the current object is null, which is expected
     * behavior for add modes.</p>
     * 
     * @see #initializeForm(FormMode, Object, MovieManager, ShowtimeManager, ShowroomManager)
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
     * Handles the Save button click event and processes the form data accordingly.
     * 
     * <p>This method implements the business logic for all six operation modes. It validates
     * user input, creates or updates objects, persists changes through the appropriate manager,
     * and provides user feedback via alerts.</p>
     * 
     * <p>Operation breakdown by mode:
     * <ul>
     *   <li>{@code ADD_MOVIE} - Validates name, creates new Movie, calls manager.addMovie()</li>
     *   <li>{@code EDIT_MOVIE} - Validates name, updates existing Movie, changes persist through reference</li>
     *   <li>{@code ADD_SHOWTIME} - Validates all selections, combines date/time into LocalDateTime,
     *       creates new Showtime, calls manager.addShowtime()</li>
     *   <li>{@code EDIT_SHOWTIME} - Validates all selections, updates existing Showtime properties
     *       via setMovie(), setShowroom(), setDate(), setTime()</li>
     *   <li>{@code ADD_ROOM} - Validates and parses room number and capacity, creates new Showroom,
     *       calls manager.addShowroom()</li>
     *   <li>{@code EDIT_ROOM} - Validates and parses room number and capacity, updates existing
     *       Showroom via setRoomNumber() and setRoomCapacity()</li>
     * </ul>
     * </p>
     * 
     * <p>Error handling:
     * <ul>
     *   <li>{@link IllegalArgumentException} - Caught from model constructors/setters when data is invalid.
     *       User sees appropriate error alert.</li>
     *   <li>{@link NumberFormatException} - Caught when parsing room number/capacity strings.
     *       User sees "Invalid Number" error alert.</li>
     *   <li>{@link Exception} - Generic catch for time parsing failures. User sees "Parse Error" alert
     *       with guidance on correct format ("HH:mm").</li>
     * </ul>
     * </p>
     * 
     * <p>Validation strategy:
     * <ul>
     *   <li>Text fields - Checked for empty strings after trimming whitespace</li>
     *   <li>Choice boxes - Checked for null selection (value not chosen)</li>
     *   <li>Date picker - Checked for null date selection</li>
     *   <li>Time field - Parsed as LocalTime to validate "HH:mm" format</li>
     *   <li>Number fields - Parsed to int to ensure valid numbers</li>
     * </ul>
     * </p>
     * 
     * <p>Success behavior:
     * <ul>
     *   <li>Displays success alert with appropriate message for the operation</li>
     *   <li>Calls {@link #closeWindow()} to dismiss the form</li>
     * </ul>
     * </p>
     * 
     * @see FormMode
     * @see AlertHelper
     * @see #closeWindow()
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
     * 
     * <p>This method is invoked when the user clicks the Cancel button. It closes the dialog
     * window without saving any changes or modifications to the data.</p>
     * 
     * <p>Any unsaved changes in the form fields are discarded. The original object (if being edited)
     * remains unchanged because updates only occur when the Save button is clicked and validation passes.</p>
     * 
     * @see #closeWindow()
     */
    @FXML
    private void handleCancelButtonClick() {
        closeWindow();
    }

    /**
     * Closes the current window (stage) containing this controller's form.
     * 
     * <p>This private helper method is called after successful save operations or when the
     * user clicks Cancel. It retrieves the Stage from the Save button's scene graph and
     * closes it, dismissing the dialog.</p>
     * 
     * <p>Implementation note: The Save button is used to access the stage because it is
     * always available in all modes, making it a reliable reference point in the scene graph.</p>
     * 
     * @see #handleSaveButtonClick()
     * @see #handleCancelButtonClick()
     */
    private void closeWindow() {
        Stage stage = (Stage) aSaveButton.getScene().getWindow();
        stage.close();
    }
}
