package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.Category;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.dao.ICategoryDAO;
import com.codependentvariables.aiandme.model.dao.SqliteCategoryDAO;
import com.codependentvariables.aiandme.modules.Toast;
import com.codependentvariables.aiandme.modules.ToastMessageType;
import com.codependentvariables.aiandme.state.AppState;

import java.util.List;

public class CategoryService {
    // Singelton instence of the service
    private static CategoryService instance;
    // DAO to interact with DV
    private final ICategoryDAO categoryDAO;

    // Global app state - used to get current user
    private static final AppState appState = AppState.getInstance();

    // Default constructor
    private CategoryService() { this(new SqliteCategoryDAO()); }

    // Initialisation of constructor for unit tests
    CategoryService(ICategoryDAO categoryDAO) { this.categoryDAO = categoryDAO; }

    // Gets singleton instance of service
    public static CategoryService getInstance() {
        if (instance == null) {
            instance = new CategoryService();
        }
        return instance;
    }

    // Service method to get all categories from DB and convert to stream. Converts filtered results back into list.
    // Research: https://medium.com/%40AlexanderObregon/javas-stream-filter-method-explained-32185b8b4cf2
    public List<Category> getVisibleCategories() {
        return categoryDAO.getAll();
    }
    /*public List<Category> getVisibleCategories() {
        return categoryDAO.getAll().stream()
                // Keeps only categories marked as visible
                .filter(Category::isVisible)
                // Keeps only categories marked as active
                .filter(Category::isActive)
                .toList();
    }
*/
    // Creates and saves a new category
    public void submitCategory(Category category) {
        User currentUser = appState.getCurrentUser();

        if (currentUser == null) {
            safeToast("Category unable to be created","User must be logged in to create a category.", ToastMessageType.ERROR);
            throw new IllegalStateException("User must be logged in to create a category.");
        }

        categoryDAO.add(category);

        safeToast(
                "Category Created",
                "The category was created successfully",
                ToastMessageType.INFORMATION
        );
    }

    // Safe toast for unit testing
    private void safeToast(String title, String msg, ToastMessageType type) {
        try {
            Toast.addMessage(title, msg, type);
        } catch (Throwable ignored) {
            // Ignore in unit tests as JavaFX is not running
        }
    }
}
