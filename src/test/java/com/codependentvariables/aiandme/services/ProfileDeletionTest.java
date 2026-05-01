package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ProfileDeletionTest {
    private final SqliteUserDAO testUserDAO = new SqliteUserDAO();
    private final UserService testService = new UserService(testUserDAO);
    private final AppState testState = AppState.getInstance();

    private int userId;

    @BeforeEach
    public void setup() {
        testUserDAO.beginTransaction();
        testService.signup("Test User", "test@user.net", "TestIkles1!");
        userId = testState.getCurrentUser().getId();
    }


    @AfterEach
    public void teardown() {
        testUserDAO.rollback();
    }

    @Test
    public void is_user_deleted() {
        testService.deleteCurrentUser();

        assertNull(testUserDAO.get(userId));
    }

    @Test
    public void is_checkin_deleted() {
        final ICheckInDAO testCheckinDAO = new SqliteCheckInDAO();

        CheckIn testCheckin = new CheckIn(userId, 5.0f, 5.0f, 5.0f, "Test Comment", LocalDateTime.now());
        testCheckinDAO.add(testCheckin);

        assertNotNull(testCheckinDAO.get(testCheckin.getId()));

        testService.deleteCurrentUser();

        assertTrue(testCheckinDAO.getAllByUserId(userId).isEmpty());
    }

    @Test
    public void is_preferred_category_deleted() {
        final SqlitePreferredCategoryDAO testPreferredCategoryDAO = new SqlitePreferredCategoryDAO();

        testPreferredCategoryDAO.add(userId, 1);

        assertFalse(testPreferredCategoryDAO.getByUserId(userId).isEmpty());

        testService.deleteCurrentUser();

        assertTrue(testPreferredCategoryDAO.getByUserId(userId).isEmpty());
    }

    @Test
    public void is_quiz_attempt_deleted() {
        final SqliteQuizAttemptDAO testQuizAttemptDAO = new SqliteQuizAttemptDAO();

        QuizAttempt testQuizAttempt = new QuizAttempt(userId, "Test Quiz Attempt", Timestamp.valueOf(LocalDateTime.now()));
        testQuizAttemptDAO.add(testQuizAttempt);

        assertNotNull(testQuizAttemptDAO.get(testQuizAttempt.getId()));

        testService.deleteCurrentUser();

        assertTrue(testQuizAttemptDAO.getByUserId(userId).isEmpty());
    }

    @Test
    public void is_quiz_attempt_question_deleted() {
        final SqliteQuizAttemptDAO testQuizAttemptDAO = new SqliteQuizAttemptDAO();
        final SqliteQuizAttemptQuestionDAO testQuizAttemptQuestionDAO = new SqliteQuizAttemptQuestionDAO();

        QuizAttempt testQuizAttempt = new QuizAttempt(userId, "Test Quiz Attempt", Timestamp.valueOf(LocalDateTime.now()));
        testQuizAttemptDAO.add(testQuizAttempt);

        QuizAttemptQuestion testQuizAttemptQuestion = new QuizAttemptQuestion(testQuizAttempt.getId(), "Test Question", null);
        testQuizAttemptQuestionDAO.add(testQuizAttemptQuestion);

        assertNotNull(testQuizAttemptDAO.get(testQuizAttempt.getId()));
        assertNotNull(testQuizAttemptQuestionDAO.get(testQuizAttemptQuestion.getId()));

        testService.deleteCurrentUser();

        assertTrue(testQuizAttemptDAO.getByUserId(userId).isEmpty());
        assertTrue(testQuizAttemptQuestionDAO.getByQuizAttemptId(testQuizAttempt.getId()).isEmpty());
    }

    @Test
    public void is_quiz_attempt_answer_deleted() {
        final SqliteQuizAttemptDAO testQuizAttemptDAO = new SqliteQuizAttemptDAO();
        final SqliteQuizAttemptQuestionDAO testQuizAttemptQuestionDAO = new SqliteQuizAttemptQuestionDAO();
        final SqliteQuizAttemptAnswerDAO testQuizAttemptAnswerDAO = new SqliteQuizAttemptAnswerDAO();

        QuizAttempt testQuizAttempt = new QuizAttempt(userId, "Test Quiz Attempt", Timestamp.valueOf(LocalDateTime.now()));
        testQuizAttemptDAO.add(testQuizAttempt);

        QuizAttemptQuestion testQuizAttemptQuestion = new QuizAttemptQuestion(testQuizAttempt.getId(), "Test Question", null);
        testQuizAttemptQuestionDAO.add(testQuizAttemptQuestion);

        QuizAttemptAnswer testQuizAttemptAnswer = new QuizAttemptAnswer(testQuizAttemptQuestion.getId(), "Test Question", null, true);
        testQuizAttemptAnswerDAO.add(testQuizAttemptAnswer);

        assertNotNull(testQuizAttemptDAO.get(testQuizAttempt.getId()));
        assertNotNull(testQuizAttemptQuestionDAO.get(testQuizAttemptQuestion.getId()));
        assertNotNull(testQuizAttemptAnswerDAO.get(testQuizAttempt.getId()));

        testService.deleteCurrentUser();

        assertTrue(testQuizAttemptDAO.getByUserId(userId).isEmpty());
        assertTrue(testQuizAttemptQuestionDAO.getByQuizAttemptId(testQuizAttempt.getId()).isEmpty());
        assertTrue(testQuizAttemptAnswerDAO.getByQuizAttemptQuestionId(testQuizAttemptQuestion.getId()).isEmpty());
    }
}