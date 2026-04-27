package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.services.AuthService.HashResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {
    public final AuthService authService = AuthService.getInstance();

    public final String password = "password1";
    public final String hash = "Zl3QG3XY5/Gsus8Ec4WTi6jMcM7EkrCGCqBMgwwYUzg=";
    public final String salt = "sKH9XkLaT2i1XR687zjlHQ==";
    public final User user = new User("", "", hash, salt);

    @Test
    public void hash_password() {
        HashResult hashResult = authService.hash(password, salt);
        assertEquals(hash, hashResult.hash());
    }

    @Test
    public void compare_correct_password() {
        boolean isEqual = authService.comparePassword(user, "not the password");
        assertFalse(isEqual);
    }

    @Test
    public void compare_incorrect_password() {
        boolean isEqual = authService.comparePassword(user, password);
        assertTrue(isEqual);
    }

    @Test
    public void create_totp_seed() {
        String totpSeed = authService.createTotpSecret();
        assertNotNull(totpSeed);
    }

    @Test
    public void get_totp() {
        String totpSecret = authService.createTotpSecret();
        String totp = authService.getTotp(totpSecret);
        assertNotNull(totp);
        assertEquals(6, totp.length());
        for (char c : totp.toCharArray()) {
            assertTrue(Character.isDigit(c));
        }
    }

    @Test
    public void compare_invalid_totp() {
        String totpSecret = authService.createTotpSecret();
        user.setTotpSecret(totpSecret);
        boolean totpMatches = authService.compareTotp(user, "000000"); // This is 1/100000 to be a false negative
        assertFalse(totpMatches);
    }

    @Test
    public void compare_valid_totp() {
        String totpSecret = authService.createTotpSecret();
        user.setTotpSecret(totpSecret);
        String totp = authService.getTotp(user.getTotpSecret());
        boolean totpMatches = authService.compareTotp(user, totp);
        assertTrue(totpMatches);
    }
}
