package com.codependentvariables.aiandme.modules.router;

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

    public static Object navigateApp(View view) {
        if (currentAppView == view) {
            return null;
        }

        Object controller = null;

        if (app != null) {
            controller = ViewUtils.loadView(app, view);
        }

        lastView = currentAppView;
        currentAppView = view;
        isLastViewLayout = false;

        if (currentAppView != View.LAYOUT) {
            currentLayoutView = null;
        }

        return controller;
    }

    public static Object navigateLayout(View view) {
        if (currentAppView != View.LAYOUT) {
            navigateApp(View.LAYOUT);
        }

        if (currentLayoutView == view) {
            return null;
        }

        Object controller = null; // can hold any obj

        if (layout != null) {
            controller = ViewUtils.loadView(layout, view);
        }

        lastView = currentLayoutView;
        currentLayoutView = view;
        isLastViewLayout = true;

        return controller;
    }

    public static View getAppView() {
        return currentAppView;
    }

    public static View getLayoutView() {
        return currentLayoutView;
    }

    public static void navigateBack() {
        if (isLastViewLayout) {
            navigateLayout(lastView);
        } else {
            navigateApp(lastView);
        }
    }
}
