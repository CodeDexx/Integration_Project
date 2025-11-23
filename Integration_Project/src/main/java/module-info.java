module com.example.integration_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;


    opens com.example.integration_project to javafx.fxml;
    exports com.example.integration_project;
    exports com.example.integration_project.Controller;
    opens com.example.integration_project.Controller to javafx.fxml;
    exports com.example.integration_project.Model;
    opens com.example.integration_project.Model to javafx.fxml;
}