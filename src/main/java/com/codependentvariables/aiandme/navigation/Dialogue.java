package com.codependentvariables.aiandme.navigation;

import com.codependentvariables.aiandme.controller.DialogueController;
import javafx.scene.layout.StackPane;
import java.util.function.Consumer;

public class Dialogue {
    private final static String ARE_YOU_SURE_MESSAGE = "Are you sure?";

    private static StackPane paneRef;

    public static void setRef(StackPane stackPane) {
        paneRef = stackPane;
    }

    public static void message(String message) {
        loadDialogue(message, DialogueType.MESSAGE, x -> { return; });
    }

    public static void confirmation(Consumer<Boolean> callback) {
        loadDialogue(ARE_YOU_SURE_MESSAGE, DialogueType.YES_NO, callback);
    }

    public static void confirmationWithCancel(Consumer<Boolean> callback) {
        loadDialogue(ARE_YOU_SURE_MESSAGE, DialogueType.YES_NO_CANCEL, callback);
    }

    public static void show(String message, DialogueType dialogueType, Consumer<Boolean> callback) {
        loadDialogue(message, dialogueType, callback);
    }

    private static void loadDialogue(String message, DialogueType dialogueType, Consumer<Boolean> callback) {
        DialogueController controller = (DialogueController)ViewUtils.loadView(paneRef, View.DIALOGUE);
        controller.initialiseData(message, dialogueType, result -> {
            paneRef.getChildren().clear();
            callback.accept(result);
        });
    }

    /**
     * Gets dialogueRef from dialogue container. Intended for unit tests.
     */
    public static StackPane getDialogueRef() {
        var children = paneRef.getChildren();
        if (children.isEmpty()) {
            return null;
        }
        return (StackPane)children.getFirst();
    }
}
