package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.Svg;
import com.codependentvariables.aiandme.model.Checkin;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.time.LocalDateTime;

public class CheckInController {
    @FXML private Slider satisfactionSlider;
    @FXML private Slider dependenceSlider;
    @FXML private Slider usageSlider;
    @FXML private TextArea commentTextArea;
    @FXML public SVGPath homeSvg;

    private final CheckInService checkInService = CheckInService.getInstance();
    private final AppState appState = AppState.getInstance();

    @FXML
    public void initialize() {
        homeSvg.setContent(Svg.HOME);
        homeSvg.setFill(appState.getIsDarkMode() ? Color.WHITE : Color.BLACK);
    }

    @FXML
    public void submitCheckIn() {
        Checkin checkin = new Checkin(
                (float) usageSlider.getValue(),
                (float) satisfactionSlider.getValue(),
                (float) dependenceSlider.getValue(),
                (String) commentTextArea.getText(),
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