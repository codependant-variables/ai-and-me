package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.controller.SettingsController;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SettingsControllerTest {

    private SettingsController testController;
    private AppState testState;

    @Test
    public void switch_theme_on_click() {
        testController = new SettingsController();
        testState = new AppState();
        boolean isDarkMode = testState.getIsDarkMode();
        testController.switchTheme();
        assertNotEquals(isDarkMode, testState.getIsDarkMode());
    }

    @Test
    public void switch_orientation_on_click() {
        testController = new SettingsController();
        testState = new AppState();
        boolean isVertical = testState.getIsVertical();
        testController.switchOrientation();
        assertNotEquals(isVertical, testState.getIsVertical());
    }

}
