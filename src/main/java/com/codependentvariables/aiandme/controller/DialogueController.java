package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.modules.DialogueMessage;
import com.codependentvariables.aiandme.modules.DialogueType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.function.Consumer;

/**
 * Controller for application dialogue popups.
 * Handles displaying dialogue messages and processing user responses.
 */
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

    /**
     * Initialises dialogue button visibility defaults.
     */
    @FXML
    public void initialize() {
        yesRef.managedProperty().bind(yesRef.visibleProperty());
        noRef.visibleProperty().bind(dialogueType.map(dialogueType -> dialogueType != DialogueType.MESSAGE));
        noRef.managedProperty().bind(noRef.visibleProperty());
        cancelRef.visibleProperty().bind(dialogueType.map(dialogueType -> dialogueType == DialogueType.YES_NO_CANCEL));
        cancelRef.managedProperty().bind(cancelRef.visibleProperty());
    }

    /**
     * Loads dialogue content and button labels.
     *
     * @param dialogueMessage dialogue configuration data
     */
    public void initialiseData(DialogueMessage dialogueMessage) {
        messageRef.setText(dialogueMessage.message());
        dialogueType.set(dialogueMessage.type());
        callback = dialogueMessage.callback();
        yesRef.setText(dialogueMessage.yesText());
        noRef.setText(dialogueMessage.noText());
        cancelRef.setText(dialogueMessage.cancelText());
    }

    /**
     * Closes the dialogue when the background is clicked.
     */
    @FXML
    public void onBackgroundClick() {
        callback.accept(null);
    }

    /**
     * Handles dialogue button clicks and returns the result.
     *
     * @param actionEvent button click event
     */
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
