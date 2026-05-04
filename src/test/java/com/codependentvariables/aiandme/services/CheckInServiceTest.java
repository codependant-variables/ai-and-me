package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.mock.MockCheckInDAO;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CheckInServiceTest {
    private static CheckInService checkInService;
    private static User user;

    @BeforeAll
    public static void setup() {
        checkInService = CheckInService.createForTest(new MockCheckInDAO());

        user = new User("Test user", "test@example.com", "hash", "salt");
        user.setId(1);
        AppState.getInstance().setCurrentUser(user);
    }

    @Test
    public void submit_invalid_check_in() {
        CheckIn checkIn = new CheckIn(user.getId(), 5.0f,  5.0f, 5.0f, null, LocalDateTime.now());
        checkInService.submitCheckIn(checkIn);
        CheckIn matchedCheckIn = checkInService.getById(checkIn.getId());
        assertNotNull(matchedCheckIn);
        assertEquals(user.getId(), matchedCheckIn.getUserId());
    }

    @Test
    public void submit_valid_check_in() {
        CheckIn checkIn = new CheckIn(user.getId(), 5.0f,  5.0f, 5.0f, "Ai usage looking ok", LocalDateTime.now());
        checkInService.submitCheckIn(checkIn);
        CheckIn matchedCheckIn = checkInService.getById(checkIn.getId());
        assertNotNull(matchedCheckIn);
        assertEquals(user.getId(), matchedCheckIn.getUserId());
    }
}