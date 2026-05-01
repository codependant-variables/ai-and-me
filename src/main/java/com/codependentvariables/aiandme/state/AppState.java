package com.codependentvariables.aiandme.state;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.navigation.Router;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import static javafx.application.Application.setUserAgentStylesheet;

public class AppState {
    private static AppState instance;

    private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>(null);;

    private boolean isDarkMode = false;
    private boolean isVertical = false;

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public ObjectProperty<User> getObservableCurrentUser() {
        return currentUser;
    }

    public User getCurrentUser() {
        return currentUser.getValue();
    }

    public void setCurrentUser(User user) {
        currentUser.setValue(user);
        if (user != null && Router.hasApp()) {
            setIsDarkMode(currentUser.get().getIsDarkMode());
        }
    }

    public boolean getIsDarkMode() {
        return this.isDarkMode;
    }

    public void setIsDarkMode(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        if (currentUser.get() != null) {
            currentUser.get().setIsDarkMode(isDarkMode);
        }

        setUserAgentStylesheet(isDarkMode ? AiAndMe.darkModeStylesheet : AiAndMe.lightModeStylesheet);
    }

    public boolean getIsVertical() {
        return this.isVertical;
    }

    public void setIsVertical(boolean isVertical) {
        this.isVertical = isVertical;
        if (currentUser.get() != null) {
            currentUser.get().setIsVertical(isVertical);
        }
    }
}