package com.codependentvariables.aiandme.modules;

import java.util.function.Consumer;

public record DialogueMessage(String message, DialogueType type, Consumer<Boolean> callback, String yesText, String noText, String cancelText) {
    public DialogueMessage {
        callback = callback == null ? x -> { return; } : callback;
        yesText = yesText == null ? (type == DialogueType.MESSAGE ? "Ok" : "Yes") : yesText;
        noText = noText == null ? "No" : noText;
        cancelText = cancelText == null ? "Cancel" : cancelText;
    }

    public DialogueMessage(String message, DialogueType type) {
        this(message, type, null, null, null, null);
    }

    public DialogueMessage(String message, DialogueType type, Consumer<Boolean> callback) {
        this(message, type, callback, null, null, null);
    }

    public DialogueMessage withCallback(Consumer<Boolean> newCallback) {
        return new DialogueMessage(message, type, newCallback, yesText, noText, cancelText);
    }
}
