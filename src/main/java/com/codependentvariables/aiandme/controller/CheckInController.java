package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.Checkin;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.CheckInService;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CheckInController {
    @FXML private Slider satisfactionSlider;
    @FXML private Slider dependenceSlider;
    @FXML private Slider usageSlider;
    @FXML private TextArea thoughtsTextArea;

    private final CheckInService checkInService = CheckInService.getInstance();

    @FXML
    public void submitCheckIn() {
        Checkin checkin = new Checkin(
                (float) usageSlider.getValue(),
                (float) satisfactionSlider.getValue(),
                (float) dependenceSlider.getValue(),
                LocalDateTime.now()
        );

        checkInService.submitCheckIn(checkin);
        Router.navigateLayout(View.HOME);
    }

    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }

}
