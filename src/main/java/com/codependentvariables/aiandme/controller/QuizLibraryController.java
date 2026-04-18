package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.IUserDAO;
import com.codependentvariables.aiandme.model.SqliteUserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.util.List;

public class QuizLibraryController {
    private final IUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private ListView<User> usersListView;

    @FXML
    private VBox userContainer;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;

    /**
     * Programmatically selects a user in the list view and
     * updates the text fields with the user's information.
     * @param user The user to select.
     */
    private void selectUser(User user) {
        usersListView.getSelectionModel().select(user);
        nameTextField.setText(user.getName());
        emailTextField.setText(user.getEmail());
        passwordTextField.setText(user.getPassword());
    }

    /**
     * Renders a cell in the users list view by setting the text to the user's full name.
     * @param userListView The list view to render the cell for.
     * @return The rendered cell.
     */
    private ListCell<User> renderCell(ListView<User> userListView) {
        return new ListCell<>() {
            /**
             * Handles the event when a user is selected in the list view.
             * @param mouseEvent The event to handle.
             */
            private void onUserSelected(MouseEvent mouseEvent) {
                ListCell<User> clickedCell = (ListCell<User>) mouseEvent.getSource();
                // Get the selected user from the list view
                User selectedUser = clickedCell.getItem();
                if (selectedUser != null) selectUser(selectedUser);
            }

            /**
             * Updates the item in the cell by setting the text to the user's full name.
             * @param user The user to update the cell with.
             * @param empty Whether the cell is empty.
             */
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                // If the cell is empty, set the text to null, otherwise set it to the user's full name
                if (empty || user == null || user.getName() == null) {
                    setText(null);
                    super.setOnMouseClicked(this::onUserSelected);
                } else {
                    setText(user.getName());
                }
            }
        };
    }

    /**
     * Synchronizes the users list view with the users in the database.
     */
    private void syncUsers() {
        usersListView.getItems().clear();
        List<User> users = userDAO.getAllUsers();
        boolean hasUser = !users.isEmpty();
        if (hasUser) {
            usersListView.getItems().addAll(users);
        }
        // Show / hide based on whether there are users
        userContainer.setVisible(hasUser);
    }

    @FXML
    public void initialize() {
        usersListView.setCellFactory(this::renderCell);
        syncUsers();
        // Select the first user and display its information
        usersListView.getSelectionModel().selectFirst();
        User firstUser = usersListView.getSelectionModel().getSelectedItem();
        if (firstUser != null) {
            selectUser(firstUser);
        }
    }

    @FXML
    private void onEditConfirm() {
        // Get the selected user from the list view
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            selectedUser.setName(nameTextField.getText());
            selectedUser.setEmail(emailTextField.getText());
            selectedUser.setPassword(passwordTextField.getText());
            passwordTextField.setText(selectedUser.getPassword());
            userDAO.updateUser(selectedUser);
            syncUsers();
        }
    }

    @FXML
    private void onDelete() {
        // Get the selected user from the list view
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userDAO.deleteUser(selectedUser);
            syncUsers();
        }
    }

    @FXML
    private void onAdd() {
        // Default values for a new user
        final String DEFAULT_NAME = "New User";
        final String DEFAULT_EMAIL = "";
        final String DEFAULT_PASSWORD = "";
        final String DEFAULT_SALT = "";
        User newUser = new User(DEFAULT_NAME, DEFAULT_EMAIL, DEFAULT_PASSWORD, DEFAULT_SALT);
        // Add the new user to the database
        userDAO.addUser(newUser);
        syncUsers();
        // Select the new user in the list view
        // and focus the first name text field
        selectUser(newUser);
        nameTextField.requestFocus();
    }

    @FXML
    private void onCancel() {
        // Find the selected user
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Since the user hasn't been modified,
            // we can just re-select it to refresh the text fields
            selectUser(selectedUser);
        }
    }
}