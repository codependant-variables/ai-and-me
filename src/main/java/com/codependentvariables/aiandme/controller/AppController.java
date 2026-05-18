package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.modules.Dialogue;
import com.codependentvariables.aiandme.modules.Router;
import com.codependentvariables.aiandme.modules.Toast;
import com.codependentvariables.aiandme.modules.View;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class AppController {
    @FXML
    public StackPane layoutRef;

    @FXML
    public StackPane dialogueRef;

    @FXML
    public StackPane toastRef;

    @FXML
    public void initialize() {
        Router.setApp(layoutRef);
        Router.navigateApp(View.LOGIN);
        Dialogue.setRef(dialogueRef);
        Toast.setRef(toastRef);
    }
}
