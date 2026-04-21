package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.model.QuizTemplateAnswer;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock database implementation for storing quiz template answer data.
 */
public class MockQuizTemplateAnswerDAO implements IQuizTemplateAnswerDAO {
    private final ArrayList<QuizTemplateAnswer> answers = new ArrayList<>();
    private int autoIncrementId = 1;

    @Override
    public void addAnswer(QuizTemplateAnswer answer) {
        answer.setId(autoIncrementId++);
        answers.add(answer);
    }

    @Override
    public void updateAnswer(QuizTemplateAnswer answer) {
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).getId() == answer.getId()) {
                answers.set(i, answer);
                return;
            }
        }
    }

    @Override
    public void deleteAnswer(QuizTemplateAnswer answer) {
        answers.remove(answer);
    }

    @Override
    public QuizTemplateAnswer get(int id) {
        for (QuizTemplateAnswer answer : answers) {
            if (answer.getId() == id) return answer;
        }
        return null;
    }

    @Override
    public List<QuizTemplateAnswer> getAnswersByQuestion(int questionId) {
        List<QuizTemplateAnswer> result = new ArrayList<>();
        for (QuizTemplateAnswer answer : answers) {
            if (answer.getQuizTemplateQuestionId() == questionId) {
                result.add(answer);
            }
        }
        return result;
    }
}

