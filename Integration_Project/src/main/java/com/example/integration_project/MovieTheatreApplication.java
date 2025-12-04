package com.example.integration_project;

import com.example.integration_project.Helpers.ImportHelper;
import com.example.integration_project.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieTheatreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheatreApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 413, 400);

        //Loading the imports
        ImportHelper.loadMovies();
        ImportHelper.loadShowroom();
        ImportHelper.loadShowtime();
        ImportHelper.loadTickets();

        stage.setTitle("Login/Sign Up");
        stage.setScene(scene);
        stage.show();
    }
}
