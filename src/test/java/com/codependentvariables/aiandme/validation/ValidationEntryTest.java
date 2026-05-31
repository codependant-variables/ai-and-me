package com.codependentvariables.aiandme.validation;

import com.codependentvariables.aiandme.InvocationCounter;
import com.codependentvariables.aiandme.modules.validation.ValidationEntry;
import com.codependentvariables.aiandme.modules.validation.validators.IValidator;
import com.codependentvariables.aiandme.modules.validation.validators.MinStringLengthValidator;
import com.codependentvariables.aiandme.modules.validation.validators.NotNullValidator;
import com.codependentvariables.aiandme.modules.validation.validators.StringNotEmptyValidator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationEntryTest {
    @Test
    public void zero_validators() {
        assertValidationResult("Any value can go here", true, "", 0);
    }

    @Test
    public void one_validator_valid() {
        assertValidationResult("not empty", true, "", 0, new StringNotEmptyValidator());
    }

    @Test
    public void one_validator_invalid() {
        assertValidationResult("", false, " is required.", 1, new StringNotEmptyValidator());
    }

    @Test
    public void more_than_one_validator_valid() {
        assertValidationResult("not empty", true, "", 0, new NotNullValidator<>(), new StringNotEmptyValidator());
    }

    @Test
    public void more_than_one_validator_exactly_one_invalid() {
        assertValidationResult("test", false, "test is less than 10 characters.", 1, new MinStringLengthValidator(10), new StringNotEmptyValidator());
    }

    @Test
    public void more_than_one_validator_more_than_one_invalid() {
        assertValidationResult(null, false, "Field is required. Field is required.", 2, new NotNullValidator<>(), new StringNotEmptyValidator());
    }

    @SafeVarargs
    private <T> void assertValidationResult(T value, boolean expectedValid, String expectedError, int expectedErrorCount, IValidator<T>... validators) {
        final InvocationCounter errorInvocationCounter = new InvocationCounter();

        ArrayList<String> errors = new ArrayList<>();
        ValidationEntry<T> validationEntry = new ValidationEntry<>(
                () -> value,
                validators
        );

        Consumer<String> consumer = string -> {
            errorInvocationCounter.count++;
            errors.add(string);
        };

        assertEquals(expectedValid, validationEntry.validate(consumer));
        assertEquals(expectedError, String.join(" ", errors));
        assertEquals(expectedErrorCount, errorInvocationCounter.count);
    }
}
