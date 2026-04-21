package com.codependentvariables.aiandme.controller;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import javafx.fxml.FXML;

public class HomeController {

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
