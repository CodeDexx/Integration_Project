package com.example.integration_project.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ManagerDashboardController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
