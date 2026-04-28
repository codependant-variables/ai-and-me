package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.Checkin;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.mock.MockCheckinDAO;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckInServiceTest {
    private MockCheckinDAO mockCheckinDAO;
    private CheckInService checkInService;
    private Checkin checkin;

    @BeforeEach
    public void setUp() {
        mockCheckinDAO = new MockCheckinDAO();
        checkInService = new CheckInService(mockCheckinDAO);

        // reset AppState
        AppState.getInstance().setCurrentUser(null);

        // create user
        User user = new User("Test user", "test@example.com", "hash", "salt");
        user.setId(1);
        AppState.getInstance().setCurrentUser(user);

        // create checkin
        checkin = new Checkin(
                5.0f,
                5.0f,
                5.0f,
                "Ai usage looking ok",
                LocalDateTime.now()
        );
    }

    @Test
    public void submitCheckIn_setsUserId() {
        checkInService.submitCheckIn(checkin);

        assertEquals(1, checkin.getUserId());
    }

    @Test
    public void submitCheckIn_savesToDAO() {
        checkInService.submitCheckIn(checkin);

        assertEquals(1, mockCheckinDAO.getAll().size());
    }
}