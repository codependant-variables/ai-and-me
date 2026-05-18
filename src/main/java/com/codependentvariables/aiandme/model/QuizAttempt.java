package com.codependentvariables.aiandme.model;

import java.sql.Timestamp;

public class QuizAttempt {
    private int id; // PK
    private int userId; // FK
    private String name;
    private Timestamp completedAt;
    private int results;

    public QuizAttempt(int userId, String name, Timestamp completedAt, int results){
        this.userId = userId;
        this.name = name;
        this.completedAt = completedAt;
        this.results = results;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }
    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public int getResults() { return results; }
    public void setResults(int results) { this.results = results; }

}
