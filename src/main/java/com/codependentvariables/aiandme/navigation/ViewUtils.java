package com.codependentvariables.aiandme.navigation;

import com.codependentvariables.aiandme.AiAndMe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;

public final class ViewUtils {
    private ViewUtils() {} // Prevent instantiation

    /**
     * Loads view onto the provided pane
     * @param stackPane StackPane to load view into
     * @param view View to load
     */
    public static void loadView(StackPane stackPane, View view) {
        String resourceName = getResourceName(view);
        URL resourceUrl = AiAndMe.class.getResource(resourceName);
        if (resourceUrl == null) {
            throw new RuntimeException("Resource not found for view: " + view.toString());
        }

        try {
            Node loadedNode = FXMLLoader.load(resourceUrl);
            stackPane.getChildren().setAll(loadedNode);
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
            case View.CHECK_IN -> "checkin.fxml";
            case View.CHECK_IN_HISTORY -> "checkin-history.fxml";
            case View.HOME -> "home.fxml";
            case View.LAYOUT -> "layout.fxml";
            case View.LOADING -> "loading.fxml";
            case View.LOGIN -> "login.fxml";
            case View.PROFILE -> "profile.fxml";
            case View.QUIZ_ATTEMPT -> "quiz-attempt.fxml";
            case View.QUIZ_ATTEMPT_RESULTS -> "quiz-attempt-results.fxml";
            case View.QUIZ_LIBRARY -> "quiz-library.fxml";
            case View.SETTINGS -> "settings.fxml";
            case View.SETUP_MFA -> "setup-mfa.fxml";
            case View.SIGNUP -> "signup.fxml";
            default -> throw new RuntimeException("View translation not implemented: " + view.toString());
        };
    }
}
