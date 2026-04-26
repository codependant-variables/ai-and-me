package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;

public class ProfileController {
    private final AppState appState = AppState.getInstance();
    private final UserService userService = UserService.getInstance();

    private User currentUser;

    @FXML
    private void initialize() {
        this.currentUser = appState.getCurrentUser();
        if (currentUser == null) {
            Router.navigateLayout(View.HOME);
        }
    }
}
