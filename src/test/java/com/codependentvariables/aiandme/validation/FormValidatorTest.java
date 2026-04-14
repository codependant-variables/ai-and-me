package com.codependentvariables.aiandme.validation;

import com.codependentvariables.aiandme.validation.validators.MinStringLengthValidator;
import com.codependentvariables.aiandme.validation.validators.NotNullValidator;
import com.codependentvariables.aiandme.validation.validators.StringNotEmptyValidator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FormValidatorTest {

    @Test
    public void exactly_one_entry() {
        // Fails
        assertValidationResult(null, false, "Field is required.");

        // Passes
        assertValidationResult("not empty", true, "");
    }

    private void assertValidationResult(String stringValue, boolean expectedValid, String expectedError) {
        ArrayList<String> errors = new ArrayList<>();
        FormValidator validator = new FormValidator(
                errors::addAll,
                new ValidationEntry<>(() -> stringValue, new StringNotEmptyValidator())
        );

        assertEquals(expectedValid, validator.validate());
        assertEquals(expectedError, String.join(" ", errors));
    }

    @Test
    public void more_than_one_entry() {
        // Both fail
        assertValidation(null, null, false, "Field is required. Field is less than 2 characters.");

        // One fails
        assertValidation(723, "a", false, "a is less than 2 characters.");

        // Both pass
        assertValidation(723, "ab", true, null);
    }

    private void assertValidation(Integer intValue, String stringValue, boolean expectedValid, String expectedError) {
        ArrayList<String> errors = new ArrayList<>();

        FormValidator validator = new FormValidator(
                errors::addAll,
                new ValidationEntry<>(() -> intValue, new NotNullValidator<>()),
                new ValidationEntry<>(() -> stringValue, new MinStringLengthValidator(2))
        );

        assertEquals(expectedValid, validator.validate());

        if (expectedError == null) {
            assertTrue(errors.isEmpty());
        } else {
            assertEquals(expectedError, String.join(" ", errors));
        }
    }
}
