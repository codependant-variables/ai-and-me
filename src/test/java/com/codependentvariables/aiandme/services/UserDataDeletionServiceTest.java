package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataDeletionServiceTest {
    private final SqlitePreferredCategoryDAO testPrefCatDAO = new SqlitePreferredCategoryDAO();
    private final SqliteCheckInDAO testCheckInDAO = new SqliteCheckInDAO();
    private final SqliteQuizAttemptDAO testQuizAttDAO = new SqliteQuizAttemptDAO();
    private final UserDataDeletionService testDelService = new UserDataDeletionService(testPrefCatDAO, testCheckInDAO, testQuizAttDAO);
    private final UserService testUserService = new UserService(new SqliteUserDAO());
    private final AppState testState = AppState.getInstance();

    @BeforeEach
    public void setup() {
        testUserService.signup("Test User", "test@user.com", "#edPassw0rd");
    }

    @AfterEach
    public void teardown() {
        // deleteCurrentUser verified working, cascade delete will remove seed data
        testUserService.deleteCurrentUser();
    }

    @Test
    public void are_preferred_categories_deleted() {
        testPrefCatDAO.add(testState.getCurrentUser().getId(),1);

        testDelService.deleteCurrentUserData();

        assertTrue(testPrefCatDAO.getByUserId(testState.getCurrentUser().getId()).isEmpty());
    }

    @Test
    public void are_check_ins_deleted() {
        testCheckInDAO.add(new CheckIn(testState.getCurrentUser().getId(),5.0f,5.0f,5.0f,"oops", LocalDateTime.now()));

        testDelService.deleteCurrentUserData();

        assertTrue(testCheckInDAO.getAllByUserId(testState.getCurrentUser().getId()).isEmpty());
    }

    @Test
    public void are_quiz_attempts_deleted() {
        final SqliteQuizAttemptQuestionDAO testQuizAttQDAO = new SqliteQuizAttemptQuestionDAO();
        final SqliteQuizAttemptAnswerDAO testQuizAttADAO = new SqliteQuizAttemptAnswerDAO();

        QuizAttempt testQuizAtt = new QuizAttempt(testState.getCurrentUser().getId(), "Test", Timestamp.valueOf(LocalDateTime.now()));
        testQuizAttDAO.add(testQuizAtt);

        QuizAttemptQuestion testQuizAttQ = new QuizAttemptQuestion(testQuizAtt.getId(), "Test", null);
        testQuizAttQDAO.add(testQuizAttQ);

        QuizAttemptAnswer testQuizAttA = new QuizAttemptAnswer(testQuizAttQ.getId(), "Test",null,true);
        testQuizAttADAO.add(testQuizAttA);

        testDelService.deleteCurrentUserData();

        assertTrue(testQuizAttDAO.getByUserId(testState.getCurrentUser().getId()).isEmpty());
        assertTrue(testQuizAttQDAO.get(testQuizAttQ.getId()).isEmpty());
        assertTrue(testQuizAttADAO.get(testQuizAttA.getId()).isEmpty());
    }
}
