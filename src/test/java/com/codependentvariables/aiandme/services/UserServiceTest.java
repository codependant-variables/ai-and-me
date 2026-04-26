package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.mock.MockUserDAO;
import com.codependentvariables.aiandme.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    public final UserService userService = new UserService(new MockUserDAO());

    public final String password = "password1";
    public final String hash = "Zl3QG3XY5/Gsus8Ec4WTi6jMcM7EkrCGCqBMgwwYUzg=";
    public final String salt = "sKH9XkLaT2i1XR687zjlHQ==";
    public final User user = new User("", "amy.adams@mydomain.gov", hash, salt);

    @Test
    public void invalid_login_attempt() {
        LoginResult loginResult = userService.attemptLogin(user, "not the password");
        assertEquals(LoginResult.INVALID, loginResult);
    }

    @Test
    public void valid_login_attempt() {
        LoginResult loginResult = userService.attemptLogin(user, password);
        assertEquals(LoginResult.VALID, loginResult);
    }
}