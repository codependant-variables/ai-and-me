package com.codependentvariables.aiandme.model;

import java.sql.Timestamp;

public class CheckIn {
    private int id;
    private int userId;
    private float aiUse;
    private float aiHappiness;
    private float aiDependence;
    private String comment;
    private Timestamp completedAt;

    public CheckIn(float aiUse, float aiHappiness, float aiDependence, String comment, LocalDateTime completedAt) {
        this.aiUse = aiUse;
        this.aiHappiness = aiHappiness;
        this.aiDependence = aiDependence;
        this.comment = comment;
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

    public float getAiUse() {
        return aiUse;
    }

    public float getAiHappiness() {
        return aiHappiness;
    }

    public float getAiDependence() {
        return aiDependence;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }
    
    public String getComment() { return comment; }
}
