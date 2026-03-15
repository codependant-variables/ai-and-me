package com.example.cab302groupproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        java.net.URL stylesheetResource = HelloApplication.class.getResource("stylesheet.css");
        if (stylesheetResource != null) {
            String stylesheet = stylesheetResource.toExternalForm();
            scene.getStylesheets().add(stylesheet);
        }

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
