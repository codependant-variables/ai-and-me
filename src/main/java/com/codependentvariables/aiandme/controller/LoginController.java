package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.Toast;
import com.codependentvariables.aiandme.navigation.ToastMessageType;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.validation.FormValidator;
import com.codependentvariables.aiandme.validation.ValidationEntry;
import com.codependentvariables.aiandme.validation.validators.*;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private final UserService userService = UserService.getInstance();

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
            )
    );

    @FXML
    private void onLogin() {
        if (!loginValidator.validate()) {
            return;
        }

        // The 2 checks below are purposefully not in the form validator, as db queries only take place if form is valid
        User user = userService.getByEmail(this.emailField.getText());
        if (user == null) {
            Toast.addMessage("Error", "User not found.", ToastMessageType.ERROR);
            return;
        }

        if (userService.attemptLogin(user, this.passwordField.getText())) {
            Router.navigateLayout(View.HOME);
        } else {
            Toast.addMessage("Error", "Incorrect password.", ToastMessageType.ERROR);
        }
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
