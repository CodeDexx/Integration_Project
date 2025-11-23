package com.example.integration_project.Controller;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Model.Client;
import com.example.integration_project.Model.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

public class ManagerDashboardController {

    @FXML
    private Label aPageTitle;

    @FXML
    private Button aLogoutButton;

    private Client aClient;
    private Manager aManager;
    private Stage aStage = new Stage();

    private List<Client> aClientList;

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
    private void onDeleteButtonClick() {
        try{
            if(this.aManager == null){
                AlertHelper.showErrorAlert("Delete Error", "No manager found", "Please log in as a manager before deleting.");
                return;
            }
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
