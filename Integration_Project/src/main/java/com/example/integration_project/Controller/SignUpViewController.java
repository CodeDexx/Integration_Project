package com.example.integration_project.Controller;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Model.User;
import com.example.integration_project.MovieTheatreApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpViewController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button saveButton;


    @FXML
    private void onSaveButtonClick() {
        String name = nameTextField.getText();
        String password = passwordTextField.getText();
        String email = emailTextField.getText();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            AlertHelper.showErrorAlert("Missing Information", "Incomplete Form", "Please in all the fields.");
            return;
        }

        try {
            User user = new User(name, password, email);

            AlertHelper.showInfoAlert("Accpunt Created", "Successfully Created User","Your account has been created successfully");

            returnToClientDashboard();
        } catch (Exception e) {
            AlertHelper.showErrorAlert("Error", "Could not create account", e.getMessage());
        }
    }

    private void returnToClientDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(MovieTheatreApplication.class.getResource("clientDashboard-view.fxml"));
            Parent view = loader.load();

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.setScene(new Scene(view));
            stage.setTitle("Client Dashboard");
            stage.show();
        }  catch (Exception e) {
            AlertHelper.showErrorAlert("Error", "Could not open the client Dashboard", e.getMessage());
        }
    }
}
