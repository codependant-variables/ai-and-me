package com.codependentvariables.aiandme.modules.validation.validators;

/**
 * Requires a string to not be empty, i.e. not null nor length of 0, though whitespace is valid
 */
public class StringNotEmptyValidator implements IValidator<String> {
    @Override
    public String validate(String value, String display) {
        return value == null || value.isEmpty() ? String.format("%s is required.", display) : null;
    }
}