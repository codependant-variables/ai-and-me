package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class LayoutController {
    private final UserService userService = UserService.getInstance();
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
        Image newImage = new Image(AiAndMe.getLogoUrlString());
        logoRef.setImage(newImage);

        // Bind managed to visible so they always match
        loginButton.managedProperty().bind(loginButton.visibleProperty());
        signupButton.managedProperty().bind(signupButton.visibleProperty());
        profileButton.managedProperty().bind(profileButton.visibleProperty());
        logoutButton.managedProperty().bind(logoutButton.visibleProperty());

        setButtonVisibility();
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
    public void navigateLogin() {
        Router.navigateApp(View.LOGIN);
    }

    @FXML
    public void navigateProfile() {
        Router.navigateLayout(View.PROFILE);
    }

    @FXML
    public void handleLogout() {
        // TODO: implement modal dialogue as "Are you sure?"
        userService.logout();
        setButtonVisibility();
        Router.navigateLayout(View.HOME); // In case of seeing sensitive data
    }

    @FXML
    public void navigateSignup() {
        Router.navigateApp(View.SIGNUP);
    }

    @FXML
    public void navigateSettings() {
        Router.navigateApp(View.SETTINGS);
    }

    @FXML
    public void navigateCheckin() {
        Router.navigateApp(View.CHECKIN);
    }

    private void setButtonVisibility() {
        boolean isLoggedIn = appState.getCurrentUser() != null;

        loginButton.setVisible(!isLoggedIn);
        signupButton.setVisible(!isLoggedIn);
        profileButton.setVisible(isLoggedIn);
        logoutButton.setVisible(isLoggedIn);
    }
}