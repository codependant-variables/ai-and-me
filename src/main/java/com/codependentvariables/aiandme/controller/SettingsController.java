package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.Svg;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

// Added imports
import com.codependentvariables.aiandme.AiAndMe;
import static javafx.application.Application.setUserAgentStylesheet;

public class SettingsController {
    // Removed initialization of variables because initialization can be done for testing or mock, not for the controller
    private final AppState appState;
    private final UserService userService;

    // Added constructors
    // Constructor for real app
    public SettingsController() {
        this(AppState.getInstance(), UserService.getInstance());
    }

    // Constructor for tests to use
    SettingsController(AppState appState, UserService userService) {
        this.appState = appState;
        this.userService = userService;
    }


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

        // Added this to run when switch theme button clicked
        setUserAgentStylesheet(
                !isDarkMode ? AiAndMe.darkModeStylesheet : AiAndMe.lightModeStylesheet
        );
        setThemeContent(!isDarkMode);


        initialize();
        userService.updateCurrentUser();
    }

    private void setThemeContent(boolean isDarkMode) {
        // Added null check but this might not be needed, just to prevent tests from crashing
        if (themeToggleIcon == null) {
            return;
        }

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