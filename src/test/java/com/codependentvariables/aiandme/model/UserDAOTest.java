package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDAOTest {
    private final MockUserDAO userDAO = new MockUserDAO();

    @Test
    public void get() {
        User user = userDAO.get(1);
        assertNotNull(user);
        assertEquals("John Doe", user.getName());
    }

    @Test
    public void getByEmail() {
        User user = userDAO.getByEmail("johndoe@example.com");
        assertNotNull(user);
        assertEquals("John Doe", user.getName());
    }
}