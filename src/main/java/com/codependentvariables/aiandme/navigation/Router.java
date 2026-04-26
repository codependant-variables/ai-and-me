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
    private static boolean isCurrentViewLayout;
    private static View lastView = View.HOME;
    private static boolean isLastViewLayout = true;

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

    public static boolean hasApp() {
        return app != null;
    }

    public static void navigateApp(View view) {
        if (app == null) {
            throw new RuntimeException("Router.app BorderPane not initialised.");
        }

        if (currentAppView == view) {
            return;
        }

        loadView(app, view);

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

        loadView(layout, view);
    }

    public static void navigateBack() {
        if (isLastViewLayout) {
            navigateLayout(lastView);
        } else {
            navigateApp(lastView);
        }
    }

    private static void loadView(StackPane stackPane, View view) {
        setLoading(stackPane);
        ViewUtils.loadView(stackPane, view);

        isLastViewLayout = isCurrentViewLayout;
        if (isLastViewLayout) {
            lastView = currentLayoutView;
        } else {
            lastView = currentAppView;
        }
        isCurrentViewLayout = stackPane == layout;
        if (isCurrentViewLayout) {
            currentLayoutView = view;
        } else {
            currentAppView = view;
        }
    }

    // TODO: decide if this is needed as it loads fast enough to never see Loading..
    private static void setLoading(StackPane stackPane) {
        ViewUtils.loadView(stackPane, View.LOADING);
    }
}
