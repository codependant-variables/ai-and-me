package com.codependentvariables.aiandme.navigation;

import com.codependentvariables.aiandme.controller.DialogueController;
import com.codependentvariables.aiandme.controller.LoginController;
import com.codependentvariables.aiandme.controller.SignupController;
import javafx.scene.layout.StackPane;
import java.util.function.Consumer;

public class Dialogue {
    private final static String ARE_YOU_SURE_MESSAGE = "Are you sure?";
    private final static String SIGNUP_TO_SAVE_MESSAGE = "You are not logged in. Would you like to create an account or login to save?";
    private static final String CANCEL_CALLBACK_MESSAGE = "Are you sure you want to use as a guest? Your data will be lost forever.";

    private static boolean testMode = false;

    private static StackPane paneRef;

    public static void setRef(StackPane stackPane) {
        paneRef = stackPane;
    }

    public static void message(String message) {
        if (testMode) return;

        loadDialogueMessage(new DialogueMessage(message, DialogueType.MESSAGE));
    }

    public static void confirmation(Consumer<Boolean> callback) {
        if (testMode) return;
        loadDialogueMessage(new DialogueMessage(ARE_YOU_SURE_MESSAGE, DialogueType.YES_NO, callback));
    }

    public static void confirmationWithCancel(Consumer<Boolean> callback) {
        if (testMode) return;
        loadDialogueMessage(new DialogueMessage(ARE_YOU_SURE_MESSAGE, DialogueType.YES_NO_CANCEL, callback));
    }

    public static void signupToSave(Runnable callback, Runnable cancelCallback) {
        if (testMode) {
            cancelCallback.run();
            return;
        }

        loadDialogueMessage(new DialogueMessage(SIGNUP_TO_SAVE_MESSAGE, DialogueType.YES_NO_CANCEL, result -> {
            if (result == null) {
                cancelCallback.run();
            } else if (result) {
                SignupController signupController = (SignupController) Router.navigateApp(View.SIGNUP);
                assert signupController != null;
                signupController.initialiseCallback(callback, CANCEL_CALLBACK_MESSAGE);
            } else {
                LoginController loginController = (LoginController) Router.navigateApp(View.LOGIN);
                assert loginController != null;
                loginController.initialiseCallback(callback, CANCEL_CALLBACK_MESSAGE);
            }
        }, "Signup", "Login", "Don't Save"));
    }

    public static void show(DialogueMessage dialogueMessage) {
        if (testMode) return;
        loadDialogueMessage(dialogueMessage);
    }

    private static void loadDialogueMessage(DialogueMessage dialogueMessage) {
        if (paneRef == null || testMode) return;

        DialogueController controller = (DialogueController)ViewUtils.loadView(paneRef, View.DIALOGUE);
        controller.initialiseData(dialogueMessage.withCallback(result -> {
            paneRef.getChildren().clear();
            dialogueMessage.callback().accept(result);
        }));
    }

    /**
     * Gets dialogueRef from dialogue container. Intended for unit tests.
     */
    public static StackPane getDialogueRef() {
        if (paneRef == null) return null; // for unit tests

        var children = paneRef.getChildren();
        if (children.isEmpty()) {
            return null;
        }
        return (StackPane)children.getFirst();
    }

    /**
     * Turns off dialogue for unit tests.
     */
    public static void disable() {
        testMode = true;
    }
}
