package com.codependentvariables.aiandme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class  AiAndMe extends Application {
    // Constants defining the window title and size
    public static final String TITLE = "AI & Me";
    public static final Double width = 800.0;
    public static final Double height = 600.0;
    public static Stage primaryStage;
    private static String stylesheetUrl;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setMaximized(true);
        // TODO: decide if we want to disable resizing or enforce max height and width on startup

        java.net.URL stylesheetResource = AiAndMe.class.getResource("stylesheet.css");
        if (stylesheetResource != null) {
            stylesheetUrl = stylesheetResource.toExternalForm();
        }

        showLandingScreen();
    }

    private static void changeScene(String resourceName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AiAndMe.class.getResource(resourceName));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            if (stylesheetUrl != null) {
                scene.getStylesheets().add(stylesheetUrl);
            }

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            throw new RuntimeException(String.format("Could not load resource: %s", resourceName), ex);
        }
    }

    public static void showLandingScreen() {
        changeScene("landing-screen.fxml");
    }

    public static void showLogin() {
        changeScene("login.fxml");
    }

    public static void showSignUp() {
        changeScene("signup.fxml");
    }

    public static void showUserView() {
        changeScene("guest-view.fxml");
    }

    public static void showQuizLibrary() {
        changeScene("quiz-library.fxml");
    }
}