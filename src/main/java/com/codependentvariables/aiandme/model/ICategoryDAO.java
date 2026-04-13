package com.codependentvariables.aiandme.model;

import java.util.List;

public interface ICategoryDAO {
    /**
     * Adds a new category to the database.
     * @param category The category to add.
     */
    void addCategory(Category category);
    /**
     * Updates an existing category in the database.
     * @param category The category to update.
     */
    void updateCategory(Category category);
    /**
     * Deletes a category from the database.
     * @param category The category to delete.
     */
    void deleteCategory(Category category);
    /**
     * Retrieves a category from the database.
     * @param id The id of the category to retrieve.
     * @return The category with the given id, or null if not found.
     */
    Category getCategory(int id);
    /**
     * Retrieves all categories from the database.
     * @return A list of all categories in the database.
     */
    List<Category> getAllCategories();
}