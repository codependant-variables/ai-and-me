package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.model.Category;

import java.util.List;

public interface IPreferredCategoryDAO {
    /**
     * Add a user's preferred category to the db.
     *
     * @param userId     The user ID to add.
     * @param categoryId The category to add.
     */
    void add(int userId, int categoryId);

    /**
     * Delete a user's preferred category from the db.
     *
     * @param userId     The user ID to delete.
     * @param categoryId The category ID to delete.
     */
    void delete(int userId, int categoryId);

    /**
     * Retrieves a list of a user's preferred categories.
     *
     * @param userId The user ID to retrieve.
     */
    List<Category> getByUserId(int userId);
}
