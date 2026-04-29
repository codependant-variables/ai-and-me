package com.codependentvariables.aiandme.model;

import java.sql.Timestamp;

public class Checkin {
    private int id;
    private int userId;
    private float aiUse;
    private float aiHappiness;
    private float aiDependence;
    private Timestamp completedAt;

    public Checkin(int userId, float aiUse, float aiHappiness, float aiDependence, Timestamp completedAt) {
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
}
