package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.codependentvariables.aiandme.services.HomeService;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class HomeController {
    @FXML
    private Label checkInStreak;
    @FXML
    private Text lastCheckIn;

    private final HomeService homeService = HomeService.getInstance();

    public void initialize() {
        checkInStreak.setText(homeService.getCheckInStreak());
        lastCheckIn.setText(homeService.getLastCheckInDate());
    }

    public void navigateCheckIn(MouseEvent mouseEvent) {
        Router.navigateLayout(View.CHECK_IN);
    }
}
