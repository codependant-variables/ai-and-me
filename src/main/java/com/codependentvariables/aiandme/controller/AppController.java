package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Dialogue;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.Toast;
import com.codependentvariables.aiandme.navigation.View;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

/**
 * Controller for the main application container.
 * Initialises routing, dialogue overlays, and toast notifications.
 */
public class AppController {
    @FXML
    public StackPane layoutRef;

    @FXML
    public StackPane dialogueRef;

    @FXML
    public StackPane toastRef;

    /**
     * Initialises the application layout and shared UI components.
     */
    @FXML
    public void initialize() {
        Router.setApp(layoutRef); // Set the main application layout container
        Router.navigateApp(View.LOGIN); // Open the login screen on startup

        // Set shared dialogue and toast containers
        Dialogue.setRef(dialogueRef);
        Toast.setRef(toastRef);
    }
}
