package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.dao.IUserDAO;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileDeletionTest {

    private IUserDAO userDAO;
    private UserService userService;
    private AppState appState;

    private User testUser;

    @BeforeEach
    public void setup() {
        userDAO = mock(IUserDAO.class);
        appState = AppState.getInstance();

        testUser = new User(
                "Test",
                "Test User",
                "test@user.net",
                "hashedPassword"
        );

        appState.setCurrentUser(testUser);
        userService = new UserService(userDAO);
    }

    @Test
    public void deleteCurrentUser_deletes_user() {
        userService.deleteCurrentUser();

        verify(userDAO).delete(testUser);
    }

    @Test
    public void deleteCurrentUser_clears_current_user() {
        userService.deleteCurrentUser();

        assertNull(appState.getCurrentUser());
    }

    @Test
    public void deleteCurrentUser_does_nothing_when_no_user_logged_in() {
        appState.setCurrentUser(null);

        userService.deleteCurrentUser();

        verify(userDAO, never()).delete(any(User.class));
        assertNull(appState.getCurrentUser());
    }
}