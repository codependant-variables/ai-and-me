package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.database.dao.SqliteCheckInDAO;
import com.codependentvariables.aiandme.database.dao.SqlitePreferredCategoryDAO;
import com.codependentvariables.aiandme.database.dao.SqliteQuizAttemptDAO;
import com.codependentvariables.aiandme.model.dao.ICheckInDAO;
import com.codependentvariables.aiandme.model.dao.IPreferredCategoryDAO;
import com.codependentvariables.aiandme.model.dao.IQuizAttemptDAO;
import com.codependentvariables.aiandme.model.dao.IUserDAO;
import com.codependentvariables.aiandme.database.dao.SqliteUserDAO;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.modules.state.AppState;
import com.codependentvariables.aiandme.services.AuthService.HashResult;

public class UserService {
    private static UserService instance;

    private static final AppState appState = AppState.getInstance();
    private static final AuthService authService = AuthService.getInstance();
    private final ICheckInDAO checkInDao;
    private final IPreferredCategoryDAO preferredCategoryDao;
    private final IQuizAttemptDAO quizAttemptDao;
    private final IUserDAO userDao;

    private UserService(ICheckInDAO checkInDao, IPreferredCategoryDAO preferredCategoryDao, IQuizAttemptDAO quizAttemptDao, IUserDAO userDao) {
        this.checkInDao = checkInDao;
        this.preferredCategoryDao = preferredCategoryDao;
        this.quizAttemptDao = quizAttemptDao;
        this.userDao = userDao;
    }

    /**
     * Default instance provider.
     */
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService(new SqliteCheckInDAO(), new SqlitePreferredCategoryDAO(), new SqliteQuizAttemptDAO(), new SqliteUserDAO());
        }
        return instance;
    }

    /**
     * Instance provider for unit testing.
     */
    public static UserService createForTest(ICheckInDAO checkInDao, IPreferredCategoryDAO preferredCategoryDao, IQuizAttemptDAO quizAttemptDao, IUserDAO userDao) {
        instance = new UserService(checkInDao, preferredCategoryDao, quizAttemptDao, userDao);
        return instance;
    }

    /**
     * Attempt a user login with password.
     * @param user User to attempt login for.
     * @param password Password to check.
     * @return Result of the login attempt.
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
     * Attempt a user login with password and TOTP.
     * @param user User to attempt login for.
     * @param password Password to check.
     * @param totp TOTP to check.
     * @return Result of the login attempt.
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

    /**
     * Gets a user by the provided email.
     * @param email Email to query with.
     * @return User with the provided email.
     */
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    /**
     * Checks if the provided email is not used by another user.
     * @param email Email to check.
     * @return True if email is unused.
     */
    public boolean isUniqueEmail(String email) {
        return userDao.getByEmail(email) == null;
    }

    /**
     * Adds user via DAO. Intended for user in unit tests only.
     * @param user User to add.
     */
    public void addUser(User user) {
        userDao.add(user);
    }

    /**
     * Signup a new user and immediately login.
     * @param name Name for new user.
     * @param email Email for new user.
     * @param password Password for new user.
     */
    public void signup(String name, String email, String password) {
        HashResult hashResult = authService.hash(password);
        User user = new User(name, email, hashResult.hash(), hashResult.salt());
        userDao.add(user);
        login(user);
    }

    /**
     * Set current user to app state. Also updates last login, used for inactive profile deletion.
     * @param user User to login.
     */
    private void login(User user) {
        appState.setCurrentUser(user);
        userDao.updateLastLoginAt(user);
    }

    /**
     * Unset current user from app state.
     */
    public void logout() {
        appState.setCurrentUser(null);
    }

    /**
     * Update app state current user in the database based on current state.
     */
    public void updateCurrentUser() {
        User currentUser = appState.getCurrentUser();
        if (currentUser != null) {
            userDao.update(currentUser);
        }
    }

    /**
     * Delete app state current user.
     */
    public void deleteCurrentUser() {
        User currentUser = appState.getCurrentUser();
        if(currentUser == null) {
            return;
        }

        userDao.delete(currentUser);
        appState.setCurrentUser(null);
    }

    public void deleteCurrentUserData() {
        int userId = appState.getCurrentUser().getId();
        deleteCurrentUserCheckIns();
        preferredCategoryDao.deleteByUserId(userId);
        quizAttemptDao.deleteByUserId(userId);
    }

    public void deleteCurrentUserCheckIns() {
        checkInDao.deleteByUserId(appState.getCurrentUser().getId());
    }

    public void deleteCurrentUserQuizAttempts() {
        quizAttemptDao.deleteByUserId(appState.getCurrentUser().getId());
    }
}