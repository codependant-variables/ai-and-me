package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTemplateAnswerModelTest {
    @Test
    public void createAnswer() {
        // Checks for creation of answer that doesn't have image
        QuizTemplateAnswer answer = new QuizTemplateAnswer(2, "Paris", true);

        assertEquals(2, answer.getQuizTemplateQuestionId());
        assertEquals("Paris", answer.getText());
        assertTrue(answer.isCorrect());
        assertEquals(0, answer.getId());
        assertNull(answer.getImage());
    }

    // Research: https://junit.org/junit4/javadoc/4.12/org/junit/Assert.html#:~:text=Asserts%20that%20two%20boolean%20arrays%20are%20equal.,Asserts%20that%20two%20byte%20arrays%20are%20equal.
    @Test
    public void createAnswerImage() {
        // Checks for creation of answer with an image
        byte[] image = {1, 2, 3};

        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Image answer", image, true);

        // Checks both image arrays contain the same data
        assertArrayEquals(image, answer.getImage());
    }

    @Test
    public void updateFields() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Old", false);

        answer.setId(10);
        answer.setQuizTemplateQuestionId(5);
        answer.setText("New");
        answer.setCorrect(true);

        assertEquals(10, answer.getId());
        assertEquals(5, answer.getQuizTemplateQuestionId());
        assertEquals("New", answer.getText());
        assertTrue(answer.isCorrect());
    }

    @Test
    public void checkAnswerText() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Good answer", true);
        assertTrue(answer.isValid());

        answer.setText(" "); // Example of invalid text
        assertFalse(answer.isValid());
    }

    @Test
    public void returnsAnswerText() {
        // Checks that the text for an answer is returned as a string
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Test", false);
        assertEquals("Test", answer.toString());
    }
}
