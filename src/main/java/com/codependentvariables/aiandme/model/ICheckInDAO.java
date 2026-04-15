package com.codependentvariables.aiandme.model;

import java.util.List;

/**
* Interface for CheckIn data access object.
* Handles CRUD operations for CheckIn class within the DB.
*/
public interface ICheckInDAO {
    /**
     * Adds a new check-in to the DB.
     * @param checkIn The check-in to add.
     */
    void addCheckIn(CheckIn checkIn);

    /**
     * Deletes a check-in from the DB.
     * @param checkIn The check-in to delete
     */
    void deleteCheckIn(CheckIn checkIn);

    /**
     * Retrieves a check-in by id.
     * @param id The id of the check-in to retrieve.
     * @return The check-in with the given id, or null if not found.
     */
    CheckIn get(int id);

    /**
     * Retrieves all check-ins from the DB.
     * @return A list of all check-ins in the DB, or null if none exist.
     */
    List<CheckIn> getAllCheckIns();

    /**
     * Retrieves all check-ins by a user from the DB.
     * @return A list of all check-ins by a specific user from the DB, or null if none found.
     */
    List<CheckIn> getAllCheckInsByUserId(int userId);
}
