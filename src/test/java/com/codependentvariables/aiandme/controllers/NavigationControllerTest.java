package com.codependentvariables.aiandme.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@ExtendWith(ApplicationExtension.class)
public class NavigationControllerTest {
    private Parent root;

    @Override
    private void start(Stage stage) throws Exception {
        root = FXMLLoader.load(
                getClass().getResource("/fxml/login.fxml")
        );

        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    @Test
    void should_show_guest_button() {
        verifyThat("#guestButton", isVisible());
    }

    @Test
    public void when_button_is_clicked(FxRobot robot) {
        robot.clickOn("#guestButton");
        verifyThat("#logoRef", isVisible());
    }
}

