package com.codependentvariables.aiandme.validation.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinStringLengthValidatorTest {
    private final int maxLength = 10;
    private final MinStringLengthValidator validator = new MinStringLengthValidator(maxLength);

    @Test
    public void correct_error_message() {
        String error = validator.validate("", "My string");
        assertNotNull(error);
        assertEquals("My string is less than 10 characters.", error);
    }

    @Test
    public void null_value() {
        assertNotNull(validator.validate(null, null));
    }

    @Test
    public void zero_length() {
        assertNotNull(validator.validate("", null));
    }

    @Test
    public void less_than_min_length() {
        assertNotNull(validator.validate("12345", null));
    }

    @Test
    public void exactly_min_length() {
        assertNull(validator.validate("1234567890", null));
    }

    @Test
    public void greater_than_min_length() {
        assertNull(validator.validate("123456789012345", null));
    }
}
