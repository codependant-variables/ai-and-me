package com.codependentvariables.aiandme;

import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.navigation.ViewUtils;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;

public class JavaFXControllerLoader {
    /**
     * Load a controller with its default constructor.
     * @param view View to load.
     * @param <T> Controller type.
     */
    public static <T> T load(View view) {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXControllerLoader.class.getResource(ViewUtils.getResourceName(view)));
            loader.load();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("FXML load failed", e);
        }
    }

    /**
     * Load a controller with the provided factory. Intended for constructing controllers with mocks.
     * @param view View to load.
     * @param factory Factory that returns a controller.
     * @param <T> Controller type.
     */
    public static <T> T load(View view, Callback<Class<?>, Object> factory) {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXControllerLoader.class.getResource(ViewUtils.getResourceName(view)));
            loader.setControllerFactory(factory);
            loader.load();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("FXML load failed", e);
        }
    }
}