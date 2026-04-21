package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.mock.MockUserDAO;
import com.codependentvariables.aiandme.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest {
    public final UserService userService = new UserService(new MockUserDAO());

    public final String password = "password1";
    public final String hash = "Zl3QG3XY5/Gsus8Ec4WTi6jMcM7EkrCGCqBMgwwYUzg=";
    public final String salt = "sKH9XkLaT2i1XR687zjlHQ==";

    @Test
    public void hash() {
        UserService.HashResult hashResult = userService.hash(password, salt);
        assertEquals(hash, hashResult.hash());
    }

    @Test
    public void attemptLogin() {
        boolean isEqual = userService.attemptLogin(new User("", "", hash, salt), password);
        assertTrue(isEqual);
    }
}