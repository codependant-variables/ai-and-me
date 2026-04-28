package com.codependentvariables.aiandme.model;

import com.codependentvariables.aiandme.model.dao.ICheckinDAO;
import com.codependentvariables.aiandme.model.mock.MockCheckinDAO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CheckinDAOTest {
    private final ICheckinDAO checkinDAO = new MockCheckinDAO();

    @Test
    public void get() {
        Checkin checkin = new Checkin(6.4f, 5.0f, 5.0f, "test", LocalDateTime.now());
        checkin.setId(1);
        checkin.setUserId(1);

        checkinDAO.add(checkin);

        // I've read checkin so much it's starting to look like chicken
        Checkin result = checkinDAO.get(1);

        assertNotNull(result);
        assertEquals(6.4f, result.getAiUse());
    }
}
