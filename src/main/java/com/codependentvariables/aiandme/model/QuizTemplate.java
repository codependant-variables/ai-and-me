package com.codependentvariables.aiandme.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a quiz or puzzle template.
 * Stores quiz information, category details,
 * and all associated questions.
 */
public class QuizTemplate {
    private int id;
    private String name;
    private int categoryId;
    private int userId;
    private String status;
    private String category;
    private boolean isPuzzle;

    /* Storage in memory temporatily here */
    private final List<QuizTemplateQuestion> questions = new ArrayList<>();

    /**
     * Creates a new quiz or puzzle template.
     *
     * @param name template name
     * @param categoryId category ID
     * @param userId creator user ID
     * @param puzzle whether this template is a puzzle
     * @param status template status
     */
    public QuizTemplate(String name, int categoryId, int userId, boolean puzzle, String status) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (status == null) throw new IllegalArgumentException("status must not be null");
        this.name = name;
        this.categoryId = categoryId;
        this.userId = userId;
        this.isPuzzle = puzzle;
        this.status = status;
    }

    /**
     * Creates a new quiz template with puzzle defaulting to false.
     *
     * @param name template name
     * @param categoryId category ID
     * @param userId creator user ID
     * @param status template status
     */
    public QuizTemplate(String name, int categoryId, int userId, String status) {
        this(name, categoryId, userId, false, status);
    }

    /**
     * Gets the template ID.
     *
     * @return template ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the template ID.
     *
     * @param id template ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the template name.
     *
     * @return template name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the template name.
     *
     * @param name template name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the category ID.
     *
     * @return category ID
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the category ID.
     *
     * @param categoryId category ID
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Gets the creator user ID.
     *
     * @return user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the creator user ID.
     *
     * @param userId user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Checks whether this template is a puzzle.
     *
     * @return true if isPuzzle, false if quiz
     */
    public boolean isPuzzle() {
        return isPuzzle;
    }

    /**
     * Sets whether this template is a puzzle.
     *
     * @param puzzle true if puzzle, false if quiz
     */
    public void setPuzzle(boolean puzzle) {
        this.isPuzzle = puzzle;
    }

    /**
     * Gets the template status.
     *
     * @return template status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the template status.
     *
     * @param status template status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns an unmodifiable list of questions.
     *
     * @return question list
     */
    public List<QuizTemplateQuestion> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    /**
     * Adds a question to the template.
     * Also ensures the question uses this template ID.
     *
     * @param question question to add
     */
    public void addQuestion(QuizTemplateQuestion question) {
        question.setQuizTemplateId(this.id);
        questions.add(question);
    }

    /**
     * Removes a question from the template.
     *
     * @param question question to remove
     */
    public void removeQuestion(QuizTemplateQuestion question) {
        questions.remove(question);
    }

    /**
     * Replaces all template questions.
     * Commonly used after loading from the database.
     *
     * @param questions new question list
     */
    public void setQuestions(List<QuizTemplateQuestion> questions) {
        this.questions.clear();
        this.questions.addAll(questions);
    }

    /**
     * Validates the template structure.
     * Checks:
     * <ul>
     *     <li>Name is not empty</li>
     *     <li>Questions exist</li>
     *     <li>All questions are valid</li>
     * </ul>
     *
     * @return true if valid, otherwise false
     */
    public boolean isValid() {
        if (name == null || name.isBlank()) return false;
        if (questions.isEmpty()) return false;
        for (QuizTemplateQuestion q : questions) {
            if (!q.isValid()) return false;
        }
        return true;
    }

    /**
     * Returns the template name.
     *
     * @return template name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets the category name for this quiz or puzzle template.
     * If no category has been assigned, "General" is returned as a default value to prevent null errors in charts and analytics.
     *
     * @return category name
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category name.
     *
     * @param category category name
     */
    public void setCategory(String category) {
        this.category = category;
    }
}

