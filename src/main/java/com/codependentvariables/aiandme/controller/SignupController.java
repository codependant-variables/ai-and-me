package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.model.IUserDAO;
import com.codependentvariables.aiandme.model.SqliteUserDAO;
import com.codependentvariables.aiandme.model.User;
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
            // TODO: change this flow to automatically log the user in after creating a user
            navigateLogin();
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
    private void navigateHome() {
        Router.navigateLayout(View.HOME);
    }

    @FXML
    private void navigateLogin() {
        Router.navigateApp(View.LOGIN);
    }
}
