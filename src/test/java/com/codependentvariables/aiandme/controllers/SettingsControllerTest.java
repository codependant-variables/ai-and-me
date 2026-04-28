package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.controller.SettingsController;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SettingsControllerTest {

    SettingsController testController = new SettingsController();

    @Test
    public void switch_theme_on_click() {
        boolean isDarkMode = AppState.getInstance().getIsDarkMode();
        testController.switchTheme();
        assertNotEquals(isDarkMode, AppState.getInstance().getIsDarkMode());
    }

    @Test
    public void switch_orientation_on_click() {
        boolean isVertical = AppState.getInstance().getIsVertical();
        testController.switchOrientation();
        assertNotEquals(isVertical, AppState.getInstance().getIsVertical());
    }

}
