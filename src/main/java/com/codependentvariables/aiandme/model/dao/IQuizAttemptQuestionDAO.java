package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.model.QuizAttemptQuestion;

import java.util.List;

/**
 * Interface for the QuizAttemptQuestion Data Access Object that handles
 * CRUD operations for the QuizAttemptQuestion class with the database.
 */
public interface IQuizAttemptQuestionDAO {
    /**
     * Adds a new quiz attempt question to the database.
 * @param quizAttemptQuestion the quiz attempt question to add
     */
    void add(QuizAttemptQuestion quizAttemptQuestion);

    /**
     * Updates an existing quiz attempt question in the database.
     * @param quizAttemptQuestion the quiz attempt question to update
     */
    void update(QuizAttemptQuestion quizAttemptQuestion);

    /**
     * Deletes a quiz attempt question from the database.
     * @param quizAttemptQuestion the quiz attempt question to delete
     */
    void delete(QuizAttemptQuestion quizAttemptQuestion);

    QuizAttemptQuestion get(int id);

    /**
     * Retrieves al questions for a given quiz attempt.
     * @param quizAttemptId the quiz attempt id
     * @return all questions for that quiz attempt
     */
    List<QuizAttemptQuestion> getByQuizAttemptId(int quizAttemptId);
}
