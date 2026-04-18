package com.codependentvariables.aiandme.navigation;

import com.codependentvariables.aiandme.AiAndMe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

/**
 * Router for application root.
 */
public class Router {
    private static BorderPane app;
    private static BorderPane layout;
    private static View currentAppView;
    private static View currentLayoutView;

    /**
     * Invoke this method in AppController.initialize to utilise for routing.
     */
    public static void setApp(BorderPane borderPane) {
        app = borderPane;
    }

    /**
     * Invoke this method in LayoutController.initialize to utilise for routing.
     */
    public static void setLayout(BorderPane borderPane) {
        layout = borderPane;
    }

    public static void navigateApp(View view) {
        if (app == null) {
            throw new RuntimeException("Router.app BorderPane not initialised.");
        }

        if (currentAppView == view) {
            return;
        }

        setLoading(app);
        ViewUtils.loadView(app, view);
        currentAppView = view;
    }

    public static void navigateLayout(View view) {
        if (currentAppView != View.LAYOUT) {
            navigateApp(View.LAYOUT);
        }

        if (layout == null) {
            throw new RuntimeException("Router.layout BorderPane not initialised.");
        }

        if (currentLayoutView == view) {
            return;
        }

        setLoading(layout);
        ViewUtils.loadView(layout, view);
        currentLayoutView = view;
    }

    // TODO: decide if this is needed as it loads fast enough to never see Loading..
    private static void setLoading(BorderPane borderPane) {
        ViewUtils.loadView(borderPane, View.LOADING);
    }
}
