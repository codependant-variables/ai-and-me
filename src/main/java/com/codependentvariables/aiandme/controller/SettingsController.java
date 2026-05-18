package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.Icon;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 * Controller for the settings page.
 * Handles application appearance and preference settings.
 */
public class SettingsController {
    private final AppState appState = AppState.getInstance();
    private final UserService userService = UserService.getInstance();

    @FXML
    private GridPane background;

    @FXML
    public SVGPath themeIcon;

    /**
     * Initialises theme defaults and background styling.
     */
    @FXML
    private void initialize() {
        // Change icon depending on current theme
        themeIcon.contentProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? Icon.SUN : Icon.MOON));
        themeIcon.fillProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? Color.YELLOW : Color.MEDIUMSLATEBLUE));
        // Update background colour for dark/light mode
        background.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: #202430;" : "-fx-background-color: #f0edef;"));
    }

    /**
     * Toggles dark mode on or off.
     */
    @FXML
    public void switchTheme() {
        boolean isDarkMode = appState.getIsDarkMode();
        appState.setIsDarkMode(!isDarkMode);
        userService.updateCurrentUser();
        System.out.println("Message");

    }

    /**
     * Toggles application orientation settings.
     */
    @FXML
    public void switchOrientation() {
        boolean isVertical = appState.getIsVertical();
        appState.setIsVertical(!isVertical);
        // TODO: apply orientation changes to UI
        userService.updateCurrentUser();
    }

    /**
     * Returns to the previous page.
     */
    @FXML
    public void navigateBack() {
        Router.navigateBack();
    }

    /**
     * Restores default application settings.
     */
    @FXML
    public void revertToDefaultSettings() {
        // TODO: implement modal dialogue
        appState.setIsDarkMode(false);
        appState.setIsVertical(false);
        userService.updateCurrentUser();
    }
}