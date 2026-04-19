package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LayoutController {
    @FXML
    private BorderPane contentRef;

    @FXML
    public void initialize() {
        Router.setLayout(contentRef);
    }

    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }

    @FXML
    public void navigateQuizLibrary() {
        Router.navigateLayout(View.QUIZ_LIBRARY);
    }

    @FXML
    public void navigateLogin() {
        Router.navigateApp(View.LOGIN);
    }

    @FXML
    public void navigateSignup() {
        Router.navigateApp(View.SIGNUP);
    }

    @FXML
    public void navigateSettings() { Router.navigateApp(View.SETTINGS); }

    @FXML
    public void navigateCheckin() { Router.navigateApp(View.CHECKIN); }

}