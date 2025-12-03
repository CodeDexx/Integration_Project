package com.example.integration_project.Controller;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Helpers.ImportHelper;
import com.example.integration_project.Model.Client;
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

import java.util.List;
/**
 * Controller class for the Login View
 * <p>
 *     This class handles user interactions from the login interface, including authentication,
 *     navigation to the sign-up view, and returning to the previous view.
 * </p>
 * <p>Only the manager account is supported for administrative login</p>
 *
 * @author Emmanuelle
 */

public class LogInViewController {

    /** Hardcoded manager account used for authentication. */
    private final Manager MANAGER_ACCOUNT = new Manager("manager@grandview.ca", "Fall2025");

    /** TextField for entering the user's email. */
    @FXML
    private TextField aEmailTextField;

    /** TextField for entering the user's password. */
    @FXML
    private TextField aPasswordTextField;

    /** Button that triggers the login process. */
    @FXML
    private Button aLoginButton;

    /** Button to close the current login window. */
    @FXML
    private Button aBackButton;

    /** Button used to open the account sign-up view. */
    @FXML
    private Button aSignupButton;

    /**
     * Handles the login action when the "log In" button is clicked.
     * <p>
     *     Validates that both email and password fields are filled.
     *     If credentials are valid, the Manager Dashboard view is loaded in a modal window.
     * </p>
     */
    @FXML
    private void onLoginButtonClick() {
        String email = aEmailTextField.getText();
        String password = aPasswordTextField.getText();

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
                nextStage.initOwner(aLoginButton.getScene().getWindow());
                nextStage.showAndWait();

            } catch (Exception e) {
                AlertHelper.showErrorAlert("Load Error", "Could not load Manager Dashboard", e.getMessage());
            }
        }
        else {
            AlertHelper.showErrorAlert("Login Failed", "Incorrect Credentials", "The email or password is incorrect.");
        }
    }

    /**
     * Opens the Sign-Up view when the "Sign Up" button is clicked.
     * <p>
     *     The sign-up view opens as a modal window.
     *     If when opening the sign-up view the loading fails, an error alert is displayed.
     * </p>
     */
    @FXML
    private void onSignupButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(MovieTheatreApplication.class.getResource("signup-view.fxml"));
            Parent view = loader.load();

            Stage nextStage = new Stage();
            nextStage.setScene(new Scene(view));
            nextStage.initModality(Modality.WINDOW_MODAL);
            nextStage.initOwner(aSignupButton.getScene().getWindow());
            nextStage.showAndWait();

        } catch (Exception e) {
            AlertHelper.showErrorAlert("Load Error", "Could not load Sign Up view", e.getMessage());
        }
    }

    /**
     * Closes the current login window when the "Back" button is clicked.
     * <p>
     *     This method simply closes the current stage.
     * </p>
     */
    @FXML
    private void onBackButtonClick() {
        Stage stage = (Stage) aBackButton.getScene().getWindow();
        stage.close();
    }
}
