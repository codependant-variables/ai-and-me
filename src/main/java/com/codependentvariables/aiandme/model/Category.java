package com.codependentvariables.aiandme.model;

public class Category {
    private int id;
    private String name;
    private boolean visible;
    private boolean active;

    public Category(String name) {
        this.name = name;
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

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
