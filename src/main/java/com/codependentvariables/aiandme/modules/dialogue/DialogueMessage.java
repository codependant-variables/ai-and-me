package com.codependentvariables.aiandme.modules.dialogue;

import java.util.function.Consumer;

/**
 * Represents a dialogue message and its configuration.
 */
public record DialogueMessage(String message, DialogueType type, Consumer<Boolean> callback, String yesText, String noText, String cancelText) {
    /**
     * Creates a dialogue message with default values for any null fields.
     */
    public DialogueMessage {
        callback = callback == null ? x -> { return; } : callback;
        yesText = yesText == null ? (type == DialogueType.MESSAGE ? "Ok" : "Yes") : yesText;
        noText = noText == null ? "No" : noText;
        cancelText = cancelText == null ? "Cancel" : cancelText;
    }

    /**
     * Creates a dialogue message without a callback.
     *
     * @param message the dialogue message
     * @param type the dialogue type
     */
    public DialogueMessage(String message, DialogueType type) {
        this(message, type, null, null, null, null);
    }

    /**
     * Creates a dialogue message with a callback.
     *
     * @param message the dialogue message
     * @param type the dialogue type
     * @param callback receives the user's response
     */
    public DialogueMessage(String message, DialogueType type, Consumer<Boolean> callback) {
        this(message, type, callback, null, null, null);
    }

    /**
     * Creates a copy of this dialogue message with a new callback.
     *
     * @param newCallback the callback to use
     * @return a new dialogue message instance
     */
    public DialogueMessage withCallback(Consumer<Boolean> newCallback) {
        return new DialogueMessage(message, type, newCallback, yesText, noText, cancelText);
    }
}
