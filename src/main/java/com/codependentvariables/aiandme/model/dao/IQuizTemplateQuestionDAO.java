package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.model.QuizTemplateQuestion;

import java.util.List;

public interface IQuizTemplateQuestionDAO {
    /**
     * Adds a new question to a quiz template.
     * @param question The question to add.
     */
    void addQuestion(QuizTemplateQuestion question);

    /**
     * Updates an existing question.
     * @param question The question to update.
     */
    void updateQuestion(QuizTemplateQuestion question);

    /**
     * Deletes a question.
     * @param question The question to delete.
     */
    void deleteQuestion(QuizTemplateQuestion question);

    /**
     * Retrieves a question by id.
     * @param id The id of the question.
     * @return The question, or null if not found.
     */
    QuizTemplateQuestion get(int id);

    /**
     * Retrieves all questions belonging to a quiz template.
     * @param quizTemplateId The id of the quiz template.
     * @return List of questions for that template.
     */
    List<QuizTemplateQuestion> getQuestionsByTemplate(int quizTemplateId);
}

