package com.codependentvariables.aiandme.model;

import java.util.List;

/**
 * Interface for the QuizAttempt Data Access Object that handles
 * CRUD operations for the QuizAttempt class with the database.
 */
public interface IQuizAttemptDAO {
    /**
     * Adds a new quiz attempt to the database.
     * @param quizAttempt the quiz attempt to add
     */
    void addQuizAttempt(QuizAttempt quizAttempt);

    /**
     * Updates an existing quiz attempt in the database.
     * @param quizAttempt the quiz attempt to update
     */
    void updateQuizAttempt(QuizAttempt quizAttempt);

    /**
     * Deletes a quiz attempt from the database.
     * @param quizAttempt the quiz attempt to delete
     */
    void deleteQuizAttempt(QuizAttempt quizAttempt);

    /**
     * Retrieves all quiz attempts from the database.
     * @return a list of all quiz attempts
     */
    List<QuizAttempt> getAllQuizAttempts();

    /**
     * Retrieves a quiz attempt by id.
     * @param id the id of the quiz attempt to retrieve
     * @return the quiz attempt with the given id, or null if not found
     */
    QuizAttempt get(int id);

    /**
     * Retrieves all quiz attempts for a given user id.
     * @param userId the user id
     * @return a list of quiz attempts for that user
     */
    List<QuizAttempt> getByUserId(int userId);

    /**
     * Retrieves latest quiz attempt for a given user id.
     * @param userId the user id
     * @return the latest quiz attempt for that user
     */
    QuizAttempt getLatestByUserId(int userId);
}