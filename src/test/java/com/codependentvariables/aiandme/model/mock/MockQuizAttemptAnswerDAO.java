package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.QuizAttemptAnswer;
import com.codependentvariables.aiandme.model.dao.IQuizAttemptAnswerDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock database implementation for storing quiz attempt answer data.
 */
public class MockQuizAttemptAnswerDAO implements IQuizAttemptAnswerDAO {

    private static final ArrayList<QuizAttemptAnswer> answers = new ArrayList<>();
    private static int autoIncrementId = 1;

    /**
     * Constructor seeds initial mock data.
     */
    public MockQuizAttemptAnswerDAO() {
        add(new QuizAttemptAnswer(1, "4", null, true));
        add(new QuizAttemptAnswer(1, "5", null, false));

        add(new QuizAttemptAnswer(2, "15", null, true));
        add(new QuizAttemptAnswer(2, "10", null, false));
    }

    /**
     * Adds a new QuizAttemptAnswer.
     */
    @Override
    public void add(QuizAttemptAnswer quizAttemptAnswer) {
        quizAttemptAnswer.setId(autoIncrementId);
        autoIncrementId++;
        answers.add(quizAttemptAnswer);
    }

    /**
     * Deletes a QuizAttemptAnswer.
     */
    @Override
    public void delete(QuizAttemptAnswer quizAttemptAnswer) {
        answers.removeIf(answer -> answer.getId() == quizAttemptAnswer.getId());
    }

    @Override
    public QuizAttemptAnswer get(int id) {
        return answers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all answers for a given quiz attempt question.
     */
    @Override
    public List<QuizAttemptAnswer> getByQuestionId(int quizAttemptQuestionId) {
        List<QuizAttemptAnswer> results = new ArrayList<>();

        for (QuizAttemptAnswer answer : answers) {
            if (answer.getQuizAttemptQuestionId() == quizAttemptQuestionId) {
                results.add(answer);
            }
        }

        return results;
    }
}