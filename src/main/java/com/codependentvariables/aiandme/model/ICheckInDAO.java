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
    public void addCheckIn(CheckIn checkIn);

    /**
     * Updates an existing check-in in the DB.
     * @param checkIn The check-in to update.
     */
    //public void updateCheckIn(CheckIn checkIn);
    // unsure if method needed, imo checkins are a one and done thing.
    // when is a user going to go back and update an existing checkin?

    /**
     * Deletes a check-in from the DB.
     * @param checkIn The check-in to delete
     */
    public void deleteCheckIn(CheckIn checkIn);

    /**
     * Retrieves a check-in by id.
     * @param id The id of the check-in to retrieve.
     * @return The check-in with the given id, or null if not found.
     */
    public CheckIn get(int id);

    /**
     * Retrieves all check-ins from the DB.
     * @return A list of all check-ins in the DB, or null if none exist.
     */
    public List<CheckIn> getAllCheckIns();

    /**
     * Retrieves all check-ins by a user from the DB.
     * @return A list of all check-ins by a specific user from the DB, or null if none found.
     */
    public List<CheckIn> getAllCheckInsByUserId(int userId);
}
