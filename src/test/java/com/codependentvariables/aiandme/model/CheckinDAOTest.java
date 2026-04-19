package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CheckinDAOTest {
    private final ICheckinDAO checkinDAO = new MockCheckinDAO();

    @Test
    public void get() {
        Checkin checkin = checkinDAO.get(1);
        assertNotNull(checkin);
        assertEquals(6.4f, checkin.getAiUse());
    }
}
