package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.JavaFXControllerLoader;
import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.controller.SettingsController;
import com.codependentvariables.aiandme.modules.View;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SettingsControllerTest extends JavaFXTest {
    private static AppState appState;
    private static SettingsController controller;

    @BeforeAll
    public static void setup() {
        appState = AppState.getInstance();
    }

    @BeforeEach
    public void setupEach() {
        controller = JavaFXControllerLoader.load(View.SETTINGS);
    }

    @Test
    public void switch_theme_once() {
        boolean isDarkMode = appState.getIsDarkMode();
        controller.switchTheme();
        assertNotEquals(isDarkMode, appState.getIsDarkMode());
    }

    @Test
    public void switch_theme_twice() {
        boolean isDarkMode = appState.getIsDarkMode();
        controller.switchTheme();
        controller.switchTheme();
        assertEquals(isDarkMode, appState.getIsDarkMode());
    }
}
