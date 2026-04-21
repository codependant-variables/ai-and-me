package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.validation.FormValidator;
import com.codependentvariables.aiandme.validation.ValidationEntry;
import com.codependentvariables.aiandme.validation.validators.*;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    private final UserService userService = UserService.getInstance();

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

    @FXML
    private void onSignup() {
        if (!signupValidator.validate()) {
            return;
        }

        userService.signup(this.nameField.getText(), this.emailField.getText(), this.passwordField.getText());
        navigateHome();
    }

    @FXML
    private void navigateHome() {
        Router.navigateLayout(View.HOME);
    }

    @FXML
    private void navigateLogin() {
        Router.navigateApp(View.LOGIN);
    }
}
