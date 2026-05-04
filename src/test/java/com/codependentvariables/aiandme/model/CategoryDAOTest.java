package com.codependentvariables.aiandme.model;

import com.codependentvariables.aiandme.model.dao.ICategoryDAO;
import com.codependentvariables.aiandme.model.mock.MockCategoryDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryDAOTest {
    private final ICategoryDAO categoryDAO = new MockCategoryDAO();

    @Test
    public void get() {
        Category category = new Category("Arithmetic");
        categoryDAO.add(category);

        Category result = categoryDAO.get(1);
        assertNotNull(result);
        assertEquals("Arithmetic", result.getName());
    }
}