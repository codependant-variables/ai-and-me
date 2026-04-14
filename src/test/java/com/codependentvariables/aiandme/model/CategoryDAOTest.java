package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryDAOTest {
    private final ICategoryDAO categoryDAO = new MockCategoryDAO();

    @Test
    public void get() {
        Category category = categoryDAO.get(1);
        assertNotNull(category);
        assertEquals("Arithmetic", category.getName());
    }
}