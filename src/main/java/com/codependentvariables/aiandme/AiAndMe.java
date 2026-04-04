package com.codependentvariables.aiandme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class  AiAndMe extends Application {
    // Constants defining the window title and size
    public static final String TITLE = "AI & Me";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AiAndMe.class.getResource("main-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        java.net.URL stylesheetResource = AiAndMe.class.getResource("stylesheet.css");
        if (stylesheetResource != null) {
            String stylesheet = stylesheetResource.toExternalForm();
            scene.getStylesheets().add(stylesheet);
        }

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}