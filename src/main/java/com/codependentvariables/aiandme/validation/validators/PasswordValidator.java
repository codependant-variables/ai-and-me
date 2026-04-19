package com.codependentvariables.aiandme.validation.validators;

/**
 * Validates a String is a valid password.
 * Requirements:
 * 1+ lowercase character
 * 1+ uppercase character
 * 1+ number
 * 1+ symbol
 */
public class PasswordValidator implements IValidator<String> {
    @Override
    public String validate(String value, String display) {
        String error = String.format("%s must contain at least 1 lowercase, 1 uppercase, 1 number, and 1 symbol.", display);

        if (value == null || value.isBlank())
            return error;

        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for(char c : value.toCharArray()) {
            if(Character.isLowerCase(c)) hasLower = true;
            else if(Character.isUpperCase(c)) hasUpper = true;
            else if(Character.isDigit(c)) hasDigit = true;
            else hasSymbol = true;
        }

        if (!hasLower || !hasUpper || !hasDigit || !hasSymbol)
            return error;

        return null;
    }
}
