package com.codependentvariables.aiandme.state;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.model.User;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import static javafx.application.Application.setUserAgentStylesheet;

public class AppState {
    private static AppState instance;

    private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>(null);
    private final BooleanProperty isDarkMode = new SimpleBooleanProperty(false);
    private final BooleanProperty isVertical = new SimpleBooleanProperty(false);

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
        if (user != null) {
            setIsDarkMode(currentUser.get().getIsDarkMode());
        }
    }

    public BooleanProperty getObservableIsDarkMode() {
        return isDarkMode;
    }

    public boolean getIsDarkMode() {
        return this.isDarkMode.get();
    }

    public void setIsDarkMode(boolean isDarkMode) {
        this.isDarkMode.set(isDarkMode);
        if (currentUser.get() != null) {
            currentUser.get().setIsDarkMode(isDarkMode);
        }

        setUserAgentStylesheet(isDarkMode ? AiAndMe.darkModeStylesheet : AiAndMe.lightModeStylesheet);
    }

    public BooleanProperty getObservableIsVertical() {
        return isVertical;
    }

    public boolean getIsVertical() {
        return this.isVertical.get();
    }

    public void setIsVertical(boolean isVertical) {
        this.isVertical.set(isVertical);
        if (currentUser.get() != null) {
            currentUser.get().setIsVertical(isVertical);
        }
    }
}