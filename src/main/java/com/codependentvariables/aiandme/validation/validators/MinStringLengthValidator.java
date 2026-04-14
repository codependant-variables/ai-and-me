package com.codependentvariables.aiandme.validation.validators;

public class MinStringLengthValidator implements IValidator<String> {
    private final int length;

    public MinStringLengthValidator(int length) {
        this.length = length;
    }

    @Override
    public String validate(String value, String display) {
        return value != null && value.length() > length ? null : String.format("%s is less than %d characters.", display, length);
    }
}