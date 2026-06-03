package com.codependentvariables.aiandme.modules.validation.validators;

import com.codependentvariables.aiandme.modules.validation.validators.NotNullValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NotNullValidatorTest {
    private final NotNullValidator<Object> validator = new NotNullValidator<>();

    @Test
    public void correct_error_message() {
        String error = validator.validate(null, "My object");
        assertNotNull(error);
        assertEquals("My object is required.", error);
    }

    @Test
    public void null_value() {
        assertNotNull(validator.validate(null, null));
    }

    @Test
    public void string_value() {
        assertNull(validator.validate("", null));
    }

    @Test
    public void integer_value() {
        assertNull(validator.validate(1, null));
    }

    @Test
    public void array_value() {
        assertNull(validator.validate(new Long[] { 831892385981235L, 981289235L }, null));
    }
}
