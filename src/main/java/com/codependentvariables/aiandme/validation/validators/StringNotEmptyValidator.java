package com.codependentvariables.aiandme.validation.validators;

public class StringNotEmptyValidator implements IValidator<String> {
    @Override
    public String validate(String value, String display) {
        return value == null || value.isEmpty() ? String.format("%s is required.", display) : null;
    }
}