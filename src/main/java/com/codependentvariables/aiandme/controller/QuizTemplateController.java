package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.modules.Router;
import com.codependentvariables.aiandme.modules.View;
import javafx.fxml.FXML;

public class QuizTemplateController {

    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }
}
