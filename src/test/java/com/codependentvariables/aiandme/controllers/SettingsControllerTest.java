package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.controller.SettingsController;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SettingsControllerTest {

    public SettingsController testController = new SettingsController();
    public AppState testState = new AppState();

    @Test
    public void switch_theme_on_click() {
        boolean isDarkMode = testState.getIsDarkMode();
        System.out.println("isDarkMode: " + isDarkMode);
        testController.switchTheme();
        System.out.println("*click*");
        System.out.println("isDarkMode: " + isDarkMode);
        testState.setIsDarkMode(!isDarkMode);
        System.out.println("isDarkMode: " + isDarkMode);
        assertNotEquals(isDarkMode, testState.getIsDarkMode());
        System.out.println("isDarkMode: " + isDarkMode);
    }

    @Test
    public void switch_orientation_on_click() {
        boolean isVertical = testState.getIsVertical(); //bool from initial state
        testController.switchOrientation(); // *click*
        testState.setIsVertical(!isVertical);
        assertNotEquals(isVertical, testState.getIsVertical()); //is it different now?
    }

}
