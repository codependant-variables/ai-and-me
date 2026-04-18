package com.codependentvariables.aiandme.validation;

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

    public boolean validate() {
        List<String> errors = new ArrayList<>();

        for (ValidationEntry<?> entry : validationEntries) {
            entry.validate(errors::add);
        }

        if (!errors.isEmpty()) {
            onError.accept(errors);
            return false;
        }
        return true;
    }
}