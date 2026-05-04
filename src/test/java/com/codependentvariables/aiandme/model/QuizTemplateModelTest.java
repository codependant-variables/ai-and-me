package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * QuizTemplate Model
 */
public class QuizTemplateModelTest {

    // Construction & field access

    @Test
    public void constructor_allFieldsAreAccessible() {
        QuizTemplate template = new QuizTemplate("Basic Math", 1, 2, "draft");

        assertEquals("Basic Math", template.getName());
        assertEquals(1,            template.getCategoryId());
        assertEquals(2,            template.getUserId());
        assertEquals("draft",      template.getStatus());
    }

    @Test
    public void constructor_nameIsNonNull() {
        QuizTemplate template = new QuizTemplate("Science", 1, 1, "draft");
        assertNotNull(template.getName());
    }

    @Test
    public void constructor_statusIsNonNull() {
        QuizTemplate template = new QuizTemplate("History", 1, 1, "published");
        assertNotNull(template.getStatus());
    }

    @Test
    public void constructor_guestUser_userIdIsZero() {
        QuizTemplate template = new QuizTemplate("Guest Quiz", 1, 0, "draft");
        assertEquals(0, template.getUserId());
    }

    // Setters

    @Test
    public void setName_updatesName() {
        QuizTemplate template = new QuizTemplate("Old Name", 1, 1, "draft");
        template.setName("New Name");
        assertEquals("New Name", template.getName());
    }

    @Test
    public void setUserId_updatesUserId() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        template.setUserId(5);
        assertEquals(5, template.getUserId());
    }

    @Test
    public void setCategoryId_updatesCategoryId() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        template.setCategoryId(3);
        assertEquals(3, template.getCategoryId());
    }

    @Test
    public void setStatus_updatesStatus() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        template.setStatus("published");
        assertEquals("published", template.getStatus());
    }

    @Test
    public void setId_updatesId() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        template.setId(42);
        assertEquals(42, template.getId());
    }

    // Default state

    @Test
    public void newTemplate_idIsZeroByDefault() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        assertEquals(0, template.getId());
    }

    @Test
    public void newTemplate_questionsListIsEmptyByDefault() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        assertTrue(template.getQuestions().isEmpty());
    }

    // Question aggregate

    @Test
    public void addQuestion_questionAppearsInList() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        template.setId(10);
        QuizTemplateQuestion question = new QuizTemplateQuestion(0, "What is 2+2?");

        template.addQuestion(question);

        assertEquals(1, template.getQuestions().size());
        assertTrue(template.getQuestions().contains(question));
    }

    @Test
    public void addQuestion_setsQuizTemplateIdOnQuestion() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        template.setId(7);
        QuizTemplateQuestion question = new QuizTemplateQuestion(0, "What is 3+3?");

        template.addQuestion(question);

        assertEquals(7, question.getQuizTemplateId());
    }

    @Test
    public void removeQuestion_questionIsRemovedFromList() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "What is 4+4?");
        template.addQuestion(question);

        template.removeQuestion(question);

        assertTrue(template.getQuestions().isEmpty());
    }

    @Test
    public void getQuestions_returnsUnmodifiableList() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");

        assertThrows(UnsupportedOperationException.class,
                () -> template.getQuestions().add(new QuizTemplateQuestion(1, "Q?")));
    }

    @Test
    public void setQuestions_replacesExistingQuestions() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        template.addQuestion(new QuizTemplateQuestion(1, "Old Q"));

        template.setQuestions(List.of(
                new QuizTemplateQuestion(1, "New Q1"),
                new QuizTemplateQuestion(1, "New Q2")
        ));

        assertEquals(2, template.getQuestions().size());
    }

    // toString

    @Test
    public void toString_returnsName() {
        QuizTemplate template = new QuizTemplate("Biology", 1, 1, "draft");
        assertEquals("Biology", template.toString());
    }

    // Validation, might be moved elsewhere later

    @Test
    public void constructor_nullName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuizTemplate(null, 1, 1, "draft"));
    }

    @Test
    public void constructor_blankName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuizTemplate("   ", 1, 1, "draft"));
    }

    @Test
    public void constructor_nullStatus_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuizTemplate("Quiz", 1, 1, null));
    }
}
