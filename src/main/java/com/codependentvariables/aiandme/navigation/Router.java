package com.codependentvariables.aiandme.navigation;

import javafx.scene.layout.StackPane;
import javafx.scene.Parent;

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

    public static void navigateApp(View view) {
        if (currentAppView == view) {
            return;
        }

        if (app != null) {
            loadView(app, view);
        }

        lastView = currentAppView;
        currentAppView = view;

        if (currentAppView != View.LAYOUT) {
            currentLayoutView = null;
        }
    }

    public static void navigateLayout(View view) {
        if (currentAppView != View.LAYOUT) {
            navigateApp(View.LAYOUT);
        }

        if (currentLayoutView == view) {
            return;
        }

        if (layout != null) {
            loadView(layout, view);
        }

        lastView = currentLayoutView;
        currentLayoutView = view;
    }

    public static void setLayoutContent(Parent view, View routeView) {
        if (currentAppView != View.LAYOUT) {
            navigateApp(View.LAYOUT);
        }

        if (layout != null) {
            layout.getChildren().setAll(view);
        }

        lastView = currentLayoutView;
        currentLayoutView = routeView;
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

    private static void loadView(StackPane stackPane, View view) {
        setLoading(stackPane);
        ViewUtils.loadView(stackPane, view);
    }

    // TODO: decide if this is needed as it loads fast enough to never see Loading..
    private static void setLoading(StackPane stackPane) {
        ViewUtils.loadView(stackPane, View.LOADING);
    }
}
