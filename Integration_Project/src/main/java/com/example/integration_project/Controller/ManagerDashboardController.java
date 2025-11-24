package com.example.integration_project.Controller;

import java.util.List;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Model.Client;
import com.example.integration_project.Model.Manager;
import com.example.integration_project.Model.Movie;
import com.example.integration_project.Model.Showroom;
import com.example.integration_project.Model.Showtime;

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
    private Showtime aShowtime;
    private ListView<Showroom> aShowroomListView;
    private ListView<Showtime> aShowtimesListView;
    private ListView<Movie> aMovieListView;
    
    @FXML
    private ListView aListView;
    private List aItems;

    @FXML
    private void initialize() {
        // Initialize the controller if needed

    }

    private void refreshView(){
        
        //this.aItems.addAll()
        this.aListView.getItems().clear();

        this.aListView.getItems().addAll(this.aItems);
    }
    @FXML
    protected void onHelloButtonClick() {
        this.aPageTitle.setText("Welcome to JavaFX Application!");
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
    private void onMoviesButtonClick() {
        this.aItems = null;
        if(this.aManager != null){
            // this.aItems = this.aManager.getMovies();
        }
        this.refreshView();
    }
    
    @FXML 
    private void onShowtimesButtonClick() {
        this.aItems = null;
        if(this.aManager != null){
            // this.aItems = List.of(this.aManager.getShowtimes());
        }
        this.refreshView();
    }

    @FXML
    private void onShowroomsButtonClick() {
        this.aItems = null;
        if(this.aManager != null){
            // this.aItems = this.aManager.getShowrooms();
        }
        this.refreshView();
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

    // private Showtime getShowtimeById(int pShowtimeId) {
    //     for (Showtime showtime : this.aManager.getShowtimes()) {
    //         if (showtime.getId() == pShowtimeId) {
    //             this.aShowtime = showtime;
    //             return showtime;
    //         }
    //     }
    //     return null;
    // }

    // private String getSelectedItem(){

    //     return 
    // }

}
