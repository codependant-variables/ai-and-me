package com.codependentvariables.aiandme.validation.validators;

import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.services.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {
    public EmailValidator emailValidator = new EmailValidator();

    @Test
    public void correct_error_message() {
        String error = emailValidator.validate("invalid input", "My email");
        assertNotNull(error);
        assertEquals("My email is not a valid email.", error);
    }

    @Test
    public void null_value() {
        assertNotNull(emailValidator.validate(null, ""));
    }

    @Test
    public void empty() {
        assertNotNull(emailValidator.validate("", ""));
    }

    @Test
    public void missing_at() {
        assertNotNull(emailValidator.validate("n1000001qut.edu.au", ""));
    }

    @Test
    public void missing_dot_after_at() {
        assertNotNull(emailValidator.validate("n1000001@quteduau", ""));
    }

    @Test
    public void dot_at_end() {
        assertNotNull(emailValidator.validate("n1000001@qut.edu.", ""));
    }

    @Test
    public void top_level_domain_less_than_2_characters() {
        assertNotNull(emailValidator.validate("n1000001@qut.edu.a", ""));
    }

    @Test
    public void between_at_and_last_dot_less_than_2_characters() {
        assertNotNull(emailValidator.validate("n1000001@q.au", ""));
    }

    @Test
    public void no_username() {
        assertNotNull(emailValidator.validate("@qut.edu.au", ""));
    }

    @Test
    public void valid_email_1() {
        assertNull(emailValidator.validate("n1000001@qut.edu.au", ""));
    }

    @Test
    public void valid_email_2() {
        assertNull(emailValidator.validate("a@aa.aa", ""));
    }

    @Test
    public void valid_email_3() {
        assertNull(emailValidator.validate("amy.adams@mydomain.gov", ""));
    }

    @Test
    public void valid_email_4() {
        assertNull(emailValidator.validate("bobthebuilder23@swagmail.net", ""));
    }
}