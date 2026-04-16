package com.codependentvariables.aiandme.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock database implementation for storing quiz template data.
 */
public class MockQuizTemplateDAO implements IQuizTemplateDAO {
    private static final ArrayList<QuizTemplate> templates = new ArrayList<>();
    private static int autoIncrementId = 1;

    @Override
    public void addTemplate(QuizTemplate quizTemplate) {
        quizTemplate.setId(autoIncrementId);
        autoIncrementId++;
        templates.add(quizTemplate);
    }

    @Override
    public void updateTemplate(QuizTemplate quizTemplate) {
        for (int i = 0; i < templates.size(); i++) {
            if (templates.get(i).getId() == quizTemplate.getId()) {
                templates.set(i, quizTemplate);
                break;
            }
        }
    }

    @Override
    public void deleteTemplate(QuizTemplate quizTemplate) {
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
    public List<QuizTemplate> getAllTemplates() {
        return new ArrayList<>(templates);
    }

    @Override
    public List<QuizTemplate> getTemplatesByCategory(int categoryId) {
        List<QuizTemplate> result = new ArrayList<>();
        for (QuizTemplate template : templates) {
            if (template.getCategoryId() == categoryId) {
                result.add(template);
            }
        }
        return result;
    }
}

