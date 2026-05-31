package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.dao.*;
import com.codependentvariables.aiandme.modules.state.AppState;

public class UserDataDeletionService {
    private static UserDataDeletionService instance;

    private static final AppState appState = AppState.getInstance();
    private final IPreferredCategoryDAO prefCatDAO;
    private final ICheckInDAO checkInDAO;
    private final IQuizAttemptDAO quizAttDAO;

    private UserDataDeletionService(IPreferredCategoryDAO prefCatDAO, ICheckInDAO checkInDAO, IQuizAttemptDAO quizAttDAO) {
        this.prefCatDAO = prefCatDAO;
        this.checkInDAO = checkInDAO;
        this.quizAttDAO = quizAttDAO;
    }

    public static UserDataDeletionService getInstance() {
        if (instance == null) {
            instance = new UserDataDeletionService(new SqlitePreferredCategoryDAO(), new SqliteCheckInDAO(), new SqliteQuizAttemptDAO());
        }
        return instance;
    }

    public static UserDataDeletionService createForTest(IPreferredCategoryDAO prefCatDAO, ICheckInDAO checkInDAO, IQuizAttemptDAO quizAttDAO) {
        instance = new UserDataDeletionService(prefCatDAO, checkInDAO, quizAttDAO);
        return instance;
    }

    public void deleteCurrentUserData() {
        prefCatDAO.deleteByUserId(appState.getCurrentUser().getId());
        checkInDAO.deleteByUserId(appState.getCurrentUser().getId());
        quizAttDAO.deleteByUserId(appState.getCurrentUser().getId());
    }

    public void deleteCurrentUserCheckIns() {
        checkInDAO.deleteByUserId(appState.getCurrentUser().getId());
    }

    public void deleteCurrentUserQuizAttempts() {
        quizAttDAO.deleteByUserId(appState.getCurrentUser().getId());
    }
}
