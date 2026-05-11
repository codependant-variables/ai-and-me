package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.navigation.*;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

import java.time.LocalDateTime;

public class CheckInController {
    @FXML
    private Slider useSlider;
    @FXML
    private Slider happinessSlider;
    @FXML
    private Slider dependenceSlider;
    @FXML
    private TextArea commentTextArea;

    private final CheckInService checkInService = CheckInService.getInstance();
    private final AppState appState = AppState.getInstance();

    private CheckIn checkIn;

    @FXML
    public void clickSubmit() {
        checkIn = new CheckIn(0,
                (float) useSlider.getValue(),
                (float) happinessSlider.getValue(),
                (float) dependenceSlider.getValue(),
                commentTextArea.getText(),
                LocalDateTime.now()
        );

        if (appState.getCurrentUser() != null) {
            submitCheckIn();
        } else {
            Dialogue.signupToSave(this::submitCheckIn, this::navigateHome);
        }
    }

    private void submitCheckIn() {
        checkIn.setUserId(appState.getCurrentUser().getId());
        checkInService.submitCheckIn(checkIn);
        Router.navigateLayout(View.HOME);
    }

    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }
}