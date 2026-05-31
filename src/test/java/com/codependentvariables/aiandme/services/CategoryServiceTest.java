package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.model.Category;
import com.codependentvariables.aiandme.model.dao.ICategoryDAO;
import com.codependentvariables.aiandme.model.mock.*;
import com.codependentvariables.aiandme.modules.state.AppState;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryServiceTest extends JavaFXTest {
    private static final AppState appState = AppState.getInstance();
    private static final ICategoryDAO categoryDAO = new MockCategoryDAO();
    private static final CategoryService categoryService = CategoryService.createForTest(categoryDAO);
    private static final UserService userService = UserService.createForTest(new MockCheckInDAO(), new MockQuizAttemptDAO(), new MockQuizAttemptQuestionDAO(), new MockQuizAttemptAnswerDAO(), new MockQuizTemplateDAO(), new MockQuizTemplateQuestionDAO(), new MockQuizTemplateAnswerDAO(), new MockUserDAO(), new MockUserPreferredCategoryDAO());

    @BeforeEach
    public void setupEach() {
        userService.deleteCurrentUser();
    }

    @Test
    public void add_category_logged_in() {
        Category category = new Category("Philosophy");
        userService.signup("", "", "");

        int startingSize = categoryDAO.getAll().size();
        categoryService.add(category);
        assertEquals(startingSize + 1, categoryDAO.getAll().size());
    }

    @Test
    public void add_category_logged_out() {
        Category category = new Category("New category");

        int startingSize = categoryDAO.getAll().size();
        assertEquals(startingSize, categoryDAO.getAll().size());
    }
}