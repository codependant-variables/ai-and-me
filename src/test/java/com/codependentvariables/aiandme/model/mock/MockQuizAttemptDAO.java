package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.IQuizAttemptDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Mock database implementation for storing quiz attempt data.
 * Uses in-memory storage instead of a real database.
 */
public class MockQuizAttemptDAO implements IQuizAttemptDAO {

    private static final ArrayList<QuizAttempt> quizAttempts = new ArrayList<>();
    private static int autoIncrementId = 1;

    /**
     * Constructor seeds initial mock data.
     */
    public MockQuizAttemptDAO() {
        add(new QuizAttempt(1, "Emma's Quiz Attempt", Timestamp.valueOf("2026-04-17 00:00:00"), 5, "Maths", false));
    }

    /**
     * Adds a new QuizAttempt to the mock database.
     */
    @Override
    public void add(QuizAttempt quizAttempt) {
        quizAttempt.setId(autoIncrementId);
        autoIncrementId++;
        quizAttempts.add(quizAttempt);
    }

    /**
     * Updates an existing QuizAttempt.
     */
    @Override
    public void update(QuizAttempt quizAttempt) {
        for (int i = 0; i < quizAttempts.size(); i++) {
            if (quizAttempts.get(i).getId() == quizAttempt.getId()) {
                quizAttempts.set(i, quizAttempt);
                break;
            }
        }
    }

    /**
     * Deletes a QuizAttempt.
     */
    @Override
    public void delete(QuizAttempt quizAttempt) {
        quizAttempts.remove(quizAttempt);
    }

    @Override
    public void deleteByUserId(int userId) {
        quizAttempts.removeIf(x -> x.getUserId() == userId);
    }

    /**
     * Retrieves a QuizAttempt by ID.
     */
    @Override
    public QuizAttempt get(int id) {
        for (QuizAttempt attempt : quizAttempts) {
            if (attempt.getId() == id) {
                return attempt;
            }
        }
        return null;
    }

    /**
     * Retrieves all QuizAttempts.
     */
    @Override
    public List<QuizAttempt> getAll() {
        return new ArrayList<>(quizAttempts);
    }

    /**
     * Retrieves all QuizAttempts for a specific user.
     */
    @Override
    public List<QuizAttempt> getByUserId(int userId) {
        List<QuizAttempt> results = new ArrayList<>();

        for (QuizAttempt attempt : quizAttempts) {
            if (attempt.getUserId() == userId) {
                results.add(attempt);
            }
        }

        return results;
    }

    /**
     * Retrieves the most recent QuizAttempt for a user.
     */
    public QuizAttempt getLatestByUserId(int userId) {
        QuizAttempt latest = null;

        for (QuizAttempt attempt : quizAttempts) {
            if (attempt.getUserId() == userId) {
                if (latest == null || attempt.getCompletedAt().after(latest.getCompletedAt())) {
                    latest = attempt;
                }
            }
        }

        return latest;
    }

    /**
     * Retrieves all QuizAttempts for a user, ordered chronologically (oldest first).
     */
    @Override
    public List<QuizAttempt> getByUserIdOrdered(int userId) {
        List<QuizAttempt> results = new ArrayList<>();

        for (QuizAttempt attempt : quizAttempts) {
            if (attempt.getUserId() == userId) {
                results.add(attempt);
            }
        }

        results.sort(Comparator.comparing(QuizAttempt::getCompletedAt));
        return results;
    }
}