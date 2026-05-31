package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.database.dao.*;
import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;
import com.codependentvariables.aiandme.modules.state.AppState;
import com.codependentvariables.aiandme.services.AuthService.HashResult;
import javafx.stage.DirectoryChooser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class UserService {
    private static UserService instance;

    private static final AppState appState = AppState.getInstance();
    private static final AuthService authService = AuthService.getInstance();
    private final ICheckInDAO checkInDAO;
    private final IQuizAttemptDAO quizAttemptDAO;
    private final IQuizAttemptQuestionDAO quizAttemptQuestionDAO;
    private final IQuizAttemptAnswerDAO quizAttemptAnswerDAO;
    private final IQuizTemplateDAO quizTemplateDAO;
    private final IQuizTemplateQuestionDAO quizTemplateQuestionDAO;
    private final IQuizTemplateAnswerDAO quizTemplateAnswerDAO;
    private final IUserDAO userDAO;
    private final IUserPreferredCategoryDAO userPreferredCategoryDAO;

    private UserService(ICheckInDAO checkInDAO, IQuizAttemptDAO quizAttemptDAO, IQuizAttemptQuestionDAO quizAttemptQuestionDAO, IQuizAttemptAnswerDAO quizAttemptAnswerDAO, IQuizTemplateDAO quizTemplateDAO, IQuizTemplateQuestionDAO quizTemplateQuestionDAO, IQuizTemplateAnswerDAO quizTemplateAnswerDAO, IUserDAO userDAO, IUserPreferredCategoryDAO userPreferredCategoryDAO) {
        this.checkInDAO = checkInDAO;
        this.quizAttemptDAO = quizAttemptDAO;
        this.quizAttemptQuestionDAO = quizAttemptQuestionDAO;
        this.quizAttemptAnswerDAO = quizAttemptAnswerDAO;
        this.quizTemplateDAO = quizTemplateDAO;
        this.quizTemplateQuestionDAO = quizTemplateQuestionDAO;
        this.quizTemplateAnswerDAO = quizTemplateAnswerDAO;
        this.userDAO = userDAO;
        this.userPreferredCategoryDAO = userPreferredCategoryDAO;
    }

    /**
     * Default instance provider.
     */
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService(new SqliteCheckInDAO(), new SqliteQuizAttemptDAO(), new SqliteQuizAttemptQuestionDAO(), new SqliteQuizAttemptAnswerDAO(), new SqliteQuizTemplateDAO(), new SqliteQuizTemplateQuestionDAO(), new SqliteQuizTemplateAnswerDAO(), new SqliteUserDAO(), new SqliteUserPreferredCategoryDAO());
        }
        return instance;
    }

    /**
     * Instance provider for unit testing.
     */
    public static UserService createForTest(ICheckInDAO checkInDAO, IQuizAttemptDAO quizAttemptDAO, IQuizAttemptQuestionDAO quizAttemptQuestionDAO, IQuizAttemptAnswerDAO quizAttemptAnswerDAO, IQuizTemplateDAO quizTemplateDAO, IQuizTemplateQuestionDAO quizTemplateQuestionDAO, IQuizTemplateAnswerDAO quizTemplateAnswerDAO, IUserDAO userDAO, IUserPreferredCategoryDAO userPreferredCategoryDAO) {
        instance = new UserService(checkInDAO, quizAttemptDAO, quizAttemptQuestionDAO, quizAttemptAnswerDAO, quizTemplateDAO, quizTemplateQuestionDAO, quizTemplateAnswerDAO, userDAO, userPreferredCategoryDAO);
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
        return userDAO.getByEmail(email);
    }

    /**
     * Checks if the provided email is not used by another user.
     * @param email Email to check.
     * @return True if email is unused.
     */
    public boolean isUniqueEmail(String email) {
        return userDAO.getByEmail(email) == null;
    }

    /**
     * Adds user via DAO. Intended for user in unit tests only.
     * @param user User to add.
     */
    public void addUser(User user) {
        userDAO.add(user);
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
        userDAO.add(user);
        login(user);
    }

    /**
     * Set current user to app state. Also updates last login, used for inactive profile deletion.
     * @param user User to login.
     */
    private void login(User user) {
        appState.setCurrentUser(user);
        userDAO.updateLastLoginAt(user);
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
            userDAO.update(currentUser);
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

        userDAO.delete(currentUser);
        appState.setCurrentUser(null);
    }

    /**
     * Delete app state current user data.
     */
    public void deleteCurrentUserData() {
        deleteCurrentUserCheckIns();
        userPreferredCategoryDAO.deleteByUserId(appState.getCurrentUser().getId());
        deleteCurrentUserQuizAttempts();
    }

    /**
     * Delete app state current user check-ins.
     */
    public void deleteCurrentUserCheckIns() {
        checkInDAO.deleteByUserId(appState.getCurrentUser().getId());
    }

    /**
     * Delete app state current user quiz attempts;
     */
    public void deleteCurrentUserQuizAttempts() {
        quizAttemptDAO.deleteByUserId(appState.getCurrentUser().getId());
    }

    /**
     * Export current user data as a JSON file.
     */
    public void exportCurrentUserData() {
        updateCurrentUser();
        User user = appState.getCurrentUser();
        int userId = user.getId();

        JSONObject json = new JSONObject();
        JSONObject jsonUser = new JSONObject(user);
        jsonUser.remove("password");
        jsonUser.remove("salt");
        json.put("user", jsonUser);
        json.put("preferredCategories", userPreferredCategoryDAO.getByUserId(userId));
        json.put("checkIns", checkInDAO.getAllByUserId(userId));
        json.put("quizTemplates", exportQuizTemplates(userId));
        json.put("quizAttempts", exportQuizAttempts(userId));

        DirectoryChooser directoryChooser = new DirectoryChooser();
        //directoryChooser.setTitle("Select Project Folder");
        String userHome = System.getProperty("user.home");
        directoryChooser.setInitialDirectory(new File(userHome));
        File selectedDirectory = directoryChooser.showDialog(null);
        File jsonFile = new File(selectedDirectory, String.format("user-data_%1$tY-%1$tm-%1$td_%1$tH-%1$tM-%1$tS.json", LocalDateTime.now()));

        try (FileWriter writer = new FileWriter(jsonFile)) {
            json.write(writer, 4, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JSONArray exportQuizTemplates(int userId) {
        List<QuizTemplate> quizTemplates = quizTemplateDAO.getByUserId(userId);
        return new JSONArray(quizTemplates.stream().map(x -> {
            List<QuizTemplateQuestion> quizTemplateQuestions = quizTemplateQuestionDAO.getByTemplateId(x.getId());
            JSONObject jsonQuizTemplate = new JSONObject(x);
            jsonQuizTemplate.put("questions", quizTemplateQuestions.stream().map(y -> {
                List<QuizTemplateAnswer> quizTemplateAnswers = quizTemplateAnswerDAO.getByQuestionId(y.getId());
                JSONObject jsonQuizTemplateQuestion = new JSONObject(y);
                jsonQuizTemplateQuestion.put("answers", quizTemplateAnswers);
                return jsonQuizTemplateQuestion;
            }).toList());
            return jsonQuizTemplate;
        }).toList());
    }

    private JSONArray exportQuizAttempts(int userId) {
        List<QuizAttempt> quizAttempts = quizAttemptDAO.getByUserId(userId);
        return new JSONArray(quizAttempts.stream().map(x -> {
            List<QuizAttemptQuestion> quizAttemptQuestions = quizAttemptQuestionDAO.getByAttemptId(x.getId());
            JSONObject jsonQuizTemplate = new JSONObject(x);
            jsonQuizTemplate.put("questions", quizAttemptQuestions.stream().map(y -> {
                List<QuizAttemptAnswer> quizAttemptAnswers = quizAttemptAnswerDAO.getByQuestionId(y.getId());
                JSONObject jsonQuizAttemptQuestion = new JSONObject(y);
                jsonQuizAttemptQuestion.put("answers", quizAttemptAnswers);
                return jsonQuizAttemptQuestion;
            }).toList());
            return jsonQuizTemplate;
        }).toList());
    }

    public List<UserPreferredCategory> getCurrentUserPreferredCategories() {
        return userPreferredCategoryDAO.getByUserId(appState.getCurrentUser().getId());
    }

    public void removeUserPreferredCategory(UserPreferredCategory userPreferredCategory) {
        userPreferredCategoryDAO.delete(userPreferredCategory);
    }
}