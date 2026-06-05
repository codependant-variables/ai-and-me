package com.codependentvariables.aiandme.modules.state;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.model.User;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import static javafx.application.Application.setUserAgentStylesheet;

/**
 * Stores and manages shared application state.
 */
public class AppState {
    private static AppState instance;

    private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>(null);
    private final BooleanProperty isDarkMode = new SimpleBooleanProperty(false);
    private final BooleanProperty isVertical = new SimpleBooleanProperty(false);
    private boolean isPuzzle = false;

    /**
     * Returns the application state instance.
     *
     * @return the singleton application state
     */
    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    /**
     * Returns the observable current user property.
     *
     * @return the current user property
     */
    public ObjectProperty<User> getObservableCurrentUser() {
        return currentUser;
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return the current user, or null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser.getValue();
    }

    public void setCurrentUser(User user) {
        currentUser.setValue(user);
        if (user != null) {
            setIsDarkMode(currentUser.get().getIsDarkMode());
        }
    }

    /**
     * Returns the observable dark mode property.
     *
     * @return the dark mode property
     */
    public BooleanProperty getObservableIsDarkMode() {
        return isDarkMode;
    }

    /**
     * Returns whether dark mode is enabled.
     *
     * @return true if dark mode is enabled
     */
    public boolean getIsDarkMode() {
        return this.isDarkMode.get();
    }

    /**
     * Enables or disables dark mode.
     *
     * @param isDarkMode true to enable dark mode
     */
    public void setIsDarkMode(boolean isDarkMode) {
        this.isDarkMode.set(isDarkMode);
        if (currentUser.get() != null) {
            currentUser.get().setIsDarkMode(isDarkMode);
        }

        setUserAgentStylesheet(isDarkMode ? AiAndMe.darkModeStylesheet : AiAndMe.lightModeStylesheet);
    }

    /**
     * Returns the observable orientation property.
     *
     * @return the orientation property
     */
    public BooleanProperty getObservableIsVertical() {
        return isVertical;
    }

    /**
     * Returns whether the application is in vertical layout mode.
     *
     * @return true if vertical mode is enabled
     */
    public boolean getIsVertical() {
        return this.isVertical.get();
    }

    /**
     * Sets the application layout orientation.
     *
     * @param isVertical true to use vertical layout
     */
    public void setIsVertical(boolean isVertical) {
        this.isVertical.set(isVertical);
        if (currentUser.get() != null) {
            currentUser.get().setIsVertical(isVertical);
        }
    }

    /**
     * Returns whether puzzle mode is enabled.
     *
     * @return true if puzzle mode is enabled
     */
    public boolean getIsPuzzle() {
        return isPuzzle;
    }

    /**
     * Sets whether puzzle mode is enabled.
     *
     * @param isPuzzles true to enable puzzle mode
     */
    public void setIsPuzzle(boolean isPuzzles) {
        this.isPuzzle = isPuzzles;
    }
}