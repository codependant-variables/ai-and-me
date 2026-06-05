package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.model.UserPreferredCategory;

import java.util.List;

public interface IUserPreferredCategoryDAO {
    /**
     * Adds a user preferred category to the db.
     * @param userPreferredCategory The user preferred category to add.
     */
    void add(UserPreferredCategory userPreferredCategory);

    /**
     * Deletes a user preferred category from the db.
     * @param userPreferredCategory The user preferred category to delete.
     */
    void delete(UserPreferredCategory userPreferredCategory);

    /**
     * Retrieves user preferred categories by user Id.
     * @param userId The user ID to retrieve.
     */
    List<UserPreferredCategory> getByUserId(int userId);

    /**
     * Deletes user preferred categories by user Id.
     * @param userId User Id to delete by.
     */
    void deleteByUserId(int userId);
}
