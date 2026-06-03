package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.QuizTemplateAnswer;
import com.codependentvariables.aiandme.model.dao.IQuizTemplateAnswerDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock in-memory implementation of IQuizTemplateAnswerDAO for testing
 */
public class MockQuizTemplateAnswerDAO implements IQuizTemplateAnswerDAO {
    private final List<QuizTemplateAnswer> quizTemplateAnswers = new ArrayList<>();
    private static int autoIncrementedId = 1;

    @Override
    public void add(QuizTemplateAnswer quizTemplateAnswer) {
        quizTemplateAnswer.setId(autoIncrementedId++);
        quizTemplateAnswers.add(quizTemplateAnswer);
    }

    @Override
    public void update(QuizTemplateAnswer quizTemplateAnswer) {
        for (int i = 0; i < quizTemplateAnswers.size(); i++) {
            if (quizTemplateAnswers.get(i).getId() == quizTemplateAnswer.getId()) {
                quizTemplateAnswers.set(i, quizTemplateAnswer);
            }
        }
    }

    @Override
    public void delete(QuizTemplateAnswer quizTemplateAnswer) {
        quizTemplateAnswers.remove(quizTemplateAnswer);
    }

    @Override
    public QuizTemplateAnswer get(int id) {
        return quizTemplateAnswers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<QuizTemplateAnswer> getByQuestionId(int questionId) {
        return quizTemplateAnswers.stream()
                .filter(c -> c.getQuizTemplateQuestionId() == questionId)
                .toList();
    }
}