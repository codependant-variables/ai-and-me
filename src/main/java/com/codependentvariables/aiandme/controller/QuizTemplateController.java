package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.modules.Router;
import com.codependentvariables.aiandme.modules.View;
import javafx.fxml.FXML;

/**
 * Controller for the quiz template view.
 */
public class QuizTemplateController {

    /**
     * Navigates back to the home page.
     */
    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }
}
