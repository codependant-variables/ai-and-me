package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.model.QuizTemplateAnswer;

import java.util.List;

public interface IQuizTemplateAnswerDAO {
    /**
     * Adds a new answer to a question.
     * @param answer The answer to add.
     */
    void addAnswer(QuizTemplateAnswer answer);

    /**
     * Updates an existing answer.
     * @param answer The answer to update.
     */
    void updateAnswer(QuizTemplateAnswer answer);

    /**
     * Deletes an answer.
     * @param answer The answer to delete.
     */
    void deleteAnswer(QuizTemplateAnswer answer);

    /**
     * Retrieves an answer by id.
     * @param id The id of the answer.
     * @return The answer, or null if not found.
     */
    QuizTemplateAnswer get(int id);

    /**
     * Retrieves all answers belonging to a question.
     * @param questionId The id of the question.
     * @return List of answers for that question.
     */
    List<QuizTemplateAnswer> getAnswersByQuestion(int questionId);
}

