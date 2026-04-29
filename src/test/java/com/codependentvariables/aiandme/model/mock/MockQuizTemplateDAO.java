package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.IQuizTemplateDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock database implementation for storing quiz template data.
 */
public class MockQuizTemplateDAO implements IQuizTemplateDAO {
    private final ArrayList<QuizTemplate> templates = new ArrayList<>();
    private int autoIncrementId = 1;

    @Override
    public void add(QuizTemplate quizTemplate) {
        quizTemplate.setId(autoIncrementId);
        autoIncrementId++;
        templates.add(quizTemplate);
    }

    @Override
    public void update(QuizTemplate quizTemplate) {
        for (int i = 0; i < templates.size(); i++) {
            if (templates.get(i).getId() == quizTemplate.getId()) {
                templates.set(i, quizTemplate);
                break;
            }
        }
    }

    @Override
    public void delete(QuizTemplate quizTemplate) {
        templates.remove(quizTemplate);
    }

    @Override
    public QuizTemplate get(int id) {
        for (QuizTemplate template : templates) {
            if (template.getId() == id) {
                return template;
            }
        }
        return null;
    }

    @Override
    public List<QuizTemplate> getAll() {
        return new ArrayList<>(templates);
    }

    @Override
    public List<QuizTemplate> getByCategoryId(int categoryId) {
        List<QuizTemplate> result = new ArrayList<>();
        for (QuizTemplate template : templates) {
            if (template.getCategoryId() == categoryId) {
                result.add(template);
            }
        }
        return result;
    }

    @Override
    public List<QuizTemplate> getByUserId(int userId) {
        List<QuizTemplate> result = new ArrayList<>();
        for (QuizTemplate template : templates) {
            if (template.getUserId() == userId) {
                result.add(template);
            }
        }
        return result;
    }
}

