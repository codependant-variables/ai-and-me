package com.codependentvariables.aiandme.model;

public class UserPreferredCategory {
    private int userId;
    private int categoryId;

    public UserPreferredCategory(int userId, int categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
