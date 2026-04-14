package com.codependentvariables.aiandme.validation.validators;

/**
 * Interface for a validator to validate an object that it can be validated
 */
public interface IValidator<T> {
    /**
     * Validate T returning a validation message
     * @param value Object of T
     * @param display Display to use for a validation message
     * @return Validation message, null if valid, not null if invalid
     */
    String validate(T value, String display);
}