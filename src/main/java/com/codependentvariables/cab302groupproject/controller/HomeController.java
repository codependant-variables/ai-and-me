package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.service.IUserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class HomeController {
    @FXML
    private ListView<User> usersListView;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    private final IUserService userService;

    public HomeController(IUserService userService) {
        this.userService = userService;
    }

    @FXML
    public void initialize() {
        usersListView.setCellFactory(this::renderCell);

        if (userService.getAll().isEmpty()) {
            userService.create(new User("Amy Adams", "amy.adams@mydomain.gov"));
            userService.create(new User("Bob Builder", "bobthebulider23@swagmail.net"));
        }

        refreshData();
    }

    private void refreshData() {
        var items = usersListView.getItems();
        items.clear();
        items.setAll(userService.getAll());
    }

    private ListCell<User> renderCell(ListView<User> userListView) {
        return new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(String.format("%s (%s)", user.getName(), user.getEmail()));
                }
            }
        };
    }

    @FXML
    protected void onCreateClick() {
        var errors = new ArrayList<String>();

        if (!User.validateName(nameTextField.getText()))
            errors.add("Name is required.");

        if (!User.validateEmail(emailTextField.getText()))
            errors.add("A valid email is required.");

        if (!errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please correct the following issues:");
            alert.setContentText(String.join("\n", errors));
            alert.showAndWait();
            return;
        }

        userService.create(new User(nameTextField.getText(), emailTextField.getText()));

        refreshData();
    }

    @FXML
    protected void onDeleteClick() {
        var selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser == null)
            return;

        userService.delete(selectedUser);

        refreshData();
    }
}
