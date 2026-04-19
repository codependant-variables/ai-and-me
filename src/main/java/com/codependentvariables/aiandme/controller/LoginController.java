package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.validation.FormValidator;
import com.codependentvariables.aiandme.validation.ValidationEntry;
import com.codependentvariables.aiandme.validation.validators.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label loginMessage;
    private final UserService userService = UserService.getInstance();

    private final FormValidator loginValidator = new FormValidator(
            errors -> setLoginMessage(String.join(" ", errors), true),
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

        User user = userService.getByEmail(this.emailField.getText());
        if (user == null) {
            setLoginMessage("User not found", true);
            return;
        }

        if (!userService.comparePassword(user, this.passwordField.getText())) {
            setLoginMessage("Incorrect password", true);
            return;
        }

        setLoginMessage("Success", false);
    }

    private void setLoginMessage(String message, boolean isError) {
        loginMessage.setText(message);
        loginMessage.setTextFill(isError ? Color.RED : Color.BLACK);
        loginMessage.setVisible(true);
    }

    @FXML
    private void onLogout() {
        emailField.setText("");
        passwordField.setText("");
        setLoginMessage("Logged out", false);
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
