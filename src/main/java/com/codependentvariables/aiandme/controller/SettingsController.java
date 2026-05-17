package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.Icon;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class SettingsController {
    private final AppState appState = AppState.getInstance();
    private final UserService userService = UserService.getInstance();

    @FXML
    private GridPane background;


    @FXML
    private void initialize() {
        themeIcon.contentProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? Icon.SUN : Icon.MOON));
        themeIcon.fillProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? Color.YELLOW : Color.MEDIUMSLATEBLUE));
        background.styleProperty().bind(appState.getObservableIsDarkMode().map(isDarkMode -> isDarkMode ? "-fx-background-color: #202430;" : "-fx-background-color: #f0edef;"));
    }

    @FXML
    public SVGPath themeIcon;

    @FXML
    public void switchTheme() {
        boolean isDarkMode = appState.getIsDarkMode();
        appState.setIsDarkMode(!isDarkMode);
        userService.updateCurrentUser();
        System.out.println("Message");

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

    @FXML
    public void revertToDefaultSettings() {
        // TODO: implement modal dialogue
        appState.setIsDarkMode(false);
        appState.setIsVertical(false);
        userService.updateCurrentUser();
    }
}