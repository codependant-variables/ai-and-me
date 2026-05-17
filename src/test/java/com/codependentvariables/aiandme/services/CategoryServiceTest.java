package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.Category;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.mock.MockCategoryDAO;
import com.codependentvariables.aiandme.navigation.Dialogue;
import com.codependentvariables.aiandme.state.AppState;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;

public class CategoryServiceTest {
    private MockCategoryDAO mockCategoryDAO;
    private CategoryService categoryService;
    private Category category;

    @BeforeAll
    static void initUI() throws Exception {
        // Starts JavaFX before running any unit tests as it needs its own UI thread which isn't started by default in JUnit tests

        try {
            // Starts JavaFX runtime and creates the JavaFX application thread
            Platform.startup(new Runnable() {
                @Override
                public void run() {
                }
            });
        } catch (IllegalStateException ignored) {
            // Toolkit already initialized, ignore
        }
        Dialogue.disable();
    }

    @BeforeEach
    void setUp() {
        mockCategoryDAO = new MockCategoryDAO();
        categoryService = new CategoryService(mockCategoryDAO);

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
        categoryService.submitCategory(category);

        assertEquals(1, mockCategoryDAO.getAll().size());
    }

    // Throws error when user not logged in if submit attempted
    // Research: https://www.baeldung.com/junit-assert-exception
    @Test
    public void saveCategory_Error() {
        // remove the user created in setUp()
        AppState.getInstance().setCurrentUser(null);

        assertThrows(IllegalStateException.class, () -> {
            categoryService.submitCategory(category);
        });

        assertEquals(0, mockCategoryDAO.getAll().size());
    }
}
