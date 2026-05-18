package com.codependentvariables.aiandme.model;

import com.codependentvariables.aiandme.model.dao.IQuizTemplateDAO;
import com.codependentvariables.aiandme.model.mock.MockQuizTemplateDAO;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTemplateDAOTest {
    private IQuizTemplateDAO quizTemplateDAO;

    // Research: https://docs.junit.org/6.0.3/writing-tests/annotations.html
    @BeforeEach
    void setUp() {
        quizTemplateDAO = new MockQuizTemplateDAO();
    }

    @Test
    void addTemplate() {
        QuizTemplate quiz = new QuizTemplate("Math Quiz", 1, 1, "draft");
        quizTemplateDAO.add(quiz);

        QuizTemplate savedQuiz = quizTemplateDAO.get(quiz.getId());
        assertTrue(quiz.getId() > 0);
        assertNotNull(savedQuiz);
        assertEquals("Math Quiz", savedQuiz.getName());
    }

    @Test
    void updateTemplate() {
        QuizTemplate quiz = new QuizTemplate("Old Name", 1, 1, "draft");

        quizTemplateDAO.add(quiz);

        quiz.setName("New Name");
        quiz.setStatus("published");
        quizTemplateDAO.update(quiz);

        QuizTemplate updatedQuiz = quizTemplateDAO.get(quiz.getId());

        assertEquals("New Name", updatedQuiz.getName());
        assertEquals("published", updatedQuiz.getStatus());
    }

    @Test
    void deleteTemplate() {
        QuizTemplate quiz = new QuizTemplate("Delete Test", 1, 1, "draft");
        quizTemplateDAO.add(quiz);
        quizTemplateDAO.delete(quiz);

        assertNull(quizTemplateDAO.get(quiz.getId()));
    }

    @Test
    void getTemplateByCategory() {
        quizTemplateDAO.add(new QuizTemplate("Math Quiz", 1, 1, "draft"));
        quizTemplateDAO.add(new QuizTemplate("Reading Quiz", 2, 1, "draft"));

        List<QuizTemplate> mathQuizzes = quizTemplateDAO.getByCategoryId(1);

        assertEquals(1, mathQuizzes.size());
        assertEquals("Math Quiz", mathQuizzes.get(0).getName()); // Gets first quiz returned
    }

    @Test
    void getTemplateByUser() {
        quizTemplateDAO.add(new QuizTemplate("My Quiz", 1, 1, "draft"));
        quizTemplateDAO.add(new QuizTemplate("Other Quiz",1, 2, "draft"));

        List<QuizTemplate> userQuizzes = quizTemplateDAO.getByUserId(1);

        assertEquals(1, userQuizzes.size());
        assertEquals("My Quiz", userQuizzes.get(0).getName()); // gets first quiz returned
    }

    /** A template created by a logged-in user must retain that user's id. */
    @Test
    void isUserIdLogged() {
        int loggedInUserId = 42;
        QuizTemplate quiz = new QuizTemplate("User Quiz", 1, loggedInUserId, "draft");
        quizTemplateDAO.add(quiz);

        QuizTemplate saved = quizTemplateDAO.get(quiz.getId());
        assertNotNull(saved);
        assertEquals(loggedInUserId, saved.getUserId(),
                "Template created by a logged-in user should store their user id");
    }

    /**
     * A template created without a logged-in user uses userId=0 as the guest default.
     * The DAO layer says 0 meaning SQL NULL; this test verifies the model/mock
     * cycle preserving 0 so that downstream null replacement logic is started.
     */
    @Test
    void isGuestUserZero() {
        int guestUserId = 0; // placeholder for guest user
        QuizTemplate quiz = new QuizTemplate("Guest Quiz", 1, guestUserId, "draft");
        quizTemplateDAO.add(quiz);

        QuizTemplate saved = quizTemplateDAO.get(quiz.getId());
        assertNotNull(saved);
        assertEquals(0, saved.getUserId(),
                "Template created by a guest should have userId=0 (stored as NULL in SQL)");
    }
}