package com.codependentvariables.aiandme.model;

import com.codependentvariables.aiandme.model.dao.ICheckInDAO;
import com.codependentvariables.aiandme.model.mock.MockCheckInDAO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CheckInDAOTest {
    private final ICheckInDAO checkInDAO = new MockCheckInDAO();

    @Test
    public void get() {
        CheckIn checkIn = new CheckIn(6.4f, 5.0f, 5.0f, "test", LocalDateTime.now());
        checkIn.setId(1);
        checkIn.setUserId(1);

        checkInDAO.add(checkIn);

        // I've read checkin so much it's starting to look like chicken
        CheckIn result = checkInDAO.get(1);

        assertNotNull(result);
        assertEquals(6.4f, result.getAiUse());
    }
}
