package com.codependentvariables.aiandme.model;

import java.sql.Timestamp;

public class QuizAttempt {
    private int id; // PK
    private int userId; // FK
    private String name;
    private Timestamp completedAt;

    public QuizAttempt(int userId, String name, Timestamp completedAt){
        this.userId = userId;
        this.name = name;
        this.completedAt = completedAt;
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
}
