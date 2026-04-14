package com.codependentvariables.aiandme.validation;

import com.codependentvariables.aiandme.validation.validators.IValidator;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ValidationEntry<T> {
    private static final String defaultDisplay = "Field";

    private final Supplier<T> valueSupplier;
    private final Supplier<String> displaySupplier;
    private final IValidator<T>[] validators;

    /**
     * Stores validators that will be validated against the provided value at a later time. Display is value toString().
     * @param valueSupplier Supplier of a value to validate
     * @param validators Validators to use for the value
     */
    @SafeVarargs
    public ValidationEntry(Supplier<T> valueSupplier, IValidator<T>... validators) {
        this.valueSupplier = valueSupplier;
        this.displaySupplier = () -> {
            T value = this.valueSupplier.get();
            if (value == null)
                return defaultDisplay;

            String display = value.toString();
            return display == null ? defaultDisplay : display;
        };
        this.validators = validators;
    }

    /**
     * Stores validators that will be validated against the provided value at a later time.
     * @param valueSupplier Supplier of a value to validate
     * @param display String to provide for validation messages
     * @param validators Validators to use for the value
     */
    @SafeVarargs
    public ValidationEntry(Supplier<T> valueSupplier, String display, IValidator<T>... validators) {
        this.valueSupplier = valueSupplier;
        this.displaySupplier = () -> display;
        this.validators = validators;
    }

    /**
     * Stores validators that will be validated against the provided value at a later time.
     * @param valueSupplier Supplier of a value to validate
     * @param displaySupplier Supplier of a display for validation messages
     * @param validators Validators to use for the value
     */
    @SafeVarargs
    public ValidationEntry(Supplier<T> valueSupplier, Supplier<String> displaySupplier, IValidator<T>... validators) {
        this.valueSupplier = valueSupplier;
        this.displaySupplier = displaySupplier;
        this.validators = validators;
    }

    /**
     * Get a value and validate against all validators.
     * @param errorConsumer Consumer method that accepts a validation message. In practice, adds the message to an error list.
     */
    public void validate(Consumer<String> errorConsumer) {
        T value = valueSupplier.get();
        String display = displaySupplier.get();
        for (IValidator<T> validator : validators) {
            String message = validator.validate(value, display);
            if (message != null) {
                errorConsumer.accept(message);
            }
        }
    }
}
