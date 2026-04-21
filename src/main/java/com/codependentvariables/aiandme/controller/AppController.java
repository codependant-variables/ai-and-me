package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.Toast;
import com.codependentvariables.aiandme.navigation.View;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class AppController {
    @FXML
    public StackPane layoutRef;

    @FXML
    public StackPane toastRef;

    @FXML
    public void initialize() {
        Router.setApp(layoutRef);
        Router.navigateApp(View.LOGIN);
        Toast.setRef(toastRef);
    }
}
