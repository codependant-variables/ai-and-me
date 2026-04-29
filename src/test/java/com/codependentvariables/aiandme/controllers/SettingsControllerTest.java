package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.controller.SettingsController;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SettingsControllerTest {

    //public SettingsController testController;
    public AppState testAppState = AppState.getInstance();;

    @Test
    public void switch_theme_on_click() {
        boolean testAppStateTheme = testAppState.getIsDarkMode();
        System.out.println(testAppStateTheme);
        testAppState.setIsDarkMode(!testAppStateTheme);
        System.out.println(testAppStateTheme);
        boolean testAppStateTheme2 = testAppState.getIsDarkMode();
        System.out.println(testAppStateTheme);
        System.out.println(testAppStateTheme2);
        assertNotEquals(testAppStateTheme,testAppStateTheme2);
    }

    @Test
    public void switch_orientation_on_click() {
        boolean testAppStateOrientation = testAppState.getIsVertical();
        testAppState.setIsVertical(!testAppStateOrientation);
        boolean testAppStateOrientation2 = testAppState.getIsVertical();
        assertNotEquals(testAppStateOrientation,testAppStateOrientation2);
    }

}
