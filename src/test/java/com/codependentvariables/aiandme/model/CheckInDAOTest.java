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
        CheckIn checkIn = new CheckIn(1, 6.4f, 5.0f, 5.0f, "test", LocalDateTime.now());
        checkInDAO.add(checkIn);

        // I've read checkin so much it's starting to look like chicken
        CheckIn result = checkInDAO.get(checkIn.getId());

        assertNotNull(result);
        assertEquals(6.4f, result.getAiUse());
    }
}
