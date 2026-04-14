package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDAOTest {
    private final IUserDAO userDAO = new MockUserDAO();

    @Test
    public void get() {
        User user = userDAO.get(1);
        assertNotNull(user);
        assertEquals("Amy Adams", user.getName());
    }

    @Test
    public void getByEmail() {
        User user = userDAO.getByEmail("bobthebuilder23@swagmail.net");
        assertNotNull(user);
        assertEquals("Bob Builder", user.getName());
    }
}