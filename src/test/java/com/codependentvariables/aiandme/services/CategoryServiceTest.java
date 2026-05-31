package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.model.Category;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.dao.ICategoryDAO;
import com.codependentvariables.aiandme.model.mock.MockCategoryDAO;
import com.codependentvariables.aiandme.modules.state.AppState;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryServiceTest extends JavaFXTest {
    private ICategoryDAO categoryDAO;
    private CategoryService categoryService;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryDAO = new MockCategoryDAO();
        categoryService = new CategoryService(categoryDAO);

        // Resets user before test
        AppState.getInstance().setCurrentUser(null);

        // Create user
        User user = new User("Test user", "test@example.com", "hash", "salt");
        user.setId(1);
        AppState.getInstance().setCurrentUser(user);

        // Create category
        category = new Category("Philosophy");
    }

    // Submits and adds category when user is logged in
    @Test
    public void saveCategory() {
        int startingSize = categoryDAO.getAll().size();
        categoryService.submitCategory(category);
        assertEquals(startingSize + 1, categoryDAO.getAll().size());
    }

    // Throws error when user not logged in if submit attempted
    // Research: https://www.baeldung.com/junit-assert-exception
    @Test
    public void saveCategory_Error() {
        int startingSize = categoryDAO.getAll().size();

        // remove the user created in setUp()
        AppState.getInstance().setCurrentUser(null);

        assertThrows(IllegalStateException.class, () -> {
            categoryService.submitCategory(category);
        });

        assertEquals(startingSize, categoryDAO.getAll().size());
    }
}