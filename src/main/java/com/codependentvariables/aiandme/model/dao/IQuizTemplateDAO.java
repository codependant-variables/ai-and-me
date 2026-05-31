package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.model.QuizTemplate;

import java.util.List;

public interface IQuizTemplateDAO {
    /**
     * Adds a new quiz template to the database.
     * @param quizTemplate The quiz template to add.
     */
    void add(QuizTemplate quizTemplate);

    /**
     * Updates an existing quiz template in the database.
     * @param quizTemplate The quiz template to update.
     */
    void update(QuizTemplate quizTemplate);

    /**
     * Deletes a quiz template from the database.
     * @param quizTemplate The quiz template to delete.
     */
    void delete(QuizTemplate quizTemplate);

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
    List<QuizTemplate> getAll();

    /**
     * Retrieves all non-puzzle quiz templates.
     * @return A list of all quiz templates.
     */
    List<QuizTemplate> getAllQuizzes();

    /**
     * Retrieves all puzzle quiz templates.
     * @return A list of all quiz templates.
     */
    List<QuizTemplate> getAllPuzzles();

    /**
     * Retrieves all quiz templates for a given category.
     * @param categoryId The category id to filter by.
     * @return A list of quiz templates belonging to the given category.
     */
    List<QuizTemplate> getByCategoryId(int categoryId);

    /**
     * Retrieves all quiz templates created by a given user.
     * @param userId The user id (foreign key) to filter by.
     * @return A list of quiz templates belonging to the given user.
     */
    List<QuizTemplate> getByUserId(int userId);
}

