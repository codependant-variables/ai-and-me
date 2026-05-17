package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.model.QuizTemplateAnswer;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import com.codependentvariables.aiandme.model.dao.IQuizTemplateAnswerDAO;
import com.codependentvariables.aiandme.model.dao.IQuizTemplateQuestionDAO;
import com.codependentvariables.aiandme.model.mock.MockQuizTemplateDAO;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for QuizTemplateService.createTemplate() focusing on user_id assignment,
 * covering the DAO-level null-substitution: getUserId()==0 implies SQL NULL, otherwise shows the user id.
 */
class QuizTemplateServiceTest {

    private MockQuizTemplateDAO templateDAO;
    private QuizTemplateService service;

    @BeforeEach
    void setUp() {
        templateDAO = new MockQuizTemplateDAO();
        service = new QuizTemplateService(templateDAO, stubQuestionDAO(), stubAnswerDAO());
    }

    /**
     * When a logged in user creates a template, their user Id must be stored on
     * the returned template and retrievable from the DAO.
     */
    @Test
    void storesUserId() {
        int loggedInUserId = 7;

        QuizTemplate result = service.createTemplate("My Quiz", 1, loggedInUserId);

        assertEquals(loggedInUserId, result.getUserId(),
                "Returned template should carry the logged-in user's id");

        QuizTemplate fromDAO = templateDAO.get(result.getId());
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
    void invokeNullId() {
        int guestUserId = 0; // Placeholder variable for SQL NULL in SqliteQuizTemplateDAO

        QuizTemplate result = service.createTemplate("Guest Quiz", 1, guestUserId);

        assertEquals(0, result.getUserId(),
                "Guest template should have userId=0 so the DAO writes SQL NULL");

        QuizTemplate fromDAO = templateDAO.get(result.getId());
        assertNotNull(fromDAO);
        assertEquals(0, fromDAO.getUserId(),
                "Guest template in DAO should also have userId=0");
    }

    /**
     * A puzzle template created by a logged-in user must store both the puzzle
     * flag and the correct userId.
     */
    @Test
    void invokeUserPuzzle() {
        int userId = 42;

        QuizTemplate result = service.createTemplate("Pattern Puzzle", 2, userId, true);

        assertTrue(result.isPuzzle(), "Template should be flagged as a puzzle");
        assertEquals(userId, result.getUserId(),
                "Puzzle template should retain the creating user's id");
    }

    /**
     * A puzzle template created by a guest must preserve userId=0 and
     * the puzzle flag.
     */
    @Test
    void preserveUserPuzzle2() {
        QuizTemplate result = service.createTemplate("Guest Puzzle", 2, 0, true);

        assertTrue(result.isPuzzle(), "Template should be flagged as a puzzle");
        assertEquals(0, result.getUserId(),
                "Guest puzzle template should have userId=0 so the DAO writes SQL NULL");
    }

    /**
     * getByUserId should only return templates owned by that user,
     * not templates with userId=0 (guest) or a different user.
     */
    @Test
    void returnMatchUserId() {
        service.createTemplate("User 5 Quiz", 1, 5);
        service.createTemplate("User 9 Quiz", 1, 9);
        service.createTemplate("Guest Quiz",  1, 0);

        List<QuizTemplate> user5Templates = service.getTemplatesByUser(5);

        assertEquals(1, user5Templates.size());
        assertEquals("User 5 Quiz", user5Templates.get(0).getName());
        assertEquals(5, user5Templates.get(0).getUserId());
    }

    /** non-operational, createTemplate() never touches questions. */
    private static IQuizTemplateQuestionDAO stubQuestionDAO() {
        return new IQuizTemplateQuestionDAO() {
            public void addQuestion(QuizTemplateQuestion q) {}
            public void updateQuestion(QuizTemplateQuestion q) {}
            public void deleteQuestion(QuizTemplateQuestion q) {}
            public QuizTemplateQuestion get(int id) { return null; }
            public List<QuizTemplateQuestion> getQuestionsByTemplate(int tid) { return Collections.emptyList(); }
        };
    }

    /** non-operational, createTemplate() never touches answers. */
    private static IQuizTemplateAnswerDAO stubAnswerDAO() {
        return new IQuizTemplateAnswerDAO() {
            public void addAnswer(QuizTemplateAnswer a) {}
            public void updateAnswer(QuizTemplateAnswer a) {}
            public void deleteAnswer(QuizTemplateAnswer a) {}
            public QuizTemplateAnswer get(int id) { return null; }
            public List<QuizTemplateAnswer> getAnswersByQuestion(int qid) { return Collections.emptyList(); }
        };
    }
}

