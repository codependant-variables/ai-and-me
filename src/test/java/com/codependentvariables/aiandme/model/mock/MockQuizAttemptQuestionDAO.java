package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.QuizAttemptQuestion;
import com.codependentvariables.aiandme.model.dao.IQuizAttemptQuestionDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock database implementation for storing quiz attempt question data.
 */
public class MockQuizAttemptQuestionDAO implements IQuizAttemptQuestionDAO {

    private static final ArrayList<QuizAttemptQuestion> questions = new ArrayList<>();
    private static int autoIncrementId = 1;

    /**
     * Constructor seeds initial mock data.
     */
    public MockQuizAttemptQuestionDAO() {
        add(new QuizAttemptQuestion(1, "What is 2 + 2?", null));
        add(new QuizAttemptQuestion(1, "What is 5 * 3?", null));
    }

    /**
     * Adds a new QuizAttemptQuestion.
     */
    @Override
    public void add(QuizAttemptQuestion quizAttemptQuestion) {
        quizAttemptQuestion.setId(autoIncrementId);
        autoIncrementId++;
        questions.add(quizAttemptQuestion);
    }

    /**
     * Updates an existing QuizAttemptQuestion.
     */
    @Override
    public void update(QuizAttemptQuestion quizAttemptQuestion) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId() == quizAttemptQuestion.getId()) {
                questions.set(i, quizAttemptQuestion);
                break;
            }
        }
    }

    /**
     * Deletes a QuizAttemptQuestion.
     */
    @Override
    public void delete(QuizAttemptQuestion quizAttemptQuestion) {
        questions.removeIf(question -> question.getId() == quizAttemptQuestion.getId());
    }

    /**
     * Retrieves all questions for a given quiz attempt.
     */
    @Override
    public List<QuizAttemptQuestion> getByQuizAttemptId(int quizAttemptId) {
        List<QuizAttemptQuestion> results = new ArrayList<>();

        for (QuizAttemptQuestion question : questions) {
            if (question.getQuizAttemptId() == quizAttemptId) {
                results.add(question);
            }
        }

        return results;
    }
}