package com.codependentvariables.aiandme.state;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.model.User;

import static javafx.application.Application.setUserAgentStylesheet;

public class AppState {
    private static AppState instance;

    private User currentUser;

    private boolean isDarkMode = false;
    private boolean isVertical = false;

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
        if (user != null) {
            setIsDarkMode(currentUser.getIsDarkMode());
        }
    }

    public boolean getIsDarkMode() {
        return this.isDarkMode;
    }

    public void setIsDarkMode(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        if (currentUser != null) {
            currentUser.setIsDarkMode(isDarkMode);
        }
        setUserAgentStylesheet(isDarkMode ? AiAndMe.darkModeStylesheet : AiAndMe.lightModeStylesheet);
    }

    public boolean getIsVertical() {
        return this.isVertical;
    }

    public void setIsVertical(boolean isVertical) {
        this.isVertical = isVertical;
        if (currentUser != null) {
            currentUser.setIsVertical(isVertical);
        }
    }
}
