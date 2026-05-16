package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.model.User;

import java.util.List;

/**
 * Interface for the User Data Access Object that handles
 * the CRUD operations for the User class with the database.
 */
public interface IUserDAO {
    /**
     * Adds a new user to the database.
     * @param user The user to add.
     */
    void add(User user);
    /**
     * Updates an existing user in the database.
     * @param user The user to update.
     */
    void update(User user);
    /**
     * Updates an existing user's last login time in the database.
     * @param user the user to update.
     */
    void updateLastLoginAt(User user);
    /**
     * Deletes a user from the database.
     * @param user The user to delete.
     */
    void delete(User user);
    /**
     * Retrieves all users from the database.
     * @return A list of all users in the database.
     */
    List<User> getAll();
    /**
     * Retrieves a user by id.
     * @param id The id of the user to retrieve.
     * @return The user with the given id, or null if not found.
     */
    User get(int id);
    /**
     * Retrieves a user by email.
     * @param email The email of the user to retrieve.
     * @return The user with the given email, or null if not found.
     */
    User getByEmail(String email);
}