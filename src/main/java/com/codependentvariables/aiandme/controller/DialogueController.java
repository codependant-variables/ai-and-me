package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.navigation.DialogueType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.function.Consumer;

public class DialogueController {
    @FXML
    public Label messageRef;
    @FXML
    public Button yesRef;
    @FXML
    public Button noRef;
    @FXML
    public Button cancelRef;

    private final ObjectProperty<DialogueType> dialogueType = new SimpleObjectProperty<>(null);
    private Consumer<Boolean> callback;

    @FXML
    public void initialize() {
        yesRef.textProperty().bind(dialogueType.map(dialogueType -> dialogueType == DialogueType.MESSAGE ? "Ok" : "Yes"));
        yesRef.managedProperty().bind(yesRef.visibleProperty());
        noRef.visibleProperty().bind(dialogueType.map(dialogueType -> dialogueType != DialogueType.MESSAGE));
        noRef.managedProperty().bind(noRef.visibleProperty());
        cancelRef.visibleProperty().bind(dialogueType.map(dialogueType -> dialogueType == DialogueType.YES_NO_CANCEL));
        cancelRef.managedProperty().bind(cancelRef.visibleProperty());
    }

    public void initialiseData(String message, DialogueType dialogueType, Consumer<Boolean> callback) {
        messageRef.setText(message);
        this.dialogueType.set(dialogueType);
        this.callback = callback;
    }

    @FXML
    public void onBackgroundClick() {
        callback.accept(null);
    }

    @FXML
    public void onClick(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        Boolean result;

        if (source == yesRef) {
            result = true;
        } else if (source == noRef) {
            result = false;
        } else {
            result = null;
        }

        callback.accept(result);
    }
}
