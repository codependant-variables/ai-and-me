package com.codependentvariables.aiandme.model;

import java.util.List;

public interface ICategoryDAO {
    /**
     * Adds a new category to the database.
     * @param category The category to add.
     */
    void add(Category category);

    /**
     * Updates an existing category in the database.
     * @param category The category to update.
     */
    void update(Category category);

    /**
     * Deletes a category from the database.
     * @param category The category to delete.
     */
    void delete(Category category);

    /**
     * Retrieves all categories from the database.
     * @return A list of all categories in the database.
     */
    List<Category> getAll();

    /**
     * Retrieves a category from the database.
     * @param id The id of the category to retrieve.
     * @return The category with the given id, or null if not found.
     */
    Category get(int id);
}