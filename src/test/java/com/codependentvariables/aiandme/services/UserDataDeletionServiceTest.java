package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.mock.*;
import com.codependentvariables.aiandme.modules.state.AppState;
import org.junit.jupiter.api.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class UserDataDeletionServiceTest extends JavaFXTest {
    private static AppState appState;
    private static CheckInService checkInService;
    private static final MockCheckInDAO mockCheckInDAO = new MockCheckInDAO();
    private static final MockPreferredCategoryDAO testPrefCatDAO = new MockPreferredCategoryDAO();
    private static final MockQuizAttemptDAO testQuizAttDAO = new MockQuizAttemptDAO();
    private static final MockQuizAttemptQuestionDAO testQuizAttQDAO = new MockQuizAttemptQuestionDAO();
    private static final MockQuizAttemptAnswerDAO testQuizAttADAO = new MockQuizAttemptAnswerDAO();
    private static UserService userService;
    private static UserDataDeletionService testDelService;

    @BeforeAll
    public static void setup() {
        appState = AppState.getInstance();
        checkInService = CheckInService.createForTest(mockCheckInDAO);
        userService = UserService.createForTest(new MockUserDAO());
    }

    @BeforeEach
    public void setupEach() {
        testDelService = UserDataDeletionService.createForTest(testPrefCatDAO, mockCheckInDAO, testQuizAttDAO);
        userService.signup("Test User", "test@user.com", "#edPassw0rd");
    }

    @AfterEach
    public void teardown() {
        // deleteCurrentUser verified working, cascade delete will remove seed data
        userService.deleteCurrentUser();
    }

    @Test
    public void are_preferred_categories_deleted() {
        testPrefCatDAO.add(appState.getCurrentUser().getId(),1);

        testDelService.deleteCurrentUserData();

        assertTrue(testPrefCatDAO.getByUserId(appState.getCurrentUser().getId()).isEmpty());
    }

    @Test
    public void are_check_ins_deleted() {
        checkInService.submitCheckIn(new CheckIn(appState.getCurrentUser().getId(), 5.0f, 5.0f, 5.0f, "oops", LocalDateTime.now()));

        testDelService.deleteCurrentUserData();

        assertTrue(checkInService.getAllByUserId(appState.getCurrentUser().getId()).isEmpty());
    }

    @Test
    public void are_quiz_attempts_deleted() {
        QuizAttempt testQuizAtt = new QuizAttempt(appState.getCurrentUser().getId(), "Test", Timestamp.valueOf(LocalDateTime.now()),5, "Maths", false);
        testQuizAttDAO.add(testQuizAtt);

        QuizAttemptQuestion testQuizAttQ = new QuizAttemptQuestion(testQuizAtt.getId(), "Test", null);
        testQuizAttQDAO.add(testQuizAttQ);

        QuizAttemptAnswer testQuizAttA = new QuizAttemptAnswer(testQuizAttQ.getId(), "Test",null,true);
        testQuizAttADAO.add(testQuizAttA);

        testDelService.deleteCurrentUserData();

        assertTrue(testQuizAttDAO.getByUserId(appState.getCurrentUser().getId()).isEmpty());
        // This needs to be implemented via connecting mock DAOs
        // assertNull(testQuizAttQDAO.get(testQuizAttQ.getId()));
        // assertNull(testQuizAttADAO.get(testQuizAttA.getId()));
    }
}
