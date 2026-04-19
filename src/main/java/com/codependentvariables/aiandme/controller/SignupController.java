package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.validation.FormValidator;
import com.codependentvariables.aiandme.validation.ValidationEntry;
import com.codependentvariables.aiandme.validation.validators.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SignupController {

    @FXML
    private VBox signUpContainer;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label signUpMessage;
    private final UserService userService = UserService.getInstance();

    private final FormValidator signupValidator = new FormValidator(
            errors -> setSignUpMessage(String.join(" ", errors), true),
            new ValidationEntry<>(
                    () -> this.nameField.getText(),
                    "Name",
                    new StringNotEmptyValidator()
            ),
            new ValidationEntry<>(
                    () -> this.emailField.getText(),
                    "Email",
                    new EmailValidator(),
                    new DynamicValidator<String>((value, display) -> userService.isUniqueEmail(value) ? null : String.format("%s is already in use.", display))
            ),
            new ValidationEntry<>(
                    () -> this.passwordField.getText(),
                    "Password",
                    new PasswordValidator()
            )
    );

    @FXML
    private void onSignUp() {
        if (!signupValidator.validate()) {
            return;
        }

        User user = userService.createUser(this.nameField.getText(), this.emailField.getText(), this.passwordField.getText());
        // TODO: update app state with new logged in user
        navigateHome();
    }

    private void setSignUpMessage(String message, boolean isError) {
        signUpMessage.setText(message);
        signUpMessage.setTextFill(isError ? Color.RED : Color.BLACK);
        signUpMessage.setVisible(true);
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
