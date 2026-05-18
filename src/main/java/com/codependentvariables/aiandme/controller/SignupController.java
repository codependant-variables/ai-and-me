package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.Icon;
import com.codependentvariables.aiandme.navigation.*;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import com.codependentvariables.aiandme.validation.FormValidator;
import com.codependentvariables.aiandme.validation.ValidationEntry;
import com.codependentvariables.aiandme.validation.validators.*;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 * Controller for the signup page.
 * Handles user registration and account creation.
 */
public class SignupController {
    @FXML
    public ImageView logoRef;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordTextField;
    @FXML
    public SVGPath viewPasswordIcon;

    private final UserService userService = UserService.getInstance();
    private final AppState appState = AppState.getInstance();

    private Runnable postSignupCallback;
    private String cancelCallbackMessage;

    /**
     * Initialises theme defaults and password visibility.
     */
    @FXML
    private void initialize() {
        var darkModeObservable = appState.getObservableIsDarkMode();
        // Update logo depending on dark/light mode
        logoRef.imageProperty().bind(darkModeObservable.map(isDark -> new Image(isDark ? AiAndMe.darkLogoUrlString : AiAndMe.lightLogoUrlString)));
        viewPasswordIcon.fillProperty().bind(darkModeObservable.map(isDark -> isDark ? Color.WHITE : Color.BLACK));

        // Toggle between visible and hidden password fields
        passwordTextField.visibleProperty().bind(passwordField.visibleProperty().not());
        viewPasswordIcon.contentProperty().bind(passwordField.visibleProperty().map(visible -> visible ? Icon.OPEN_EYE : Icon.CLOSED_EYE));

        passwordField.textProperty().bindBidirectional(passwordTextField.textProperty());
        passwordField.managedProperty().bind(passwordField.visibleProperty());
        passwordTextField.setOnAction(onMousePressed -> toggleViewPassword());
        passwordTextField.managedProperty().bind(passwordTextField.visibleProperty());
    }

    /**
     * Sets callback actions used after signup or cancellation.
     *
     * @param postSignupCallback action to run after signup
     * @param cancelCallbackMessage confirmation message text
     */
    public void initialiseCallback(Runnable postSignupCallback, String cancelCallbackMessage) {
        this.postSignupCallback = postSignupCallback;
        this.cancelCallbackMessage = cancelCallbackMessage;
    }

    /**
     * Toggles password visibility.
     */
    @FXML
    private void toggleViewPassword() {
        passwordField.setVisible(!passwordField.isVisible());
    }

    /**
     * Validates signup form input.
     */
    private final FormValidator signupValidator = new FormValidator(
            new ValidationEntry<>(
                    () -> this.nameField.getText(),
                    "Name",
                    new StringNotEmptyValidator()
            ),
            new ValidationEntry<>(
                    () -> this.emailField.getText(),
                    "Email",
                    new EmailValidator(),
                    new DynamicValidator<>((value, display) -> userService.isUniqueEmail(value) ? null : String.format("%s is already in use.", display))
            ),
            new ValidationEntry<>(
                    () -> this.passwordField.getText(),
                    "Password",
                    new PasswordValidator()
            )
    );

    /**
     * Attempts to create a new user account.
     */
    @FXML
    private void onSignup() {
        if (!signupValidator.validate()) {
            return;
        }

        userService.signup(this.nameField.getText(), this.emailField.getText(), this.passwordField.getText());
        Toast.addMessage("Woohoo!", "Welcome to AI & Me. This is the dashboard, where you'll spend most of your time.", ToastMessageType.INFORMATION);
        if (postSignupCallback != null) {
            postSignupCallback.run();
        } else {
            Router.navigateLayout(View.HOME);
        }
    }

    /**
     * Navigates back to the home page.
     * Displays a confirmation dialogue if signup completed.
     */
    @FXML
    private void navigateHome() {
        if (postSignupCallback != null) {
            Dialogue.show(new DialogueMessage(cancelCallbackMessage, DialogueType.YES_NO_CANCEL, result -> {
                if (result != null && result) {
                    Router.navigateLayout(View.HOME);
                }
            }));
        } else {
            Router.navigateLayout(View.HOME);
        }
    }

    /**
     * Opens the login page.
     */
    @FXML
    private void navigateLogin() {
        LoginController loginController = (LoginController)Router.navigateApp(View.LOGIN);
        assert loginController != null;
        loginController.initialiseCallback(postSignupCallback, cancelCallbackMessage);
    }
}