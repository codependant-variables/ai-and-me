package com.codependentvariables.aiandme.navigation;

import javafx.scene.layout.StackPane;

/**
 * Router for application root.
 */
public class Router {
    private static StackPane app;
    private static StackPane layout;
    private static View currentAppView;
    private static View currentLayoutView;

    /**
     * Invoke this method in AppController.initialize to utilise for routing.
     */
    public static void setApp(StackPane stackPane) {
        app = stackPane;
    }

    /**
     * Invoke this method in LayoutController.initialize to utilise for routing.
     */
    public static void setLayout(StackPane stackPane) {
        layout = stackPane;
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

        if (currentAppView != View.LAYOUT) {
            currentLayoutView = null;
        }
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
    private static void setLoading(StackPane stackPane) {
        ViewUtils.loadView(stackPane, View.LOADING);
    }
}
