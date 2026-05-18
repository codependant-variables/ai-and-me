package com.codependentvariables.aiandme;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class JavaFXTest {

    private static boolean initialized = false;

    @BeforeAll
    static void initJavaFX() {

        if (initialized) {
            return;
        }

        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // JavaFX toolkit already initialized
        }

        initialized = true;
    }
}