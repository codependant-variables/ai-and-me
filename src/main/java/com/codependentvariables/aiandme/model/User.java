package com.codependentvariables.aiandme.model;

import com.codependentvariables.aiandme.services.UserService;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String salt;
    private boolean isDarkMode = false;
    private boolean isVertical = false;
    private ArrayList<Category> preferredCategories;

    public User(String name, String email, String password, String salt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public User(String name, String email, String password, String salt, boolean isDarkMode, boolean isVertical) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.isDarkMode = isDarkMode;
        this.isVertical = isVertical;
        this.preferredCategories = new ArrayList<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        UserService userService = UserService.getInstance();
        UserService.HashResult hashResult = userService.hash(password);
        this.password = hashResult.hash();
        this.salt = hashResult.salt();
    }

    public String getSalt() {
        return salt;
    }

    public boolean getIsDarkMode() {
        return this.isDarkMode;
    }

    public void setIsDarkMode(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
    }

    public boolean getIsVertical() {
        return this.isVertical;
    }

    public void setIsVertical(boolean isVertical) {
        this.isVertical = isVertical;
    }

    public ArrayList<Category> getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(ArrayList<Category> preferredCategories) {
        this.preferredCategories = preferredCategories;
    }

    public void addPreferredCategory(Category category) {
        preferredCategories.add(category);
    }

    public void removePreferredCategory(Category category) {
        preferredCategories.remove(category);
    }
}