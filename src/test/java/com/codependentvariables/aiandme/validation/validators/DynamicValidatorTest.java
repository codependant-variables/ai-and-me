package com.codependentvariables.aiandme.validation.validators;

import com.codependentvariables.aiandme.modules.validation.validators.DynamicValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DynamicValidatorTest {
    @Test
    public void correct_error_message() {
        DynamicValidator<String> validator = new DynamicValidator<>((value, display) -> String.format("My value %s and display %s is being used.", value, display));
        String error = validator.validate("123", "456");
        assertNotNull(error);
        assertEquals("My value 123 and display 456 is being used.", error);
    }

    @Test
    public void value_is_used_to_determine_message() {
        int integer = 0;
        DynamicValidator<Integer> validator = new DynamicValidator<>((value, display) -> value == 0 ? "Equals 0": null);

        String error = validator.validate(integer, null);
        assertNotNull(error);
        assertEquals("Equals 0", error);

        integer = 1;

        error = validator.validate(integer, null);
        assertNull(error);
    }
}
