package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.dao.*;
import com.codependentvariables.aiandme.state.AppState;

public class UserDataDeletionService {
    private static UserDataDeletionService instance;

    private static final AppState appState = AppState.getInstance();
    private final SqlitePreferredCategoryDAO prefCatDAO;
    private final SqliteCheckInDAO checkInDAO;
    private final SqliteQuizAttemptDAO quizAttDAO;

    private UserDataDeletionService() {
        prefCatDAO = new SqlitePreferredCategoryDAO();
        checkInDAO = new SqliteCheckInDAO();
        quizAttDAO = new SqliteQuizAttemptDAO();
    }

    UserDataDeletionService(SqlitePreferredCategoryDAO prefCatDAO, SqliteCheckInDAO checkInDAO, SqliteQuizAttemptDAO quizAttDAO) {
        this.prefCatDAO = prefCatDAO;
        this.checkInDAO = checkInDAO;
        this.quizAttDAO = quizAttDAO;
    }

    public static UserDataDeletionService getInstance() {
        if (instance == null) {
            instance = new UserDataDeletionService();
        }
        return instance;
    }

    public void deleteCurrentUserData() {
        prefCatDAO.deleteAllByUserId(appState.getCurrentUser().getId());
        checkInDAO.deleteAllByUserId(appState.getCurrentUser().getId());
        quizAttDAO.deleteAllByUserId(appState.getCurrentUser().getId());
    }
}
