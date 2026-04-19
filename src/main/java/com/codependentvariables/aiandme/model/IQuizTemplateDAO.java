package com.codependentvariables.aiandme.model;

import java.util.List;

public interface IQuizTemplateDAO {
    /**
     * Adds a new quiz template to the database.
     * @param quizTemplate The quiz template to add.
     */
    void addTemplate(QuizTemplate quizTemplate);

    /**
     * Updates an existing quiz template in the database.
     * @param quizTemplate The quiz template to update.
     */
    void updateTemplate(QuizTemplate quizTemplate);

    /**
     * Deletes a quiz template from the database.
     * @param quizTemplate The quiz template to delete.
     */
    void deleteTemplate(QuizTemplate quizTemplate);

    /**
     * Retrieves a quiz template by id.
     * @param id The id of the quiz template to retrieve.
     * @return The quiz template with the given id, or null if not found.
     */
    QuizTemplate get(int id);

    /**
     * Retrieves all quiz templates.
     * @return A list of all quiz templates.
     */
    List<QuizTemplate> getAllTemplates();

    /**
     * Retrieves all quiz templates for a given category.
     * @param categoryId The category id to filter by.
     * @return A list of quiz templates belonging to the given category.
     */
    List<QuizTemplate> getTemplatesByCategory(int categoryId);
}

