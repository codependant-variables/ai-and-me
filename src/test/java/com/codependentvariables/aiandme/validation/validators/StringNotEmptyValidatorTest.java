package com.codependentvariables.aiandme.validation.validators;

import com.codependentvariables.aiandme.modules.validation.validators.StringNotEmptyValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StringNotEmptyValidatorTest {
    private final StringNotEmptyValidator validator = new StringNotEmptyValidator();

    @Test
    public void correct_error_message() {
        String error = validator.validate("", "My string");
        assertNotNull(error);
        assertEquals("My string is required.", error);
    }

    @Test
    public void null_value() {
        assertNotNull(validator.validate(null, null));
    }

    @Test
    public void empty_string() {
        assertNotNull(validator.validate("", null));
    }

    @Test
    public void blank_string() {
        assertNull(validator.validate("    ", null));
    }

    @Test
    public void non_blank_string() {
        assertNull(validator.validate("test", null));
    }
}
