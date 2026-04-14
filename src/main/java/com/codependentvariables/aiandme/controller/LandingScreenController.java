package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import javafx.fxml.FXML;

public class LandingScreenController {
    @FXML
    private void handleGoToGuest() {
        try {
            AiAndMe.showUserView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToSignUp() {
        try {
            AiAndMe.showSignUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToLogin() {
        try {
            AiAndMe.showLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
