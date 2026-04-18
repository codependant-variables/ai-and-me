package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class AppController {
    @FXML
    public BorderPane layoutRef;

    @FXML
    public void initialize() {
        Router.setApp(layoutRef);
        Router.navigateApp(View.LOGIN);
    }
}
