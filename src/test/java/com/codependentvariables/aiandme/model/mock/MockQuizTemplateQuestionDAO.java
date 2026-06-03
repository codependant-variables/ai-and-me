package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import com.codependentvariables.aiandme.model.dao.IQuizTemplateQuestionDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock in-memory implementation of IQuizTemplateQuestionDAO for testing
 */
public class MockQuizTemplateQuestionDAO implements IQuizTemplateQuestionDAO {
    private final List<QuizTemplateQuestion> quizTemplateQuestions = new ArrayList<>();
    private static int autoIncrementedId = 1;

    @Override
    public void add(QuizTemplateQuestion quizTemplateQuestion) {
        quizTemplateQuestion.setId(autoIncrementedId++);
        quizTemplateQuestions.add(quizTemplateQuestion);
    }

    @Override
    public void update(QuizTemplateQuestion quizTemplateQuestion) {
        for (int i = 0; i < quizTemplateQuestions.size(); i++) {
            if (quizTemplateQuestions.get(i).getId() == quizTemplateQuestion.getId()) {
                quizTemplateQuestions.set(i, quizTemplateQuestion);
            }
        }
    }

    @Override
    public void delete(QuizTemplateQuestion quizTemplateQuestion) {
        quizTemplateQuestions.remove(quizTemplateQuestion);
    }

    @Override
    public QuizTemplateQuestion get(int id) {
        return quizTemplateQuestions.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<QuizTemplateQuestion> getByTemplateId(int templateId) {
        return quizTemplateQuestions.stream()
                .filter(c -> c.getQuizTemplateId() == templateId)
                .toList();
    }
}