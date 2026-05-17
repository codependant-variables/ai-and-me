package com.codependentvariables.aiandme.validation.validators;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {
    private final EmailValidator validator = new EmailValidator();

    @BeforeEach
    void setUp() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit already initialised - ignore
        }
    }
    
    @Test
    public void correct_error_message() {
        String error = validator.validate("invalid input", "My email");
        assertNotNull(error);
        assertEquals("My email is not a valid email.", error);
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
    public void missing_at() {
        assertNotNull(validator.validate("n1000001qut.edu.au", ""));
    }

    @Test
    public void missing_dot_after_at() {
        assertNotNull(validator.validate("n1000001@quteduau", ""));
    }

    @Test
    public void dot_at_end() {
        assertNotNull(validator.validate("n1000001@qut.edu.", ""));
    }

    @Test
    public void top_level_domain_less_than_2_characters() {
        assertNotNull(validator.validate("n1000001@qut.edu.a", ""));
    }

    @Test
    public void between_at_and_last_dot_less_than_2_characters() {
        assertNotNull(validator.validate("n1000001@q.au", ""));
    }

    @Test
    public void no_username() {
        assertNotNull(validator.validate("@qut.edu.au", ""));
    }

    @Test
    public void valid_email_1() {
        assertNull(validator.validate("n1000001@qut.edu.au", ""));
    }

    @Test
    public void valid_email_2() {
        assertNull(validator.validate("a@aa.aa", ""));
    }

    @Test
    public void valid_email_3() {
        assertNull(validator.validate("amy.adams@mydomain.gov", ""));
    }

    @Test
    public void valid_email_4() {
        assertNull(validator.validate("bobthebuilder23@swagmail.net", ""));
    }
}