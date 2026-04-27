package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SettingsControllerTest {

    @Test
    public void dark_mode_test() {
        assertTrue(AppState.getInstance().getIsDarkMode());
    }

    @Test
    public void light_mode_test() {
        assertFalse(AppState.getInstance().getIsDarkMode());
    }

    @Test
    public void orientation_vertical_test() {
        assertTrue(AppState.getInstance().getIsVertical());

    }

    @Test
    public void orientation_horizontal_test() {
        assertFalse(AppState.getInstance().getIsVertical());

    }

}
