package com.codependentvariables.aiandme;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import com.codependentvariables.aiandme.model.SqliteConnection;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.navigation.ViewUtils;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class  AiAndMe extends Application {
    public static final String TITLE = "AI & Me";
    public static final Double WIDTH = 800.0;
    public static final Double HEIGHT = 600.0;

    /*public static void crossFadeTransition(Scene newScene, Scene oldScene) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), oldScene);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.play();

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newScene);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }*/


    @Override
    public void start(Stage stage) {
        SqliteConnection.getConnection(); // Frontload db connection load time at app startup

        stage.setTitle(TITLE);
        stage.setMaximized(true);
        // TODO: decide if we want to disable resizing or enforce max height and width on startup

        URL stylesheetResource = AiAndMe.class.getResource("stylesheet.css");
        Application.setUserAgentStylesheet((new NordLight()).getUserAgentStylesheet()); //<<use this to replace old stylesheet

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