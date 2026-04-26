package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.model.QuizAttemptAnswer;

import java.util.List;

/**
 * Interface for the QuizAttemptAnswer Data Access Object for class in database
 */
public interface IQuizAttemptAnswerDAO {
    /**
     * Adds a new quiz attempt answer to the database.
     * @param quizAttemptAnswer the quiz attempt answer to add
     */
    void add(QuizAttemptAnswer quizAttemptAnswer);

    /**
     * Deletes a quiz attempt answer from the database.
     * @param quizAttemptAnswer the quiz attempt answer to delete
     */
    void delete(QuizAttemptAnswer quizAttemptAnswer);

    /**
     * Retrieves all answers for a given quiz attempt question.
     * @param quizAttemptQuestionId the quiz attempt question id
     * @return all answers for that question
     */
    List<QuizAttemptAnswer> getByQuizAttemptQuestionId(int quizAttemptQuestionId);
}