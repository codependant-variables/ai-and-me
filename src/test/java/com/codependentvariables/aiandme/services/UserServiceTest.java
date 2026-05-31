package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.model.mock.MockUserDAO;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.modules.state.AppState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends JavaFXTest {
    public static UserService userService;
    public static AppState appState;

    public final String password = "password1";
    public final String hash = "Zl3QG3XY5/Gsus8Ec4WTi6jMcM7EkrCGCqBMgwwYUzg=";
    public final String salt = "sKH9XkLaT2i1XR687zjlHQ==";
    public final User user = new User("", "amy.adams@mydomain.gov", hash, salt);

    @BeforeAll
    public static void setup() {
        userService = UserService.createForTest(new MockUserDAO());
        appState = AppState.getInstance();
    }

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

    @Test
    public void delete_current_user() {
        appState.setCurrentUser(user);
        userService.deleteCurrentUser();

        assertNull(appState.getCurrentUser());
    }

    @Test
    public void delete_current_user_without_current_user() {
        appState.setCurrentUser(null);
        userService.deleteCurrentUser();
        assertNull(appState.getCurrentUser());
    }
}