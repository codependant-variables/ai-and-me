package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.JavaFXControllerLoader;
import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.controller.DialogueController;
import com.codependentvariables.aiandme.modules.DialogueMessage;
import com.codependentvariables.aiandme.modules.DialogueType;
import com.codependentvariables.aiandme.modules.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DialogueControllerTest extends JavaFXTest {
    private static DialogueController controller;
    private Result result;

    private static class Result {
        Boolean value;
        boolean hasValue = false;

        void set(Boolean value) {
            this.value = value;
            this.hasValue = true;
        }
    }

    @BeforeEach
    public void setupEach() {
        controller = JavaFXControllerLoader.load(View.DIALOGUE);
        result = new Result();
    }

    @Test
    public void background_click_returns_null() {
        controller.initialiseData(new DialogueMessage("My message", DialogueType.MESSAGE, x -> {
            result.set(x);
        }));
        controller.onBackgroundClick();

        assertTrue(result.hasValue);
        assertNull(result.value);
    }

    @Test
    public void yes_click_returns_true() {
        controller.initialiseData(new DialogueMessage("My message", DialogueType.YES_NO, x -> {
            result.set(x);
        }));
        controller.yesRef.fire();

        assertTrue(result.hasValue);
        assertTrue(result.value);
    }

    @Test
    public void no_click_returns_true() {
        controller.initialiseData(new DialogueMessage("My message", DialogueType.YES_NO, x -> {
            result.set(x);
        }));
        controller.noRef.fire();

        assertTrue(result.hasValue);
        assertFalse(result.value);
    }

    @Test
    public void cancel_click_returns_null() {
        controller.initialiseData(new DialogueMessage("My message", DialogueType.MESSAGE, x -> {
            result.set(x);
        }));
        controller.cancelRef.fire();

        assertTrue(result.hasValue);
        assertNull(result.value);
    }
}
