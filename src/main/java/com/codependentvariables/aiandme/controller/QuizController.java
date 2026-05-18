package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import javafx.fxml.FXML;

/**
 * Controller for the quiz view.
 */
public class QuizController {

    /**
     * Navigates back to the home page.
     */
    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }
}
