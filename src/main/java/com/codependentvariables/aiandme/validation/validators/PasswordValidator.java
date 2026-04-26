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
    private final static int MIN_LENGTH = 10;
    private final static int MIN_LOWERCASE = 1;
    private final static int MIN_UPPERCASE = 1;
    private final static int MIN_DIGITS = 1;
    private final static int MIN_SYMBOLS = 1;

    @Override
    public String validate(String value, String display) {
        if (value == null || value.isEmpty()) {
            return String.format("%s must be at least %d characters and contain at least %d lowercase, %d uppercase, %d number, and %d symbol.", display, MIN_LENGTH, MIN_LOWERCASE, MIN_UPPERCASE, MIN_DIGITS, MIN_SYMBOLS);
        }

        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for (char c : value.toCharArray()) {
            if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSymbol = true;
        }

        java.util.StringJoiner missing = new java.util.StringJoiner(", ");
        if (value.length() < MIN_LENGTH) missing.add(String.format("%d characters", MIN_LENGTH));
        if (!hasLower) missing.add(String.format("%d lowercase", MIN_LOWERCASE));
        if (!hasUpper) missing.add(String.format("%d uppercase", MIN_UPPERCASE));
        if (!hasDigit) missing.add(String.format("%d number", MIN_DIGITS));
        if (!hasSymbol) missing.add(String.format("%d symbol", MIN_SYMBOLS));

        if (missing.length() > 0) {
            return String.format("%s is missing: %s.", display, missing.toString());
        }

        return null;
    }
}
