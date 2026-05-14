package com.codependentvariables.aiandme.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizTemplate {
    private int id;
    private String name;
    private int categoryId;
    private int userId;
    private boolean isPuzzle;
    private String status;

    /* Storage in memory temporatily here */
    private final List<QuizTemplateQuestion> questions = new ArrayList<>();

    public QuizTemplate(String name, int categoryId, int userId, boolean isPuzzle, String status) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (status == null) throw new IllegalArgumentException("status must not be null");
        this.name = name;
        this.categoryId = categoryId;
        this.userId = userId;
        this.isPuzzle = isPuzzle;
        this.status = status;
    }

    /** Constructor with isPuzzle defaulting to false. */
    public QuizTemplate(String name, int categoryId, int userId, String status) {
        this(name, categoryId, userId, false, status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isPuzzle() {
        return isPuzzle;
    }

    public void setPuzzle(boolean isPuzzle) {
        this.isPuzzle = isPuzzle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /* returns view that cannot be modified directly */
    public List<QuizTemplateQuestion> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    /* Adds a question to the temporary array and ensures its quizTemplateId is consistent with this template. */
    public void addQuestion(QuizTemplateQuestion question) {
        question.setQuizTemplateId(this.id);
        questions.add(question);
    }

    /* Removes a question from the temporary array */
    public void removeQuestion(QuizTemplateQuestion question) {
        questions.remove(question);
    }

    /*
     * Replaces the entire in memory question list (i.e. after loading from DAO).
     */
    public void setQuestions(List<QuizTemplateQuestion> questions) {
        this.questions.clear();
        this.questions.addAll(questions);
    }

     /**
     * Returns {@code true} when properly structured
     * <ul>
     *    <li>has a non-empty name</li>
     *    <li>has a valid categoryId (greater than 0)</li>
     *    <li>has a valid status (e.g. "active" or "inactive")</li>
     *   </ul>
     */
     public boolean isValid() {
        if (name == null || name.isBlank()) return false;
        if (questions.isEmpty()) return false;
        return questions.stream().allMatch(QuizTemplateQuestion::isValid);
    }

    @Override
    public String toString() {
        return name;
    }
}
