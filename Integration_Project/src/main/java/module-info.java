module com.example.integration_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.integration_project to javafx.fxml;
    exports com.example.integration_project;
}