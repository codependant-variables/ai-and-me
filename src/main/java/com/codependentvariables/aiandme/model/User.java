package com.codependentvariables.aiandme.model;

import com.codependentvariables.aiandme.services.UserService;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String salt;

    public User(String name, String email, String password, String salt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.salt = salt;
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
}