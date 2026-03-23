module com.example.aiandme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.aiandme to javafx.fxml;
    exports com.example.aiandme;
    exports com.example.aiandme.controller;
    opens com.example.aiandme.controller to javafx.fxml;
    exports com.example.aiandme.model;
    opens com.example.aiandme.model to javafx.fxml;
}