package com.codependentvariables.aiandme.modules.validation.validators;

/**
 * Interface for a class that validates a value of T
 */
public interface IValidator<T> {
    /**
     * Validate T, returning a validation message
     * @param value Object of T
     * @param display Display to use for a validation message
     * @return Validation message, null if valid, not null if invalid
     */
    String validate(T value, String display);
}