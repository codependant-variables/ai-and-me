package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MockQuizAttemptDAO class.
 * Verifies correct behaviour of CRUD operations and query methods.
 */
public class QuizAttemptDAOTest {

    private final IQuizAttemptDAO quizAttemptDAO = new MockQuizAttemptDAO();

    /**
     * Tests retrieving a QuizAttempt by its ID.
     * Ensures the seeded data is returned correctly.
     */
    @Test
    public void get() {
        QuizAttempt quizAttempt = quizAttemptDAO.get(1);

        assertNotNull(quizAttempt);
        assertEquals("Emma's Quiz Attempt", quizAttempt.getName());
        assertEquals(1, quizAttempt.getUserId());
        assertEquals(Timestamp.valueOf("2026-04-17 00:00:00"), quizAttempt.getCompletedAt());
    }

    /**
     * Tests retrieving all QuizAttempts for a specific user.
     */
    @Test
    public void getByUserId() {
        List<QuizAttempt> attempts = quizAttemptDAO.getByUserId(1);

        assertNotNull(attempts);
        assertEquals(1, attempts.size());
        assertEquals("Emma's Quiz Attempt", attempts.getFirst().getName());
    }

    /**
     * Tests adding a new QuizAttempt to the DAO.
     * Verifies that the object is assigned an ID and can be retrieved.
     */
    @Test
    public void addQuizAttempt() {
        QuizAttempt quizAttempt = new QuizAttempt(
                2,
                "Bob's Quiz Attempt",
                Timestamp.valueOf("2026-04-18 10:30:00")
        );

        quizAttemptDAO.add(quizAttempt);

        QuizAttempt retrieved = quizAttemptDAO.get(quizAttempt.getId());

        assertNotNull(retrieved);
        assertEquals("Bob's Quiz Attempt", retrieved.getName());
    }

    /**
     * Tests retrieving the latest QuizAttempt for a user.
     * Ensures the most recent timestamp is selected.
     */
    @Test
    public void getLatestByUserId() {
        quizAttemptDAO.add(new QuizAttempt(
                1,
                "Older Attempt",
                Timestamp.valueOf("2026-04-16 09:00:00")
        ));

        quizAttemptDAO.add(new QuizAttempt(
                1,
                "Newest Attempt",
                Timestamp.valueOf("2026-04-18 12:00:00")
        ));

        // Cast required because method is not in interface
        QuizAttempt latest = ((MockQuizAttemptDAO) quizAttemptDAO).getLatestByUserId(1);

        assertNotNull(latest);
        assertEquals("Newest Attempt", latest.getName());
    }
}