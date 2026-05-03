package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.dao.IUserDAO;
import com.codependentvariables.aiandme.model.dao.SqliteUserDAO;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.state.AppState;
import com.codependentvariables.aiandme.services.AuthService.HashResult;

public class UserService {
    private static UserService instance;

    private final IUserDAO userDAO;
    private static final AppState appState = AppState.getInstance();
    private static final AuthService authService = AuthService.getInstance();

    private UserService() {
        this(new SqliteUserDAO());
    }

    // Package-private constructor for unit tests
    UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    /**
     * Attempt user login with password.
     */
    public LoginResult attemptLogin(User user, String password) {
        boolean passwordMatches = authService.comparePassword(user, password);

        if (!passwordMatches) {
            return LoginResult.INVALID;
        }

        login(user);
        return LoginResult.VALID;
    }

    /**
     * Attempt user login with password and TOTP.
     */
    public LoginResult attemptLogin(User user, String password, String totp) {
        if (user.getTotpSecret() != null && totp.isEmpty()) {
            return LoginResult.REQUIRES_TOTP;
        }

        LoginResult loginResult = attemptLogin(user, password);
        if (user.getTotpSecret() == null) {
            return loginResult;
        }

        boolean totpMatches = authService.compareTotp(user, totp);
        if (!totpMatches) {
            return LoginResult.INVALID;
        }

        login(user);
        return LoginResult.VALID;
    }

    public User getByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    public boolean isUniqueEmail(String email) {
        return userDAO.getByEmail(email) == null;
    }

    public void signup(String name, String email, String password) {
        HashResult hashResult = authService.hash(password);
        User user = new User(name, email, hashResult.hash(), hashResult.salt());
        userDAO.add(user);
        login(user);
    }

    private void login(User user) {
        appState.setCurrentUser(user);
    }

    public void logout() {
        appState.setCurrentUser(null);
    }

    public void updateCurrentUser() {
        User currentUser = appState.getCurrentUser();
        if (currentUser != null) {
            userDAO.update(currentUser);
        }
    }

    public void deleteCurrentUser() {
        User currentUser = appState.getCurrentUser();
        if(currentUser == null) {
            return;
        }

        userDAO.delete(currentUser);
        appState.setCurrentUser(null);
    }
}