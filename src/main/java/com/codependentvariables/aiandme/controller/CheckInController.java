package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.Svg;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class CheckInController {
    private final AppState appState = AppState.getInstance();

    @FXML
    public SVGPath homeSvg;

    @FXML
    public void initialize() {
        homeSvg.setContent(Svg.HOME);
        homeSvg.setFill(appState.getIsDarkMode() ? Color.WHITE : Color.BLACK);
    }

    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }
}
