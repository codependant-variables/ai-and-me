package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.model.QuizTemplateQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock database implementation for storing quiz template question data.
 */
public class MockQuizTemplateQuestionDAO implements IQuizTemplateQuestionDAO {
    private final ArrayList<QuizTemplateQuestion> questions = new ArrayList<>();
    private int autoIncrementId = 1;

    @Override
    public void addQuestion(QuizTemplateQuestion question) {
        question.setId(autoIncrementId++);
        questions.add(question);
    }

    @Override
    public void updateQuestion(QuizTemplateQuestion question) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId() == question.getId()) {
                questions.set(i, question);
                return;
            }
        }
    }

    @Override
    public void deleteQuestion(QuizTemplateQuestion question) {
        questions.remove(question);
    }

    @Override
    public QuizTemplateQuestion get(int id) {
        for (QuizTemplateQuestion question : questions) {
            if (question.getId() == id) return question;
        }
        return null;
    }

    @Override
    public List<QuizTemplateQuestion> getQuestionsByTemplate(int quizTemplateId) {
        List<QuizTemplateQuestion> result = new ArrayList<>();
        for (QuizTemplateQuestion question : questions) {
            if (question.getQuizTemplateId() == quizTemplateId) {
                result.add(question);
            }
        }
        return result;
    }
}

