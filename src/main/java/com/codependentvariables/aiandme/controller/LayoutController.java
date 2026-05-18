package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.modules.Dialogue;
import com.codependentvariables.aiandme.modules.Router;
import com.codependentvariables.aiandme.modules.Toast;
import com.codependentvariables.aiandme.modules.ToastMessageType;
import com.codependentvariables.aiandme.modules.View;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

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

    @FXML
    public void initialize() {
        Router.setLayout(contentRef);

        logoRef.imageProperty().bind(appState.getObservableIsDarkMode().map(isDark -> new Image(isDark ? AiAndMe.darkLogoUrlString : AiAndMe.lightLogoUrlString)));

        loginButton.visibleProperty().bind(appState.getObservableCurrentUser().isNull());
        signupButton.visibleProperty().bind(appState.getObservableCurrentUser().isNull());
        profileButton.visibleProperty().bind(appState.getObservableCurrentUser().isNotNull());
        logoutButton.visibleProperty().bind(appState.getObservableCurrentUser().isNotNull());

        // Bind managed to visible so they always match
        loginButton.managedProperty().bind(loginButton.visibleProperty());
        signupButton.managedProperty().bind(signupButton.visibleProperty());
        profileButton.managedProperty().bind(profileButton.visibleProperty());
        logoutButton.managedProperty().bind(logoutButton.visibleProperty());
    }

    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }

    @FXML
    public void navigateQuizLibrary() {
        Router.navigateLayout(View.QUIZ_LIBRARY);
    }

    @FXML
    public void navigatePuzzleLibrary() {
        Router.navigateLayout(View.PUZZLE_LIBRARY);
    }

    @FXML
    public void navigateLogin() {
        Router.navigateApp(View.LOGIN);
    }

    @FXML
    public void navigateProfile() {
        Router.navigateLayout(View.PROFILE);
    }

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

    @FXML
    public void navigateSignup() {
        Router.navigateApp(View.SIGNUP);
    }

    @FXML
    public void navigateSettings() { Router.navigateLayout(View.SETTINGS); }

    public void navigateCheckin(ActionEvent actionEvent) {
    }
}