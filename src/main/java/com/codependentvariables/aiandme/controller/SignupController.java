package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.Svg;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.Toast;
import com.codependentvariables.aiandme.navigation.ToastMessageType;
import com.codependentvariables.aiandme.navigation.View;
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

    @FXML
    private void initialize() {
        boolean isDarkMode = AppState.getInstance().getIsDarkMode();

        Image newImage = new Image(AiAndMe.getLogoUrlString());
        logoRef.setImage(newImage);
        viewPasswordIcon.setFill(isDarkMode ? Color.WHITE : Color.BLACK);

        passwordField.textProperty().bindBidirectional(passwordTextField.textProperty());
        passwordField.managedProperty().bind(passwordField.visibleProperty());
        passwordTextField.setOnAction(onMousePressed -> toggleViewPassword());
        passwordTextField.managedProperty().bind(passwordTextField.visibleProperty());

        toggleViewPassword();
    }
    @FXML
    private void toggleViewPassword() {
        boolean isVisible = passwordField.isVisible();
        viewPasswordIcon.setContent(isVisible ? Svg.CLOSED_EYE : Svg.OPEN_EYE);
        passwordField.setVisible(!isVisible);
        passwordTextField.setVisible(isVisible);
    }

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
        Toast.addMessage("Woohoo!", "Welcome to AI & Me. This is the dashboard, where you'll spend most of your time.", ToastMessageType.INFORMATION);
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