package com.example.integration_project.Controller;

import com.example.integration_project.Helpers.AlertHelper;
import com.example.integration_project.Helpers.ImportHelper;
import com.example.integration_project.Helpers.AlertHelper.EmailValidator;
import com.example.integration_project.Model.Client;
import com.example.integration_project.Model.ClientManager;
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

    /** Sets the signup button to the text "Create an account" if the client has no account */
    @FXML
    private void initialize() {
        aSignupButton.setText("Create Account");

        ImportHelper.loadClients(); // loads list of clients from ImportHelper into ClientManager
    }

    /**
     * Handles the login action when the "log In" button is clicked.
     * <p>
     *     Validates that both email and password fields are filled.
     *     If credentials are valid, the Manager Dashboard view is loaded in a modal window.
     * </p>
     * <p> If the client already has an account, the for loop goes through the clients in the {@link ImportHelper},
     * and logs him/her in and the Client Dashboard is displayed in a modal window</p>
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

        if (!EmailValidator.isValidFormat(email)) {
            AlertHelper.showErrorAlert("Login Error", "Invalid Email Format", "Please enter a valid email address (e.g., name@domain.com).");
        }

        if (email.equals(MANAGER_ACCOUNT.getEmailAddress()) && password.equals(MANAGER_ACCOUNT.getPassword())) {
            openView("managerDashboard-view.fxml");
            aEmailTextField.clear();
            aPasswordTextField.clear();
            return;
        }

        Client foundClient = null;

        for (Client client : ClientManager.getInstance().getClients()) {
            if (client.getEmailAddress().equalsIgnoreCase(email) &&
                    client.getPassword().equals(password)) {

                foundClient = client;
                break;
            }
        }

        if (foundClient != null) {
            openView("clientDashboard-view.fxml");
            return;
        }

        AlertHelper.showErrorAlert("Login Failed", "Incorrect Email or Password", "Please enter a valid email or password.");
    }

    /**
     * Opens the Sign-Up view when the "Sign Up" button is clicked.
     */
    @FXML
    private void onSignupButtonClick() {
        openView("signup-view.fxml");
    }

    /**
     * Closes the current login window when the "Back" button is clicked.
     */
    @FXML
    private void onBackButtonClick() {
        Stage stage = (Stage) aBackButton.getScene().getWindow();
        stage.close();
    }

    /**
     * openView method loads any view and if the loading fails, an error alert is displayed.
     */
    private void openView(String viewName) {
        try {
            FXMLLoader loader = new FXMLLoader(MovieTheatreApplication.class.getResource(viewName));
            Parent view = loader.load();

            Stage nextStage = new Stage();
            nextStage.setScene(new Scene(view));
            nextStage.initModality(Modality.WINDOW_MODAL);
            nextStage.initOwner(aLoginButton.getScene().getWindow());
            nextStage.showAndWait();

        } catch (Exception e) {
            AlertHelper.showErrorAlert("Error", "Could not load the page" +  viewName, e.getMessage());
        }
    }
}
