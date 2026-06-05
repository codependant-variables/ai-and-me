package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;
import com.codependentvariables.aiandme.model.mock.*;
import com.codependentvariables.aiandme.modules.state.AppState;
import com.codependentvariables.aiandme.services.user.LoginResult;
import com.codependentvariables.aiandme.services.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UserServiceTest extends JavaFXTest {
    public static AppState appState;
    public static CheckInService checkInService;
    public static QuizAttemptService quizAttemptService;
    public static QuizTemplateService quizTemplateService;
    public static UserService userService;

    private static final ICategoryDAO categoryDAO = new MockCategoryDAO();
    private static final ICheckInDAO checkInDAO = new MockCheckInDAO();
    private static final IQuizAttemptDAO quizAttemptDAO = new MockQuizAttemptDAO();
    private static final IQuizAttemptQuestionDAO quizAttemptQuestionDAO = new MockQuizAttemptQuestionDAO();
    private static final IQuizAttemptAnswerDAO quizAttemptAnswerDAO = new MockQuizAttemptAnswerDAO();
    private static final IQuizTemplateDAO quizTemplateDAO = new MockQuizTemplateDAO();
    private static final IQuizTemplateQuestionDAO quizTemplateQuestionDAO = new MockQuizTemplateQuestionDAO();
    private static final IQuizTemplateAnswerDAO quizTemplateAnswerDAO = new MockQuizTemplateAnswerDAO();
    private static final IUserDAO userDAO = new MockUserDAO();
    private static final IUserPreferredCategoryDAO userPreferredCategoryDAO = new MockUserPreferredCategoryDAO();

    public final String password = "password1";
    public final String hash = "Zl3QG3XY5/Gsus8Ec4WTi6jMcM7EkrCGCqBMgwwYUzg=";
    public final String salt = "sKH9XkLaT2i1XR687zjlHQ==";
    public final User user = new User("", "amy.adams@mydomain.gov", hash, salt);

    @BeforeAll
    public static void setup() {
        appState = AppState.getInstance();
        checkInService = CheckInService.createForTest(checkInDAO);
        quizAttemptService = QuizAttemptService.createForTest(quizAttemptDAO, quizAttemptQuestionDAO, quizAttemptAnswerDAO, categoryDAO);
        quizTemplateService = QuizTemplateService.createForTest(quizTemplateDAO, quizTemplateQuestionDAO, quizTemplateAnswerDAO);
        userService = UserService.createForTest(checkInDAO, quizAttemptDAO, quizAttemptQuestionDAO, quizAttemptAnswerDAO, quizTemplateDAO, quizTemplateQuestionDAO, quizTemplateAnswerDAO, userDAO, userPreferredCategoryDAO);
    }

    @BeforeEach
    public void setupEach() {
        userService.logout();
    }

    @Test
    public void invalid_login_attempt() {
        LoginResult loginResult = userService.attemptLogin(user, "not the password");
        assertEquals(LoginResult.INVALID, loginResult);
    }

    @Test
    public void valid_login_attempt() {
        LoginResult loginResult = userService.attemptLogin(user, password);
        assertEquals(LoginResult.VALID, loginResult);
    }

    @Test
    public void delete_current_user() {
        appState.setCurrentUser(user);
        userService.deleteCurrentUser();
        assertNull(appState.getCurrentUser());
    }

    @Test
    public void delete_current_user_without_current_user() {
        appState.setCurrentUser(null);
        userService.deleteCurrentUser();
        assertNull(appState.getCurrentUser());
    }

    @Test
    public void delete_current_user_data() {
        userService.attemptLogin(user, password);
        User user = appState.getCurrentUser();
        int userId = user.getId();

        int categoryId = categoryDAO.getAll().getFirst().getId();
        userPreferredCategoryDAO.add(new UserPreferredCategory(userId, categoryId));

        checkInService.submitCheckIn(new CheckIn(userId, 5.0f, 5.0f, 5.0f, null, LocalDateTime.now()));

        QuizTemplate quizTemplate = quizTemplateService.createTemplate("New template", categoryId, userId);
        QuizTemplateQuestion quizTemplateQuestion = new QuizTemplateQuestion(quizTemplate.getId(), "New question");
        QuizTemplateAnswer quizTemplateAnswer = new QuizTemplateAnswer(0, "New answer", true);
        quizTemplateQuestion.addAnswer(quizTemplateAnswer);
        quizTemplateService.saveQuestions(List.of(quizTemplateQuestion), List.of());

        quizAttemptService.saveAttempt(quizTemplate, userId, List.of(quizTemplateAnswer));

        userService.deleteCurrentUserData();

        assertTrue(userPreferredCategoryDAO.getByUserId(userId).isEmpty());
        assertTrue(checkInService.getAllByUserId(userId).isEmpty());
        assertTrue(quizAttemptService.getByUserId(userId).isEmpty());
    }
}