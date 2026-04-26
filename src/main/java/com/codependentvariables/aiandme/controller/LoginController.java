package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.Toast;
import com.codependentvariables.aiandme.navigation.ToastMessageType;
import com.codependentvariables.aiandme.navigation.View;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.converter.DefaultStringConverter;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordFieldVisible;
    @FXML
    private Label otpLabel;
    @FXML
    private TextField otpField;
    @FXML
    private Button loginButton;
    @FXML
    public SVGPath viewPasswordIcon;
    @FXML
    public String closedEyeSVG = "M14.7649 6.07595C14.9991 6.22231 15.0703 6.53078 14.9239 6.76495C14.4849 7.46742 13.9632 8.10644 13.3702 8.66304L14.5712 9.86405C14.7664 10.0593 14.7664 10.3759 14.5712 10.5712C14.3759 10.7664 14.0593 10.7664 13.8641 10.5712L12.6011 9.30816C11.8049 9.90282 10.9089 10.3621 9.93374 10.651L10.383 12.3276C10.4544 12.5944 10.2961 12.8685 10.0294 12.94C9.76266 13.0115 9.4885 12.8532 9.41703 12.5864L8.95916 10.8775C8.48742 10.958 8.00035 10.9999 7.5 10.9999C6.99964 10.9999 6.51257 10.958 6.04082 10.8775L5.58299 12.5864C5.51153 12.8532 5.23737 13.0115 4.97063 12.94C4.7039 12.8685 4.5456 12.5944 4.61706 12.3277L5.06624 10.651C4.09111 10.3621 3.19503 9.90281 2.3989 9.30814L1.1359 10.5711C0.940638 10.7664 0.624058 10.7664 0.428797 10.5711C0.233537 10.3759 0.233537 10.0593 0.428797 9.86404L1.62982 8.66302C1.03682 8.10643 0.515113 7.46742 0.0760677 6.76495C-0.0702867 6.53078 0.000898544 6.22231 0.235064 6.07595C0.46923 5.9296 0.777703 6.00078 0.924057 6.23495C1.40354 7.00212 1.989 7.68056 2.66233 8.2427C2.67315 8.25096 2.6837 8.25971 2.69397 8.26897C4.00897 9.35527 5.65536 9.9999 7.5 9.9999C10.3078 9.9999 12.6563 8.50629 14.0759 6.23495C14.2223 6.00078 14.5308 5.9296 14.7649 6.07595Z";
    @FXML
    public String openEyeSVG = "M21.821 12.43c-.083-.119-2.062-2.944-4.793-4.875-1.416-1.003-3.202-1.555-5.028-1.555-1.825 0-3.611.552-5.03 1.555-2.731 1.931-4.708 4.756-4.791 4.875-.238.343-.238.798 0 1.141.083.119 2.06 2.944 4.791 4.875 1.419 1.002 3.205 1.554 5.03 1.554 1.826 0 3.612-.552 5.028-1.555 2.731-1.931 4.71-4.756 4.793-4.875.239-.342.239-.798 0-1.14zm-9.821 4.07c-1.934 0-3.5-1.57-3.5-3.5 0-1.934 1.566-3.5 3.5-3.5 1.93 0 3.5 1.566 3.5 3.5 0 1.93-1.57 3.5-3.5 3.5zM14 13c0 1.102-.898 2-2 2-1.105 0-2-.898-2-2 0-1.105.895-2 2-2 1.102 0 2 .895 2 2z";

    @FXML
    private final UserService userService = UserService.getInstance();

    private final BooleanProperty loggingIn = new SimpleBooleanProperty(false);
    private final BooleanProperty requireOtp = new SimpleBooleanProperty(false);
    private final TextFormatter<String> totpTextFormatter = new TextFormatter<>(
            new DefaultStringConverter(),
            "",
            change -> {
                String newText = change.getControlNewText(); // Proposed new text
                for (char c : newText.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        return null; // Reject change
                    }
                }
                return change;
            }
    );

    @FXML
    private void initialize() {
        boolean isDarkMode = AppState.getInstance().getIsDarkMode();
        viewPasswordIcon.setFill(isDarkMode ? Color.WHITE : Color.BLACK);
        passwordField.textProperty().bindBidirectional(passwordFieldVisible.textProperty());
        passwordFieldVisible.setOnAction(onMousePressed -> showPassword());

        emailField.disableProperty().bind(this.loggingIn.or(this.requireOtp));
        passwordField.disableProperty().bind(this.loggingIn.or(this.requireOtp));
        passwordFieldVisible.disableProperty().bind(this.loggingIn.or(this.requireOtp));
        loginButton.disableProperty().bind(this.loggingIn);

        otpLabel.visibleProperty().bind(this.requireOtp);
        otpLabel.managedProperty().bind(otpLabel.visibleProperty());
        otpField.visibleProperty().bind(this.requireOtp);
        otpField.managedProperty().bind(otpField.visibleProperty());
        otpField.setTextFormatter(totpTextFormatter);
    }
    @FXML
    private void showPassword() {
        boolean PasswordVisible = passwordField.isVisible();
        viewPasswordIcon.setContent(PasswordVisible ? closedEyeSVG : openEyeSVG);
        passwordField.setVisible(!PasswordVisible);
        passwordField.setManaged(!PasswordVisible);
        passwordFieldVisible.setVisible(PasswordVisible);
        passwordFieldVisible.setManaged(PasswordVisible);
    }

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

    @FXML
    private void onLogin() {
        this.loggingIn.setValue(true);

        if (!loginValidator.validate()) {
            this.loggingIn.setValue(false);
            return;
        }

        // This check is purposefully not in the form validator, as db queries only take place if form is valid
        User user = userService.getByEmail(this.emailField.getText());
        if (user == null) {
            Toast.addMessage("Error", "User not found.", ToastMessageType.ERROR);
            this.loggingIn.setValue(false);
            return;
        }

        LoginResult loginResult = userService.attemptLogin(user, this.passwordField.getText(), this.otpField.getText());
        if (loginResult == LoginResult.VALID) {
            Router.navigateLayout(View.HOME);
        } else if (loginResult == LoginResult.REQUIRES_TOTP) {
            this.requireOtp.setValue(true);
            Toast.addMessage("OTP Required", "Please enter a one-time password.", ToastMessageType.WARNING);
        } else {
            Toast.addMessage("Error", "Incorrect password.", ToastMessageType.ERROR);
            this.requireOtp.setValue(false);
        }

        this.loggingIn.setValue(false);
    }

    @FXML
    private void navigateHome() {
        Router.navigateLayout(View.HOME);
    }

    @FXML
    private void navigateSignUp() {
        Router.navigateApp(View.SIGNUP);
    }
}
