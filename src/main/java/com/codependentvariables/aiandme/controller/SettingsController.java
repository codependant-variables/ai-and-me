package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.Svg;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class SettingsController {
    private final AppState appState = AppState.getInstance();
    private final UserService userService = UserService.getInstance();

    @FXML
    private void initialize() {
        setThemeContent(appState.getIsDarkMode());
    }

    @FXML
    public SVGPath themeToggleIcon;

    @FXML
    public void switchTheme() {
        boolean isDarkMode = appState.getIsDarkMode();
        appState.setIsDarkMode(!isDarkMode);
        setThemeContent(!isDarkMode);


        initialize();
        userService.updateCurrentUser();
    }

    private void setThemeContent(boolean isDarkMode) {
        themeToggleIcon.setContent(isDarkMode ? Svg.SUN : Svg.MOON);
        themeToggleIcon.setFill(isDarkMode ? Color.YELLOW : Color.MEDIUMSLATEBLUE);
    }

    @FXML
    public void switchOrientation() {
        boolean isVertical = appState.getIsVertical();
        appState.setIsVertical(!isVertical);
        // TODO: do something when orientation changes
        userService.updateCurrentUser();
    }

    @FXML
    public void navigateBack() {
        Router.navigateBack();
    }
}