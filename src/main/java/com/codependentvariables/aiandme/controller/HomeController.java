package com.codependentvariables.aiandme.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.codependentvariables.aiandme.services.HomeService;

public class HomeController {
    @FXML
    private Label checkInStreak;

    private final HomeService homeService = HomeService.getInstance();

    public void initialize() {
        checkInStreak.setText(homeService.getCheckInStreak());
    }
}
