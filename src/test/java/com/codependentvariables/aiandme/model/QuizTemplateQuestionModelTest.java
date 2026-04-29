package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * QuizTemplateQuestion Model
 */
public class QuizTemplateQuestionModelTest {

    // Construction & field access

    @Test
    public void constructor_fieldsAreAccessible() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(3, "What is 2+2?");

        assertEquals(3,              question.getQuizTemplateId());
        assertEquals("What is 2+2?", question.getText());
    }

    @Test
    public void constructor_textIsNonNull() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Some question?");
        assertNotNull(question.getText());
    }

    @Test
    public void constructor_answersListIsEmptyByDefault() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Some question?");
        assertTrue(question.getAnswers().isEmpty());
    }

    @Test
    public void constructor_idIsZeroByDefault() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Some question?");
        assertEquals(0, question.getId());
    }

    @Test
    public void constructor_withImage_imageIsStored() {
        byte[] img = new byte[]{1, 2, 3};
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Img question?", img);
        assertArrayEquals(img, question.getImage());
    }

    @Test
    public void constructor_withoutImage_imageIsNull() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "No image?");
        assertNull(question.getImage());
    }

    // Setters

    @Test
    public void setId_updatesId() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        question.setId(99);
        assertEquals(99, question.getId());
    }

    @Test
    public void setText_updatesText() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Old?");
        question.setText("New?");
        assertEquals("New?", question.getText());
    }

    @Test
    public void setQuizTemplateId_updatesId() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        question.setQuizTemplateId(5);
        assertEquals(5, question.getQuizTemplateId());
    }

    // Answer aggregate

    @Test
    public void addAnswer_answerAppearsInList() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        question.setId(10);
        QuizTemplateAnswer answer = new QuizTemplateAnswer(0, "Answer A", true);

        question.addAnswer(answer);

        assertEquals(1, question.getAnswers().size());
        assertTrue(question.getAnswers().contains(answer));
    }

    @Test
    public void addAnswer_setsQuestionIdOnAnswer() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        question.setId(7);
        QuizTemplateAnswer answer = new QuizTemplateAnswer(0, "Answer A", true);

        question.addAnswer(answer);

        assertEquals(7, answer.getQuizTemplateQuestionId());
    }

    @Test
    public void removeAnswer_answerIsRemovedFromList() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "Answer A", true);
        question.addAnswer(answer);

        question.removeAnswer(answer);

        assertTrue(question.getAnswers().isEmpty());
    }

    @Test
    public void getAnswers_returnsUnmodifiableList() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");

        assertThrows(UnsupportedOperationException.class,
                () -> question.getAnswers().add(new QuizTemplateAnswer(1, "X", false)));
    }

    @Test
    public void setAnswers_replacesExistingAnswers() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        question.addAnswer(new QuizTemplateAnswer(1, "Old A", false));

        question.setAnswers(List.of(
                new QuizTemplateAnswer(1, "New A", true),
                new QuizTemplateAnswer(1, "New B", false)
        ));

        assertEquals(2, question.getAnswers().size());
    }

    // hasCorrectAnswer

    @Test
    public void hasCorrectAnswer_returnsTrueWhenOneAnswerIsCorrect() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        question.addAnswer(new QuizTemplateAnswer(1, "Wrong", false));
        question.addAnswer(new QuizTemplateAnswer(1, "Right", true));

        assertTrue(question.hasCorrectAnswer());
    }

    @Test
    public void hasCorrectAnswer_returnsFalseWhenNoCorrectAnswer() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        question.addAnswer(new QuizTemplateAnswer(1, "Wrong A", false));
        question.addAnswer(new QuizTemplateAnswer(1, "Wrong B", false));

        assertFalse(question.hasCorrectAnswer());
    }

    @Test
    public void hasCorrectAnswer_returnsFalseWhenNoAnswers() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        assertFalse(question.hasCorrectAnswer());
    }

    // isValid

    @Test
    public void isValid_returnsTrueForWellFormedQuestion() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        question.addAnswer(new QuizTemplateAnswer(1, "Wrong", false));
        question.addAnswer(new QuizTemplateAnswer(1, "Right", true));

        assertTrue(question.isValid());
    }

    @Test
    public void isValid_returnsFalseWithOnlyOneAnswer() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        question.addAnswer(new QuizTemplateAnswer(1, "Only", true));

        assertFalse(question.isValid());
    }

    @Test
    public void isValid_returnsFalseWithNoCorrectAnswer() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        question.addAnswer(new QuizTemplateAnswer(1, "A", false));
        question.addAnswer(new QuizTemplateAnswer(1, "B", false));

        assertFalse(question.isValid());
    }

    @Test
    public void isValid_returnsFalseWithNoAnswers() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "Q?");
        assertFalse(question.isValid());
    }

    // toString

    @Test
    public void toString_returnsText() {
        QuizTemplateQuestion question = new QuizTemplateQuestion(1, "What is Java?");
        assertEquals("What is Java?", question.toString());
    }

    // Validations not implemented yet might need to move this later ;( tear

    @Test
    public void constructor_nullText_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuizTemplateQuestion(1, null));
    }

    @Test
    public void constructor_blankText_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuizTemplateQuestion(1, "   "));
    }
}

