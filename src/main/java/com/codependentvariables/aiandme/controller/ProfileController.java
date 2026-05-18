package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.Icon;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.navigation.*;
import com.codependentvariables.aiandme.services.UserDataDeletionService;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import com.codependentvariables.aiandme.validation.ValidationEntry;
import com.codependentvariables.aiandme.validation.validators.DynamicValidator;
import com.codependentvariables.aiandme.validation.validators.EmailValidator;
import com.codependentvariables.aiandme.validation.validators.StringNotEmptyValidator;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.SVGPath;

import java.util.Objects;

public class ProfileController {
    private final AppState appState = AppState.getInstance();
    private final UserService userService = UserService.getInstance();
    private final UserDataDeletionService dataDeletionService = UserDataDeletionService.getInstance();

    private User currentUser;

    @FXML
    public TextField nameField;
    @FXML
    public Button nameButton;
    @FXML
    public Button cancelNameButton;
    @FXML
    public SVGPath editNameSvg;
    @FXML
    public SVGPath cancelNameSvg;
    @FXML
    public TextField emailField;
    @FXML
    public Button emailButton;
    @FXML
    public SVGPath editEmailSvg;
    @FXML
    public SVGPath cancelEmailSvg;
    @FXML
    public Button cancelEmailButton;
    @FXML
    public CheckBox mfaCheckbox;
    @FXML
    public Button mfaButton;

    private final ValidationEntry<String> nameValidator = new ValidationEntry<>(
            () -> this.nameField.getText(),
            "Name",
            new StringNotEmptyValidator()
    );
    private final ValidationEntry<String> emailValidator = new ValidationEntry<>(
            () -> this.emailField.getText(),
            "Email",
            new EmailValidator(),
            new DynamicValidator<>((value, display) -> {
                User user = userService.getByEmail(value);
                return user == null || user.getId() == currentUser.getId() ? null : String.format("%s is already in use.", display);
            })
    );

    @FXML
    private void initialize() {
        this.currentUser = appState.getCurrentUser();
        if (currentUser == null) {
            Router.navigateLayout(View.HOME);
        }

        nameField.setText(currentUser.getName());
        editNameSvg.contentProperty().bind(
                Bindings.when(nameField.disableProperty())
                        .then(Icon.PENCIL)
                        .otherwise(Icon.PENCIL) // TODO: change to save when icon is added
        );
        cancelNameSvg.setContent(Icon.CANCEL);
        cancelNameButton.visibleProperty().bind(nameField.disableProperty().not());

        emailField.setText(currentUser.getEmail());
        editEmailSvg.contentProperty().bind(
                Bindings.when(emailField.disableProperty())
                        .then(Icon.PENCIL)
                        .otherwise(Icon.PENCIL) // TODO: change to save when icon is added
        );
        cancelEmailSvg .setContent(Icon.CANCEL);
        cancelEmailButton.visibleProperty().bind(emailField.disableProperty().not());

        boolean hasMfa = currentUser.getTotpSecret() != null;
        mfaCheckbox.setSelected(hasMfa);
        mfaButton.textProperty().bind(
                Bindings.when(mfaCheckbox.selectedProperty())
                        .then("Remove MFA")
                        .otherwise("Setup MFA")
        );
    }

    @FXML
    private void editName() {
        boolean isDisable = nameField.isDisable();
        if (isDisable) {
            nameField.setDisable(false);
            return;
        }

        if (!nameValidator.validate(this::displayError)) {
            return;
        }

        nameField.setDisable(true);
        if (Objects.equals(nameField.getText(), currentUser.getName())) {
            return;
        }

        currentUser.setName(nameField.getText());
        userService.updateCurrentUser();
        displaySaved();
    }

    @FXML
    private void cancelEditName() {
        nameField.setDisable(true);
        nameField.setText(currentUser.getName());
    }

    @FXML
    private void editEmail() {
        boolean isDisable = emailField.isDisable();
        if (isDisable) {
            emailField.setDisable(false);
            return;
        }

        if (!emailValidator.validate(this::displayError)) {
            return;
        }

        emailField.setDisable(true);
        if (Objects.equals(emailField.getText(), currentUser.getEmail())) {
            return;
        }

        currentUser.setEmail(emailField.getText());
        userService.updateCurrentUser();
        displaySaved();
    }

    @FXML
    private void cancelEditEmail() {
        emailField.setDisable(true);
        emailField.setText(currentUser.getEmail());
    }

    private void displayError(String error) {
        Toast.addMessage("Invalid", error, ToastMessageType.ERROR);
    }

    private void displaySaved() {
        Toast.addMessage("Success", "Saved data.", ToastMessageType.INFORMATION);
    }

    @FXML
    private void navigateChangePassword() {
        throw new RuntimeException("Change password not implemented.");
    }

    @FXML
    private void clickMfa() {
        if (currentUser.getTotpSecret() == null) {
            Router.navigateApp(View.SETUP_MFA);
            return;
        }

        // TODO: modal dialogue "Are you sure?"
        currentUser.setTotpSecret(null);
        userService.updateCurrentUser();
        mfaCheckbox.setSelected(false);
        Toast.addMessage("Success", "MFA removed.", ToastMessageType.INFORMATION);
    }

    @FXML
    private void navigateCheckInHistory() {
        Router.navigateLayout(View.CHECK_IN_HISTORY);
    }

    @FXML
    private void exportData() {
        throw new RuntimeException("Export user data not implemented.");
    }

    @FXML
    private void clearData() {
        dataDeletionService.deleteCurrentUserData();
        Toast.addMessage("Data Cleared", "All data associated with this account has been deleted.", ToastMessageType.INFORMATION);
    }

    @FXML
    private void clearCheckIns() {
        dataDeletionService.deleteCurrentUserCheckIns();
        Toast.addMessage("Check-ins Deleted", "All check-ins associated with this account have been deleted.", ToastMessageType.INFORMATION);
    }

    @FXML
    private void clearQuizAttempts() {
        dataDeletionService.deleteCurrentUserQuizAttempts();
        Toast.addMessage("Quiz Attempts Deleted", "All quiz attempts associated with this account have been deleted.", ToastMessageType.INFORMATION);
    }

    @FXML
    private void deleteUser() {
        Dialogue.confirmationWithCancel(result -> {
            if (result == null || !result) {
                return;
            }

            userService.deleteCurrentUser();
            Router.navigateLayout(View.HOME);
            Toast.addMessage("Account Deleted", "We'll miss you!", ToastMessageType.INFORMATION);
        });
    }
}
