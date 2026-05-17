package com.codependentvariables.aiandme;

import javafx.application.Platform;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class JavaFXTest {
    static {
        if (!Platform.isFxApplicationThread()) {
            try {
                CountDownLatch latch = new CountDownLatch(1);
                Platform.startup(latch::countDown);
                if (!latch.await(5, TimeUnit.SECONDS)) {
                    throw new RuntimeException("JavaFX Toolkit failed to start.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
