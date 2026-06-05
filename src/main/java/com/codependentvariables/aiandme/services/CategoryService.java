package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.Category;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.dao.ICategoryDAO;
import com.codependentvariables.aiandme.database.dao.SqliteCategoryDAO;
import com.codependentvariables.aiandme.modules.toast.Toast;
import com.codependentvariables.aiandme.modules.toast.ToastMessageType;
import com.codependentvariables.aiandme.modules.state.AppState;

import java.util.List;

/**
 * Provides operations for creating and retrieving categories.
 */
public class CategoryService {
    private static CategoryService instance;

    private final ICategoryDAO categoryDAO;
    private static final AppState appState = AppState.getInstance();

    private CategoryService(ICategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    /**
     * Returns the CategoryService instance.
     *
     * @return the singleton CategoryService instance
     */
    public static CategoryService getInstance() {
        if (instance == null) {
            instance = new CategoryService(new SqliteCategoryDAO());
        }
        return instance;
    }

    /**
     * Creates a CategoryService instance for unit testing.
     *
     * @param categoryDAO DAO implementation to use
     * @return configured CategoryService instance
     */
    public static CategoryService createForTest(ICategoryDAO categoryDAO) {
        instance = new CategoryService(categoryDAO);
        return instance;
    }

    /**
     * Gets all categories.
     * @return List of categories.
     */
    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    /**
     * Checks if the provided name is not used by another category.
     * @param name Name to check.
     * @return True if name is unused.
     */
    public boolean isUniqueName(String name) {
        return categoryDAO.getByName(name) == null;
    }

    /**
     * Adds a category via the DAO.
     * @param category Category to add.
     */
    public void add(Category category) {
        User currentUser = appState.getCurrentUser();

        if (currentUser == null) {
            Toast.addMessage("Category unable to be created", "User must be logged in to create a category", ToastMessageType.ERROR);
            return;
        }

        categoryDAO.add(category);
        Toast.addMessage("Category Created", "The category was created successfully", ToastMessageType.INFORMATION);
    }
}
