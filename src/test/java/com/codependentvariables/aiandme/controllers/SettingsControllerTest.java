package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.JavaFXControllerLoader;
import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.controller.SettingsController;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SettingsControllerTest extends JavaFXTest {
    private static SettingsController settingsController;
    private static AppState appState;

    @BeforeAll
    public static void setup() {
        appState = AppState.getInstance();
        settingsController = JavaFXControllerLoader.load(View.SETTINGS);
    }

    @Test
    public void switch_theme_once() {
        boolean isDarkMode = appState.getIsDarkMode();
        settingsController.switchTheme();
        assertNotEquals(isDarkMode, appState.getIsDarkMode());
    }

    @Test
    public void switch_theme_twice() {
        boolean isDarkMode = appState.getIsDarkMode();
        settingsController.switchTheme();
        settingsController.switchTheme();
        assertEquals(isDarkMode, appState.getIsDarkMode());
    }
}
