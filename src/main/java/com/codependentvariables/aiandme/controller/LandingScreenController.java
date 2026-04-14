package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import javafx.fxml.FXML;

public class LandingScreenController {
    @FXML
    private void handleGoToGuest() {
        AiAndMe.showUserView();
    }

    @FXML
    private void handleGoToSignUp() {
        AiAndMe.showSignUp();
    }

    @FXML
    private void handleGoToLogin() {
        AiAndMe.showLogin();
    }
}
