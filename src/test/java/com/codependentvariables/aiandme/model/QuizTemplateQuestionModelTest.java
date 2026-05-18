package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTemplateQuestionModelTest {
    private QuizTemplateQuestion question;

    // Research: https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations
    @BeforeEach
    void setUp() {
        question = new QuizTemplateQuestion(3, "What is 2+2?");
    }

    @Test
    void checkFields() {
        assertEquals(3, question.getQuizTemplateId());
        assertEquals("What is 2+2?", question.getText());
    }

    @Test
    void defaultId() {
        assertEquals(0, question.getId()); // id is 0 until saved to the database
    }

    @Test
    void checkEmpty() {
        assertTrue(question.getAnswers().isEmpty()); // no answers added yet
    }

    @Test
    void updateFields() {
        question.setId(99);
        question.setText("New question?");
        question.setQuizTemplateId(5);

        assertEquals(99, question.getId());
        assertEquals("New question?", question.getText());
        assertEquals(5, question.getQuizTemplateId());
    }

    @Test
    void addAnswer() {
        question.setId(10);
        QuizTemplateAnswer answer = new QuizTemplateAnswer(0, "Answer A", true);

        question.addAnswer(answer);

        assertTrue(question.getAnswers().contains(answer));
        assertEquals(10, answer.getQuizTemplateQuestionId()); // answer should know which question it belongs to
    }

    @Test
    void removeAnswer() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Answer A", true);
        question.addAnswer(answer);

        question.removeAnswer(answer);

        assertTrue(question.getAnswers().isEmpty());
    }

    @Test
    void checkValid() {
        assertFalse(question.isValid()); // not valid with no answers

        question.addAnswer(new QuizTemplateAnswer(1, "Wrong", false));
        assertFalse(question.isValid()); // not valid with only one answer

        question.addAnswer(new QuizTemplateAnswer(1, "Right", true));
        assertTrue(question.isValid()); // valid once there are 2+ answers with one correct
    }
}