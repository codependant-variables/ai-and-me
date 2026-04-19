package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import static javafx.application.Application.setUserAgentStylesheet;

public class HomeController {
    private final AppState appState = AppState.getInstance();

    @FXML
    private void navigateQuizLibrary() {
        Router.navigateLayout(View.QUIZ_LIBRARY);
    }

    @FXML
    private void navigateSignup() {
        Router.navigateApp(View.SIGNUP);
    }

    @FXML
    private void navigateLogin() {
        Router.navigateApp(View.LOGIN);
    }
}
