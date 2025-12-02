package com.example.integration_project.Controller;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Model.Manager;
import com.example.integration_project.MovieTheatreApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 */

public class LogInViewController {

    private final Manager MANAGER_ACCOUNT = new Manager("manager@grandview.ca", "Fall2025");

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    @FXML
    private Button signupButton;

    @FXML
    private void onLoginButtonClick() {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            AlertHelper.showErrorAlert("Login Error",
                    "Missing Information", "Please enter both email and password.");
            return;
        }

        if (email.equals(MANAGER_ACCOUNT.getEmailAddress()) && password.equals(MANAGER_ACCOUNT.getPassword())) {

            try {
                FXMLLoader loader = new FXMLLoader(MovieTheatreApplication.class.getResource("managerDashboard-view.fxml"));
                Parent view = loader.load();

                Stage nextStage = new Stage();
                nextStage.setScene(new Scene(view));
                nextStage.initModality(Modality.WINDOW_MODAL);
                nextStage.initOwner(loginButton.getScene().getWindow());
                nextStage.showAndWait();

            } catch (Exception e) {
                AlertHelper.showErrorAlert("Load Error", "Could not load Manager Dashboard", e.getMessage());
            }
        }
        else {
            AlertHelper.showErrorAlert("Login Failed", "Incorrect Credentials", "The email or password is incorrect.");
        }
    }

    @FXML
    private void onSignupButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(MovieTheatreApplication.class.getResource("signup-view.fxml"));
            Parent view = loader.load();

            Stage nextStage = new Stage();
            nextStage.setScene(new Scene(view));
            nextStage.initModality(Modality.WINDOW_MODAL);
            nextStage.initOwner(signupButton.getScene().getWindow());
            nextStage.showAndWait();

        } catch (Exception e) {
            AlertHelper.showErrorAlert("Load Error", "Could not load Sign Up view", e.getMessage());
        }
    }

    @FXML
    private void onBackButtonClick() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
