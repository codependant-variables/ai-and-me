package com.codependentvariables.aiandme.modules.dialogue;

import com.codependentvariables.aiandme.controller.DialogueController;
import com.codependentvariables.aiandme.controller.LoginController;
import com.codependentvariables.aiandme.controller.SignupController;
import com.codependentvariables.aiandme.modules.router.Router;
import com.codependentvariables.aiandme.modules.router.View;
import com.codependentvariables.aiandme.modules.router.ViewUtils;
import javafx.scene.layout.StackPane;
import java.util.function.Consumer;

/**
 * Utility class for displaying dialogue windows and handling user responses.
 */
public class Dialogue {
    private final static String ARE_YOU_SURE_MESSAGE = "Are you sure?";
    private final static String SIGNUP_TO_SAVE_MESSAGE = "You are not logged in. Would you like to create an account or login to save?";
    private static final String CANCEL_CALLBACK_MESSAGE = "Are you sure you want to use as a guest? Your data will be lost forever.";

    private static StackPane paneRef;

    /**
     * Sets the container used to display dialogues.
     *
     * @param stackPane the dialogue container
     */
    public static void setRef(StackPane stackPane) {
        paneRef = stackPane;
    }

    /**
     * Displays a message dialogue.
     *
     * @param message the message to display
     */
    public static void message(String message) {
        loadDialogueMessage(new DialogueMessage(message, DialogueType.MESSAGE));
    }

    /**
     * Displays a confirmation dialogue with Yes and No options.
     *
     * @param callback receives the user's response
     */
    public static void confirmation(Consumer<Boolean> callback) {
        loadDialogueMessage(new DialogueMessage(ARE_YOU_SURE_MESSAGE, DialogueType.YES_NO, callback));
    }

    /**
     * Displays a confirmation dialogue with Yes, No, and Cancel options.
     *
     * @param callback receives the user's response
     */
    public static void confirmationWithCancel(Consumer<Boolean> callback) {
        loadDialogueMessage(new DialogueMessage(ARE_YOU_SURE_MESSAGE, DialogueType.YES_NO_CANCEL, callback));
    }

    /**
     * Displays a dialogue prompting the user to sign up or log in before saving.
     *
     * @param callback executed after successful authentication
     * @param cancelCallback executed when the user chooses not to save
     */
    public static void signupToSave(Runnable callback, Runnable cancelCallback) {
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

    /**
     * Displays the specified dialogue message.
     *
     * @param dialogueMessage the dialogue to display
     */
    public static void show(DialogueMessage dialogueMessage) {
        loadDialogueMessage(dialogueMessage);
    }

    /**
     * Loads and displays a dialogue message.
     *
     * @param dialogueMessage the dialogue to load
     */
    private static void loadDialogueMessage(DialogueMessage dialogueMessage) {
        DialogueController controller = (DialogueController) ViewUtils.loadView(paneRef, View.DIALOGUE);
        controller.initialiseData(dialogueMessage.withCallback(result -> {
            paneRef.getChildren().clear();
            dialogueMessage.callback().accept(result);
        }));
    }

    /**
     * Gets dialogueRef from dialogue container. Intended for unit tests.
     *
     * @return the dialogue container, or null if no dialogue is displayed
     */
    public static StackPane getDialogueRef() {
        var children = paneRef.getChildren();
        if (children.isEmpty()) {
            return null;
        }
        return (StackPane) children.getFirst();
    }
}
