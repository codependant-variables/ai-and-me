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
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        showLandingScreen();
    }

    public static void showLandingScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AiAndMe.class.getResource("landing-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setTitle(TITLE);
        primaryStage.show();
    }

    public static void showLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AiAndMe.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void showSignUp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AiAndMe.class.getResource("signup.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign Up");
        primaryStage.show();
    }

    public static void showUserView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AiAndMe.class.getResource("guest-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        java.net.URL stylesheetResource = AiAndMe.class.getResource("stylesheet.css");
        if (stylesheetResource != null) {
            String stylesheet = stylesheetResource.toExternalForm();
            scene.getStylesheets().add(stylesheet);
        }

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}