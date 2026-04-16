package com.codependentvariables.aiandme.model;

import java.time.LocalDateTime;

public class CheckIn {
    private int id;
    private int userId;
    private float aiUse;
    private float aiHappiness;
    private float aiDependence;
    private LocalDateTime completedAt;

    public CheckIn(int userId, float aiUse, float aiHappiness,float aiDependence, LocalDateTime completedAt) {
        this.userId = userId;
        this.aiUse = aiUse;
        this.aiHappiness = aiHappiness;
        this.aiDependence = aiDependence;
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

    public float getUse() {
        return aiUse;
    }

    public float getHappiness() {
        return aiHappiness;
    }

    public float getDependence() {
        return aiDependence;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

}
