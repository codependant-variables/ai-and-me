package com.codependentvariables.aiandme.model;

import java.sql.Timestamp;

/**
 * Stores information about a completed quiz or puzzle attempt.
 */
public class QuizAttempt {
    private int id; // PK
    private int userId; // FK
    private String name;
    private Timestamp completedAt;
    private int results;
    private String category;
    private boolean puzzle;

    /**
     * Creates a new quiz attempt.
     *
     * @param userId ID of the user
     * @param name Name of the quiz or puzzle
     * @param completedAt Completion timestamp
     * @param results Attempt score
     * @param category Attempt category
     * @param puzzle Whether the attempt is a puzzle
     */
    public QuizAttempt(int userId, String name, Timestamp completedAt, int results, String category, boolean puzzle){
        this.userId = userId;
        this.name = name;
        this.completedAt = completedAt;
        this.results = results;
        this.category = category;
        this.puzzle = puzzle;
    }

    /**
     * Gets the attempt ID.
     *
     * @return attempt ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the attempt ID.
     *
     * @param id attempt ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user ID.
     *
     * @return user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }


    /**
     * Gets the quiz or puzzle name.
     *
     * @return attempt name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the quiz or puzzle name.
     *
     * @param name attempt name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the completion timestamp.
     *
     * @return completion date/time
     */
    public Timestamp getCompletedAt() {
        return completedAt;
    }

    /**
     * Sets the completion timestamp.
     *
     * @param completedAt completion date/time
     */
    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    /**
     * Gets the attempt score.
     *
     * @return attempt score
     */
    public int getResults() { return results; }

    /**
     * Sets the attempt score.
     *
     * @param results attempt score
     */
    public void setResults(int results) { this.results = results; }

    /**
     * Gets the attempt category.
     *
     * @return category name
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the attempt category.
     *
     * @param category category name
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Checks whether the attempt is a puzzle.
     *
     * @return true if puzzle, false if quiz
     */
    public boolean isPuzzle() {
        return puzzle;
    }

    /**
     * Sets whether the attempt is a puzzle.
     *
     * @param puzzle true if puzzle, false if quiz
     */
    public void setPuzzle(boolean puzzle) {
        this.puzzle = puzzle;
    }
}
