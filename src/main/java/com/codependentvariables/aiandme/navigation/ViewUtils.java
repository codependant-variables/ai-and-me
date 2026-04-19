package com.codependentvariables.aiandme.navigation;

import com.codependentvariables.aiandme.AiAndMe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public final class ViewUtils {
    private ViewUtils() {} // Prevent instantiation

    /**
     * Loads view onto the provided pane
     * @param borderPane BorderPane to load view into
     * @param view View to load
     */
    public static void loadView(BorderPane borderPane, View view) {
        String resourceName = getResourceName(view);
        URL resourceUrl = AiAndMe.class.getResource(resourceName);
        if (resourceUrl == null) {
            throw new RuntimeException("Resource not found for view: " + view.toString());
        }

        try {
            Node loadedNode = FXMLLoader.load(resourceUrl);
            borderPane.setCenter(loadedNode);
        } catch (IOException ex) {
            throw new RuntimeException(String.format("Could not load resource: %s", resourceName), ex);
        }
    }

    /**
     * Gets resource name for a view
     * @return Resource name
     */
    public static String getResourceName(View view) {
        return switch (view) {
            case View.APP -> "app.fxml";
            case View.HOME -> "home.fxml";
            case View.LAYOUT -> "layout.fxml";
            case View.LOADING -> "loading.fxml";
            case View.LOGIN -> "login.fxml";
            case View.QUIZ_LIBRARY -> "quiz-library.fxml";
            case View.SIGNUP -> "signup.fxml";
            case View.SETTINGS -> "settings.fxml";
            case View.CHECKIN -> "checkin.fxml";
            default -> throw new RuntimeException("View translation not implemented: " + view.toString());
        };
    }
}
