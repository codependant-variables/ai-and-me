package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTemplateModelTest {

    @Test
    public void newTemplate_fieldsMatchWhatWasPassedIn() {
        QuizTemplate template = new QuizTemplate("Basic Math", 1, 2, "draft");

        assertEquals("Basic Math", template.getName());
        assertEquals(1, template.getCategoryId());
        assertEquals(2, template.getUserId());
        assertEquals("draft", template.getStatus());
    }

    @Test
    public void startWithZero() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        assertEquals(0, template.getId());
    }

    @Test
    public void startListEmpty() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        assertTrue(template.getQuestions().isEmpty());
    }

    @Test
    public void updateSetValues() {
        QuizTemplate template = new QuizTemplate("Old Name", 1, 1, "draft");

        template.setId(42);
        template.setName("New Name");
        template.setCategoryId(3);
        template.setUserId(5);
        template.setStatus("published");

        assertEquals(42, template.getId());
        assertEquals("New Name", template.getName());
        assertEquals(3, template.getCategoryId());
        assertEquals(5, template.getUserId());
        assertEquals("published", template.getStatus());
    }
}