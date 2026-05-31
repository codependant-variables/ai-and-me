package com.codependentvariables.aiandme.modules.validation.validators;

import com.codependentvariables.aiandme.modules.validation.validators.PasswordValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PasswordValidator.
 */
public class PasswordValidatorTest {

    private final PasswordValidator validator = new PasswordValidator();

    @Test
    public void correct_error_message() {
        String error = validator.validate("invalid", "My password");
        assertNotNull(error);
        assertEquals("My password is missing: 10 characters, 1 uppercase, 1 number, 1 symbol.", error);
    }

    @Test
    public void null_value() {
        assertNotNull(validator.validate(null, ""));
    }

    @Test
    public void empty() {
        assertNotNull(validator.validate("", ""));
    }

    @Test
    public void missing_lowercase() {
        assertNotNull(validator.validate("PASSWORD1!", ""));
    }

    @Test
    public void missing_uppercase() {
        assertNotNull(validator.validate("password1!", ""));
    }

    @Test
    public void missing_number() {
        assertNotNull(validator.validate("Password!", ""));
    }

    @Test
    public void missing_symbol() {
        assertNotNull(validator.validate("Password1", ""));
    }

    @Test
    public void only_letters() {
        assertNotNull(validator.validate("Password", ""));
    }

    @Test
    public void only_numbers() {
        assertNotNull(validator.validate("12345678", ""));
    }

    @Test
    public void valid_password_1() {
        assertNull(validator.validate("Password1!", ""));
    }

    @Test
    public void valid_password_2() {
        assertNull(validator.validate("Abcdef123$", ""));
    }

    @Test
    public void valid_password_3() {
        assertNull(validator.validate("MySecure1@", ""));
    }
}