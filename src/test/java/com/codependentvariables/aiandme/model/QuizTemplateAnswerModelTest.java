package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * QuizTemplateAnswer Model
 */
public class QuizTemplateAnswerModelTest {

    // Construction & field access

    @Test
    public void constructor_fieldsAreAccessible() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(2, "Paris", true);

        assertEquals(2,       answer.getQuizTemplateQuestionId());
        assertEquals("Paris", answer.getText());
        assertTrue(answer.isCorrect());
    }

    @Test
    public void constructor_correctFlagFalse_isCorrectReturnsFalse() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "London", false);
        assertFalse(answer.isCorrect());
    }

    @Test
    public void constructor_correctFlagTrue_isCorrectReturnsTrue() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Paris", true);
        assertTrue(answer.isCorrect());
    }

    @Test
    public void constructor_textIsNonNull() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Some answer", false);
        assertNotNull(answer.getText());
    }

    @Test
    public void constructor_idIsZeroByDefault() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Some answer", false);
        assertEquals(0, answer.getId());
    }

    @Test
    public void constructor_withImage_imageIsStored() {
        byte[] img = new byte[]{1, 2, 3};
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Img answer", img, true);
        assertArrayEquals(img, answer.getImage());
    }

    @Test
    public void constructor_withoutImage_imageIsNull() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "No image", false);
        assertNull(answer.getImage());
    }

    // Setters

    @Test
    public void setId_updatesId() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "A", false);
        answer.setId(10);
        assertEquals(10, answer.getId());
    }

    @Test
    public void setText_updatesText() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Old", false);
        answer.setText("New");
        assertEquals("New", answer.getText());
    }

    @Test
    public void setCorrect_updatesFlag() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "A", false);
        answer.setCorrect(true);
        assertTrue(answer.isCorrect());
    }

    @Test
    public void setQuizTemplateQuestionId_updatesId() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "A", false);
        answer.setQuizTemplateQuestionId(5);
        assertEquals(5, answer.getQuizTemplateQuestionId());
    }

    // isValid

    @Test
    public void isValid_returnsTrueForNonBlankText() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Valid answer", true);
        assertTrue(answer.isValid());
    }

    @Test
    public void isValid_returnsFalseForBlankText() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "placeholder", false);
        answer.setText("   ");
        assertFalse(answer.isValid());
    }

    // toString

    @Test
    public void toString_returnsText() {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Berlin", false);
        assertEquals("Berlin", answer.toString());
    }

    // Validation yep, definitely moving this ;(

    @Test
    public void constructor_nullText_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuizTemplateAnswer(1, null, false));
    }

    @Test
    public void constructor_blankText_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuizTemplateAnswer(1, "   ", false));
    }

    @Test
    public void constructor_withImage_nullText_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuizTemplateAnswer(1, null, new byte[]{1}, false));
    }
}

