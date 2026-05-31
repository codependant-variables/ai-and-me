package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.modules.dialogue.Dialogue;
import com.codependentvariables.aiandme.modules.router.Router;
import com.codependentvariables.aiandme.modules.toast.Toast;
import com.codependentvariables.aiandme.modules.toast.ToastMessageType;
import com.codependentvariables.aiandme.modules.router.View;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.modules.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Controller for the main application layout and navigation bar.
 * Handles navigation between views and updates UI elements
 * based on the current application state.
 */
public class LayoutController {
    private final UserService userService = UserService.getInstance();
    private final CheckInService checkInService = CheckInService.getInstance();
    private final AppState appState = AppState.getInstance();

    @FXML
    public ImageView logoRef;
    @FXML
    public Button loginButton;
    @FXML
    public Button signupButton;
    @FXML
    public Button profileButton;
    @FXML
    public Button logoutButton;

    @FXML
    private StackPane contentRef;

    /**
     * Initialises layout bindings and navigation state.
     */
    @FXML
    public void initialize() {
        Router.setLayout(contentRef);

        logoRef.imageProperty().bind(appState.getObservableIsDarkMode().map(isDark -> new Image(isDark ? AiAndMe.darkLogoUrlString : AiAndMe.lightLogoUrlString)));

        // Show login/signup only when logged out
        loginButton.visibleProperty().bind(appState.getObservableCurrentUser().isNull());
        signupButton.visibleProperty().bind(appState.getObservableCurrentUser().isNull());

        // Show profile and logout only when logged in
        profileButton.visibleProperty().bind(appState.getObservableCurrentUser().isNotNull());
        logoutButton.visibleProperty().bind(appState.getObservableCurrentUser().isNotNull());

        // Bind managed to visible so they always match
        loginButton.managedProperty().bind(loginButton.visibleProperty());
        signupButton.managedProperty().bind(signupButton.visibleProperty());
        profileButton.managedProperty().bind(profileButton.visibleProperty());
        logoutButton.managedProperty().bind(logoutButton.visibleProperty());
    }

    /** Navigates to the home page. */
    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }

    /** Navigates to the quiz library page. */
    @FXML
    public void navigateQuizLibrary() {
        Router.navigateLayout(View.QUIZ_LIBRARY);
    }

    /** Navigates to the puzzle library page. */
    @FXML
    public void navigatePuzzleLibrary() {
        Router.navigateLayout(View.PUZZLE_LIBRARY);
    }

    /** Navigates to the login page. */
    @FXML
    public void navigateLogin() {
        Router.navigateApp(View.LOGIN);
    }

    /** Navigates to the profile page. */
    @FXML
    public void navigateProfile() {
        Router.navigateLayout(View.PROFILE);
    }

    /**
     * Logs the current user out after confirmation.
     */
    @FXML
    public void handleLogout() {
        Dialogue.confirmation(result -> {
            if (result == null || !result) {
                return;
            }

            userService.logout();
            Router.navigateLayout(View.HOME); // In case of seeing sensitive data
            Toast.addMessage("Logged Out", "Goodbye!", ToastMessageType.INFORMATION);
        });
    }

    /** Navigates to the signup page. */
    @FXML
    public void navigateSignup() {
        Router.navigateApp(View.SIGNUP);
    }

    /** Navigates to the settings page. */
    @FXML
    public void navigateSettings() {
        Router.navigateLayout(View.SETTINGS);
    }

    /** Navigates to the check in page. */
    public void navigateCheckin() {
        Router.navigateLayout(View.CHECK_IN);
    }
}