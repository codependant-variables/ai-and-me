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
    private static View lastView = View.HOME;
    private static boolean isLastViewLayout = true;

    /**
     * Invoke this method in AppController.initialize to utilise for routing.
     */
    public static void setApp(StackPane stackPane) {
        app = stackPane;
        reset();
    }

    /**
     * Invoke this method in LayoutController.initialize to utilise for routing.
     */
    public static void setLayout(StackPane stackPane) {
        layout = stackPane;
        reset();
    }

    private static void reset() {
        currentAppView = null;
        currentLayoutView = null;
        lastView = View.HOME;
        isLastViewLayout = true;
    }

    /**
     * Places the view into app content (root).
     * @param view View to load.
     * @return Related controller of the loaded view.
     */
    public static Object navigateApp(View view) {
        if (currentAppView == view) {
            return null;
        }

        Object controller = null;

        if (app != null) {
            controller = ViewUtils.loadView(app, view);
        }

        if (view != View.LAYOUT) {
            lastView = currentAppView == View.LAYOUT ? currentLayoutView : currentAppView;
            isLastViewLayout = currentAppView == View.LAYOUT;
        }

        currentAppView = view;

        if (currentAppView != View.LAYOUT) {
            currentLayoutView = null;
        }

        return controller;
    }

    /**
     * Places the view into layout content (with navbar).
     * @param view View to load.
     * @return Related controller of the loaded view.
     */
    public static Object navigateLayout(View view) {
        if (currentAppView != View.LAYOUT) {
            lastView = currentAppView;
            isLastViewLayout = false;
            navigateApp(View.LAYOUT);
        }

        if (currentLayoutView == view) {
            return null;
        }

        Object controller = null;

        if (layout != null) {
            controller = ViewUtils.loadView(layout, view);
        }

        lastView = currentLayoutView;
        isLastViewLayout = true;
        currentLayoutView = view;

        return controller;
    }

    public static View getAppView() {
        return currentAppView;
    }

    public static View getLayoutView() {
        return currentLayoutView;
    }

    /**
     * Navigate to the last loaded view, accounting for if it was in app (root) or layout.
     */
    public static void navigateBack() {
        if (isLastViewLayout) {
            navigateLayout(lastView);
        } else {
            navigateApp(lastView);
        }
    }
}
