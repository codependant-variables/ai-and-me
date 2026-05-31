package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.modules.dialogue.Dialogue;
import com.codependentvariables.aiandme.modules.router.Router;
import com.codependentvariables.aiandme.modules.router.View;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.modules.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

import java.time.LocalDateTime;

/**
 * Controller for the daily check-in view.
 * Handles collecting and submitting user check-in data.
 */
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

    /**
     * Creates and submits a new check-in.
     * Prompts guest users to sign up before saving.
     */
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

    /**
     * Saves the check-in for the current user
     * and returns to the home page.
     */
    private void submitCheckIn() {
        checkIn.setUserId(appState.getCurrentUser().getId());
        checkInService.submitCheckIn(checkIn);
        Router.navigateLayout(View.HOME);
    }

    /**
     * Navigates back to the home page.
     */
    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }
}