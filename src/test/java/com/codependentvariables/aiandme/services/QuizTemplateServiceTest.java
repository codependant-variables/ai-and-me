package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.model.dao.IQuizTemplateAnswerDAO;
import com.codependentvariables.aiandme.model.dao.IQuizTemplateDAO;
import com.codependentvariables.aiandme.model.dao.IQuizTemplateQuestionDAO;
import com.codependentvariables.aiandme.model.mock.MockQuizTemplateDAO;
import com.codependentvariables.aiandme.model.mock.MockQuizTemplateQuestionDAO;
import com.codependentvariables.aiandme.model.mock.MockQuizTemplateAnswerDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for QuizTemplateService.createTemplate() focusing on user_id assignment,
 * covering the DAO-level null-substitution: getUserId()==0 implies SQL NULL, otherwise shows the user id.
 */
public class QuizTemplateServiceTest {
    private static QuizTemplateService quizTemplateService;

    private static final IQuizTemplateDAO quizTemplateDAO = new MockQuizTemplateDAO();
    private static final IQuizTemplateQuestionDAO quizTemplateQuestionDAO = new MockQuizTemplateQuestionDAO();
    private static final IQuizTemplateAnswerDAO quizTemplateAnswerDAO = new MockQuizTemplateAnswerDAO();

    @BeforeAll
    public static void setup() {
        quizTemplateService = QuizTemplateService.createForTest(quizTemplateDAO, quizTemplateQuestionDAO, quizTemplateAnswerDAO);
    }

    /**
     * When a logged-in user creates a template, their user Id must be stored on
     * the returned template and retrievable from the DAO.
     */
    @Test
    public void storesUserId() {
        int loggedInUserId = 7;

        QuizTemplate result = quizTemplateService.createTemplate("My Quiz", 1, loggedInUserId);

        assertEquals(loggedInUserId, result.getUserId(),
                "Returned template should carry the logged-in user's id");

        QuizTemplate fromDAO = quizTemplateDAO.get(result.getId());
        assertNotNull(fromDAO);
        assertEquals(loggedInUserId, fromDAO.getUserId(),
                "Template persisted in DAO should carry the logged-in user's id");
    }

    /**
     * When no user is logged in (i.e. userId = 0), the template must preserve userId=0
     * so that SqliteQuizTemplateDAO can translate it to SQL NULL via the
     * null replacement (i.e. if getUserId() == 0 then setNull(...).
     */
    @Test
    public void invokeNullId() {
        int guestUserId = 0; // Placeholder variable for SQL NULL in SqliteQuizTemplateDAO

        QuizTemplate result = quizTemplateService.createTemplate("Guest Quiz", 1, guestUserId);

        assertEquals(0, result.getUserId(),
                "Guest template should have userId=0 so the DAO writes SQL NULL");

        QuizTemplate fromDAO = quizTemplateDAO.get(result.getId());
        assertNotNull(fromDAO);
        assertEquals(0, fromDAO.getUserId(),
                "Guest template in DAO should also have userId=0");
    }

    /**
     * A puzzle template created by a logged-in user must store both the puzzle
     * flag and the correct userId.
     */
    @Test
    public void invokeUserPuzzle() {
        int userId = 42;

        QuizTemplate result = quizTemplateService.createTemplate("Pattern Puzzle", 2, userId, true);

        assertTrue(result.isPuzzle(), "Template should be flagged as a puzzle");
        assertEquals(userId, result.getUserId(),
                "Puzzle template should retain the creating user's id");
    }

    /**
     * A puzzle template created by a guest must preserve userId=0 and
     * the puzzle flag.
     */
    @Test
    public void preserveUserPuzzle2() {
        QuizTemplate result = quizTemplateService.createTemplate("Guest Puzzle", 2, 0, true);

        assertTrue(result.isPuzzle(), "Template should be flagged as a puzzle");
        assertEquals(0, result.getUserId(),
                "Guest puzzle template should have userId=0 so the DAO writes SQL NULL");
    }

    /**
     * getByUserId should only return templates owned by that user,
     * not templates with userId=0 (guest) or a different user.
     */
    @Test
    public void returnMatchUserId() {
        quizTemplateService.createTemplate("User 5 Quiz", 1, 5);
        quizTemplateService.createTemplate("User 9 Quiz", 1, 9);
        quizTemplateService.createTemplate("Guest Quiz",  1, 0);

        List<QuizTemplate> user5Templates = quizTemplateService.getByUserId(5);

        assertEquals(1, user5Templates.size());
        assertEquals("User 5 Quiz", user5Templates.getFirst().getName());
        assertEquals(5, user5Templates.getFirst().getUserId());
    }
}

