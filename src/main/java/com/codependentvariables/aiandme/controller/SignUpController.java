package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.model.IUserDAO;
import com.codependentvariables.aiandme.model.SqliteUserDAO;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SignUpController {

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
    private final IUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private void onSignUp() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (name.isEmpty()) {
            setSignUpMessage("Please enter a name", true);
            return;
        }

        if (email.isEmpty()) {
            setSignUpMessage("Please enter an email", true);
            return;
        }

        if (password.isEmpty()) {
            setSignUpMessage("Please enter a password", true);
            return;
        }

        User user = userDAO.getByEmail(email);
        if (user != null) {
            setSignUpMessage("User already exists", true);
            return;
        }

        if (password.length() < 8) {
            setSignUpMessage("Password must be 8 characters.", true);
            return;
        }

        try {
            setSignUpMessage("Success", false);
            AiAndMe.showLogin();
        } catch (Exception e) {
            e.printStackTrace();
            setSignUpMessage("Sign up failed", true);
        }
    }

    private void setSignUpMessage(String message, boolean isRed) {
        signUpMessage.setText(message);
        signUpMessage.setTextFill(isRed ? Color.RED : Color.BLACK);
        signUpMessage.setVisible(true);
    }

    @FXML
    private void handleGoToLandingScreen() {
        try {
            AiAndMe.showLandingScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToLogin() {
        try {
            AiAndMe.showLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToGuest() {
        try {
            AiAndMe.showUserView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
