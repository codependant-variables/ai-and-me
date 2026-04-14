package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.model.IUserDAO;
import com.codependentvariables.aiandme.model.SqliteUserDAO;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class LoginController {

    @FXML
    private VBox loginContainer;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label loginMessage;
    private final IUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private void onLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty()) {
            setLoginMessage("Please enter an email", true);
            return;
        }

        if (password.isEmpty()) {
            setLoginMessage("Please enter a password", true);
            return;
        }

        User user = userDAO.getByEmail(email);
        if (user == null) {
            setLoginMessage("User not found", true);
            return;
        }

        if (!UserService.comparePassword(user, password)) {
            setLoginMessage("Incorrect password", true);
            return;
        }

        setLoginMessage("Success", false);
    }

    private void setLoginMessage(String message, boolean isRed) {
        loginMessage.setText(message);
        loginMessage.setTextFill(isRed ? Color.RED : Color.BLACK);
        loginMessage.setVisible(true);
    }

    @FXML
    private void onLogout() {
        emailField.setText("");
        passwordField.setText("");
        setLoginMessage("Logged out", false);
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
    private void handleGoToGuest() {
        try {
            AiAndMe.showUserView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToSignUp() {
        try {
            AiAndMe.showSignUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
