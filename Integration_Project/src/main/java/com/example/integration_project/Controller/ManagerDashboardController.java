package com.example.integration_project.Controller;

import java.util.List;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Model.Client;
import com.example.integration_project.Model.Manager;
import com.example.integration_project.Model.Movie;
import com.example.integration_project.Model.MovieManager;
import com.example.integration_project.Model.Showroom;
import com.example.integration_project.Model.ShowroomManager;
import com.example.integration_project.Model.Showtime;
import com.example.integration_project.Model.ShowtimeManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ManagerDashboardController {

    @FXML
    private Label aPageTitle;

    @FXML
    private Button aLogoutButton;

    private Client aClient;
    private Manager aManager;
    private Stage aStage = new Stage();
    private String aCurrentView;
    private MovieManager aMovieManager = new MovieManager();
    private ShowtimeManager aShowtimeManager = new ShowtimeManager();
    private ShowroomManager aShowroomManager = new ShowroomManager();
    
    @FXML
    private ListView<Object> aListView = new  ListView<>();
    private Object aSelectedItem;

    @FXML
    private void initialize() {
        // Initialize the controller if needed
        this.aCurrentView = "Movies";
        refreshView("Movies");
    }

    private void refreshView(String pCurrentListView){
    
        //Listing the items in the ListView based on the current view

        switch (pCurrentListView) {
            case "Movies" -> {
                this.aListView.getItems().clear();
                if(this.aManager != null){
                    // Getting the list of movies from the manager
                    for (Movie movie : aMovieManager.getMovies()) {
                        this.aListView.getItems().add("Sample Movie Item");
                        this.aListView.getItems().add(movie.getName());
                    }
                }
                else{
                    AlertHelper.showErrorAlert("Display Error", "Empty Movie List","The list of movies is currently empty. Please add some.");
                }
            }
            case "Showtimes" -> {
                this.aListView.getItems().clear();
                if(this.aManager == null){
                    // Getting the list of showtimes from the manager
                    this.aListView.getItems().add("Sample Showtime Item");
                    this.aListView.getItems().addAll(this.aShowtimeManager.getShowtimes());
                }
            }
            case "Showrooms" -> {
                this.aListView.getItems().clear();
                if(this.aManager != null){
                    // Getting the list of showrooms from the manager
                    this.aListView.getItems().add("Sample Showrooms Item");
                    this.aListView.getItems().addAll(this.aShowroomManager.getShowrooms());
                }
            }
            default -> {
            }
        }
    }

    @FXML
    private void onLogoutButtonClick() {
        aStage = (Stage) aLogoutButton.getScene().getWindow();
        aStage.close();
    }

    @FXML
    private void onAddButtonClick() {
        // Object.getClass

        // add movie

        // add showtime

        // add showroom
        
        try{
            if(this.aClient == null){
                AlertHelper.showErrorAlert("Add Error", "No client selected", "Please select a client before adding.");
                return;
            }
            this.aManager.addUser(this.aClient);
            AlertHelper.showInfoAlert("Add", "Client Added", "The client has been added successfully.");
        } catch(Exception e){
            AlertHelper.showErrorAlert("Add Error", "Add Error", e.getMessage());
        }
    }

    @FXML 
    private void onUpdateButtonClick() {
        // Object.getClass

        // update movie

        // update showtime

        // update showroom
        
        try{
            if(this.aClient == null){
                AlertHelper.showErrorAlert("Update Error", "No client selected", "Please select a client before updating.");
                return;
            }
            // this.aManager.updateUser(this.aClient);
            AlertHelper.showInfoAlert("Update", "Client Updated", "The client has been updated successfully.");
        } catch(Exception e){
            AlertHelper.showErrorAlert("Update Error", "Update Error", e.getMessage());
        }
    }

    @FXML
    private void onMoviesButtonClick(ActionEvent pEvent) {
        this.aSelectedItem = null;
        this.aCurrentView = ((Button) pEvent.getSource()).getText();
        if(this.aManager != null){
            this.refreshView(this.aCurrentView);
        }
    }
    
    @FXML 
    private void onShowtimesButtonClick(ActionEvent pEvent) {
        this.aSelectedItem = null;
        if(this.aManager == null){
            this.refreshView(((Button) pEvent.getSource()).getText());
        }
    }

    @FXML
    private void onShowroomsButtonClick(ActionEvent pEvent) {
        this.aSelectedItem = null;
        if(this.aManager != null){
            this.refreshView(((Button) pEvent.getSource()).getText());
        }
    }

    @FXML
    private void onDeleteButtonClick() {
        // Object.getClass

        // delete movie
        if(this.aManager != null){
            if (true) {
                
            }
        }
        else{
                AlertHelper.showErrorAlert("Delete Error", "No manager found", "Please log in as a manager before deleting.");
                return;
        }
        // delete showtime

        // delete showroom
        
        try{
            
            if(this.aClient == null){
                AlertHelper.showErrorAlert("Delete Error", "No client selected", "Please select a client before deleting.");
                return;
            }
            this.aManager.deleteUser(this.aClient);
            AlertHelper.showInfoAlert("Delete", "Client Deleted", "The animal has been deleted successfully.");
        } catch(Exception e){
            AlertHelper.showErrorAlert("Delete Error", "Delete Error", e.getMessage());
        }
    }
}
