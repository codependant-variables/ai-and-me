module com.example.cab302groupproject {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.cab302groupproject to javafx.fxml;
    exports com.example.cab302groupproject;
    exports com.example.cab302groupproject.controller;
    opens com.example.cab302groupproject.controller to javafx.fxml;
}