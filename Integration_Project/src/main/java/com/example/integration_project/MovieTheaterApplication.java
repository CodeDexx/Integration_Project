package com.example.integration_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieTheaterApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//<<<<<<< HEAD
        FXMLLoader fxmlLoader = new FXMLLoader(MovieTheaterApplication.class.getResource("login-view.fxml"));
//=======
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
//>>>>>>> b7c7ea1 (MovieTheaterApplication.java forced commit.)
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Login/Sign Up");
        stage.setScene(scene);
        stage.show();
    }
}
