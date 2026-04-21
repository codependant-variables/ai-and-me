package com.codependentvariables.aiandme.validation;

import com.codependentvariables.aiandme.navigation.Toast;
import com.codependentvariables.aiandme.navigation.ToastMessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Validates an array of ValidationEntry, where if any are invalid, invokes a provided onError method.
 */
public class FormValidator {
    private final ValidationEntry<?>[] validationEntries;
    private final Consumer<List<String>> onError;

    public FormValidator(Consumer<List<String>> onError, ValidationEntry<?>... validationEntries) {
        this.onError = onError;
        this.validationEntries = validationEntries;
    }

    public FormValidator(ValidationEntry<?>... validationEntries) {
        this.onError = null;
        this.validationEntries = validationEntries;
    }

    public boolean validate() {
        List<String> errors = new ArrayList<>();

        for (ValidationEntry<?> entry : validationEntries) {
            entry.validate(errors::add);
        }

        if (!errors.isEmpty()) {
            if (onError != null) {
                onError.accept(errors);
            } else {
                for (String error : errors) {
                    Toast.addMessage("Invalid", error, ToastMessageType.WARNING);
                }
            }
            return false;
        }
        return true;
    }
}