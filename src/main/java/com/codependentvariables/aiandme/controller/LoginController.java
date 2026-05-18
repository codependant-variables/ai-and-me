package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.Icon;
import com.codependentvariables.aiandme.navigation.*;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.services.AuthService;
import com.codependentvariables.aiandme.services.LoginResult;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import com.codependentvariables.aiandme.validation.FormValidator;
import com.codependentvariables.aiandme.validation.ValidationEntry;
import com.codependentvariables.aiandme.validation.validators.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.converter.DefaultStringConverter;

/**
 * Controller for the login view.
 * Handles user authentication and OTP verification.
 */
public class LoginController {
    @FXML
    public ImageView logoRef;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button togglePasswordVisibleButton;
    @FXML
    private Label otpLabel;
    @FXML
    private TextField otpField;
    @FXML
    private Button loginButton;
    @FXML
    public SVGPath viewPasswordIcon;

    private final UserService userService = UserService.getInstance();
    private final AppState appState = AppState.getInstance();

    private Runnable postLoginCallback;
    private String cancelCallbackMessage;

    private final BooleanProperty loggingIn = new SimpleBooleanProperty(false);
    private final BooleanProperty requireOtp = new SimpleBooleanProperty(false);

    /**
     * Restricts OTP input to numeric values
     * with the correct maximum length.
     */
    private final TextFormatter<String> totpTextFormatter = new TextFormatter<>(
            new DefaultStringConverter(),
            "",
            change -> {
                String newText = change.getControlNewText(); // Proposed new text
                if (newText.length() > AuthService.TOTP_SIZE) {
                    return null;
                }

                for (char c : newText.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        return null; // Reject change
                    }
                }
                return change;
            }
    );

    /**
     * Initialises bindings, theme settings,
     * and login form behaviour.
     */
    @FXML
    private void initialize() {
        var darkModeObservable = appState.getObservableIsDarkMode();

        // Update logo depending on theme
        logoRef.imageProperty().bind(darkModeObservable.map(isDark -> new Image(isDark ? AiAndMe.darkLogoUrlString : AiAndMe.lightLogoUrlString)));
        viewPasswordIcon.fillProperty().bind(darkModeObservable.map(isDark -> isDark ? Color.WHITE : Color.BLACK));

        // Toggle between hidden and visible password fields
        passwordTextField.visibleProperty().bind(passwordField.visibleProperty().not());
        viewPasswordIcon.contentProperty().bind(passwordField.visibleProperty().map(visible -> visible ? Icon.OPEN_EYE : Icon.CLOSED_EYE));

        emailField.disableProperty().bind(this.loggingIn.or(this.requireOtp));

        passwordField.textProperty().bindBidirectional(passwordTextField.textProperty());
        passwordField.disableProperty().bind(this.loggingIn.or(this.requireOtp));
        passwordField.managedProperty().bind(passwordField.visibleProperty());
        passwordTextField.disableProperty().bind(this.loggingIn.or(this.requireOtp));
        passwordTextField.managedProperty().bind(passwordTextField.visibleProperty());
        togglePasswordVisibleButton.disableProperty().bind(this.loggingIn.or(this.requireOtp));

        otpLabel.visibleProperty().bind(this.requireOtp);
        otpLabel.managedProperty().bind(otpLabel.visibleProperty());
        otpField.visibleProperty().bind(this.requireOtp);
        otpField.managedProperty().bind(otpField.visibleProperty());
        otpField.setTextFormatter(totpTextFormatter);

        loginButton.disableProperty().bind(this.loggingIn);
    }

    /**
     * Sets callbacks used after login or cancellation.
     *
     * @param postLoginCallback action to run after login
     * @param cancelCallbackMessage message shown when cancelling
     */
    public void initialiseCallback(Runnable postLoginCallback, String cancelCallbackMessage) {
        this.postLoginCallback = postLoginCallback;
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
     * Validates login form input.
     */
    private final FormValidator loginValidator = new FormValidator(
            new ValidationEntry<>(
                    () -> this.emailField.getText(),
                    "Email",
                    new EmailValidator()
            ),
            new ValidationEntry<>(
                    () -> this.passwordField.getText(),
                    "Password",
                    new StringNotEmptyValidator()
            ),
            new ValidationEntry<>(
                    () -> this.otpField.getText(),
                    "One-time password",
                    new DynamicValidator<>((value, display) -> !requireOtp.getValue() || value.length() == AuthService.TOTP_SIZE ? null : String.format("%s must be %d characters.", display, AuthService.TOTP_SIZE))
            )
    );

    /**
     * Attempts to log the user in.
     * Handles OTP requests and login validation.
     */
    @FXML
    private void onLogin() {
        passwordField.setVisible(true);
        this.loggingIn.setValue(true);

        if (!loginValidator.validate()) {
            this.loggingIn.setValue(false);
            return;
        }

        // Only query database if form is valid
        User user = userService.getByEmail(this.emailField.getText());
        if (user == null) {
            Toast.addMessage("Error", "User not found.", ToastMessageType.ERROR);
            this.loggingIn.setValue(false);
            return;
        }

        LoginResult loginResult = userService.attemptLogin(user, this.passwordField.getText(), this.otpField.getText());
        if (loginResult == LoginResult.VALID) {
            Toast.addMessage("Logged In", String.format("Welcome, %s!", user.getName()), ToastMessageType.INFORMATION);
            if (postLoginCallback != null) {
                postLoginCallback.run();
            } else {
                Router.navigateLayout(View.HOME);
            }
        } else if (loginResult == LoginResult.REQUIRES_TOTP) {
            this.requireOtp.setValue(true);
            Toast.addMessage("OTP Required", "Please enter a one-time password.", ToastMessageType.WARNING);
        } else {
            Toast.addMessage("Error", "Incorrect password.", ToastMessageType.ERROR);
            this.requireOtp.setValue(false);
            this.otpField.setText("");
        }

        this.loggingIn.setValue(false);
    }

    /**
     * Navigates back to the home page.
     */
    @FXML
    private void navigateHome() {
        if (postLoginCallback != null) {
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
     * Navigates to the signup page.
     */
    @FXML
    private void navigateSignup() {
        SignupController signupController = (SignupController)Router.navigateApp(View.SIGNUP);
        assert signupController != null;
        signupController.initialiseCallback(postLoginCallback, cancelCallbackMessage);
    }
}
