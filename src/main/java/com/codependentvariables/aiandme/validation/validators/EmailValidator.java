package com.codependentvariables.aiandme.validation.validators;

public class EmailValidator implements IValidator<String> {
    @Override
    public String validate(String value, String display) {
        String error = String.format("%s is not a valid email.", display);

        if (value == null || value.isBlank())
            return error;

        int atIndex = value.indexOf('@');
        int dotIndex = value.lastIndexOf('.');

        if (atIndex < 1 || dotIndex == -1 || dotIndex >= value.length() - 2 || atIndex + 2 >= dotIndex)
            return error;

        return null;
    }
}