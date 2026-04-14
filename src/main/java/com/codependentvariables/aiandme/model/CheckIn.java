package com.codependentvariables.aiandme.model;

import java.time.LocalDateTime;

public class CheckIn {
    private int id;
    private int userId;
    private int use;
    private int happiness;
    private int dependence;
    private LocalDateTime completedAt;

    public CheckIn(int userId, int use, int happiness, int dependence) {
        this.userId = userId; // maybe in future change to currentUser.getId()? Open for comments.
        this.use = use;
        this.happiness = happiness;
        this.dependence = dependence;
        completedAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public int getUse() { return use; }
    public int getHappiness() { return happiness; }
    public int getDependence() { return dependence; }
    public LocalDateTime getCompletedAt() { return completedAt; }

}
