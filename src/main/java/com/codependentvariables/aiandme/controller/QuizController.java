package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import javafx.fxml.FXML;

public class QuizController {

    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }
}
