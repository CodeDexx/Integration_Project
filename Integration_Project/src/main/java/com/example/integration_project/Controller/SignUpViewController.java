package com.example.integration_project.Controller;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Helpers.AlertHelper.EmailValidator;
import com.example.integration_project.Model.Client;
import com.example.integration_project.Model.ClientManager;
import com.example.integration_project.Model.User;
import com.example.integration_project.MovieTheatreApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for the Sign-Up view
 * <p>
 *     This controller is responsible for handling the creation of a new user account, validating the input field,
 *     displaying feedback using alerts, and transitioning the user to the Client Dashboard upon successful creation.
 * </p>
 * <p>
 *     No permanent user-storage is implemented, accounts are only created temporarily during runtime.
 * </p>
 *
 * @author Emmanuelle
 */
public class SignUpViewController {

    /** TextField where the user enters their name. */
    @FXML
    private TextField aNameTextField;

    /** TextField where the user enters their password. */
    @FXML
    private TextField aPasswordTextField;

    /** TextField where the user enters their email address. */
    @FXML
    private TextField aEmailTextField;

    /** aSaveButton saves the account created. */
    @FXML
    private Button aSaveButton;

    /**
     * Handles the event when the "Save" button is clicked.
     * <p>
     *     This method collects user input, validates that all the fields are filled, creates a new {@link User},
     *     and displays alerts based on the success or failure of the process.
     * </p>
     * <p>On success, this method redirects the user to the Client Dashboard.</p>
     */
    @FXML
    private void onSaveButtonClick() {

        String name = aNameTextField.getText();
        String password = aPasswordTextField.getText();
        String email = aEmailTextField.getText();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            AlertHelper.showErrorAlert("Missing Information", "Incomplete Form", "Please fill in all the fields.");
            return;
        }

        if (!EmailValidator.isValidFormat(email)) {
            AlertHelper.showErrorAlert("Sign-Up Error", "Invalid Email Format", "Please enter a valid email address (e.g., name@domain.com).");
            return;
        }

        try {

            Client newClient = new Client(name, email, password);

            // Saving the new client
            ClientManager.getInstance().addClient(newClient);

            AlertHelper.showInfoAlert("Account Created", "Success", "Your account has been created!");
            aNameTextField.clear();
            aPasswordTextField.clear();
            aEmailTextField.clear();
            returnToClientDashboard();

        } catch (Exception e) {
            AlertHelper.showErrorAlert("Error", "Could not create account", e.getMessage());
        }
    }

    /**
     * Loads and display the "Client Dashboard" view after a successful sign-up.
     * <p>
     *     This method opens the dashboard as a modal window, blocking the sign-up window
     *     until the user finishes interacting with the dashboard.
     * </p>
     * <p>If loading fails, an error alert is shown to the user.</p>
     */
    private void returnToClientDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(MovieTheatreApplication.class.getResource("clientDashboard-view.fxml"));
            Parent view = loader.load();


            Stage nextStage = new Stage();
            nextStage.setScene(new Scene(view));
            nextStage.initModality(Modality.WINDOW_MODAL);
            nextStage.initOwner(aSaveButton.getScene().getWindow());
            nextStage.showAndWait();

        }  catch (Exception e) {
            AlertHelper.showErrorAlert("Error", "Could not open the client Dashboard", e.getMessage());
        }
    }
}
