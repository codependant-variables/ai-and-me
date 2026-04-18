package com.codependentvariables.aiandme;

import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.navigation.ViewUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class  AiAndMe extends Application {
    public static final String TITLE = "AI & Me";
    public static final Double WIDTH = 800.0;
    public static final Double HEIGHT = 600.0;

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);
        stage.setMaximized(true);
        // TODO: decide if we want to disable resizing or enforce max height and width on startup

        URL stylesheetResource = AiAndMe.class.getResource("stylesheet.css");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AiAndMe.class.getResource(ViewUtils.getResourceName(View.APP)));
            Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
            if (stylesheetResource != null) {
                scene.getStylesheets().add(stylesheetResource.toExternalForm());
            }

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException("Could not load AppController.", ex);
        }
    }
}