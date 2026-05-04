package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.controller.QuizLibraryController;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizLibraryControllerTest {

    @Test
    void isAttemptable_returnsTrue_forTwoQuestions() {
        List<QuizTemplateQuestion> questions = List.of(
            makeQuestion(1, "Q1"),
            makeQuestion(2, "Q2")
        );

        assertTrue(QuizLibraryController.isAttemptable(questions),
            "Two questions meets the minimum — should be attemptable");
    }

    @Test
    void isAttemptable_returnsTrue_forMoreThanTwoQuestions() {
        List<QuizTemplateQuestion> questions = List.of(
            makeQuestion(1, "Q1"),
            makeQuestion(2, "Q2"),
            makeQuestion(3, "Q3")
        );

        assertTrue(QuizLibraryController.isAttemptable(questions));
    }

    @Test
    void isAttemptable_returnsFalse_forOneQuestion() {
        List<QuizTemplateQuestion> questions = List.of(makeQuestion(1, "Q1"));

        assertFalse(QuizLibraryController.isAttemptable(questions),
            "One question is below the minimum — should not be attemptable");
    }

    @Test
    void isAttemptable_returnsFalse_forEmptyList() {
        assertFalse(QuizLibraryController.isAttemptable(List.of()),
            "Empty question list must not be attemptable");
    }

    @Test
    void isAttemptable_returnsFalse_forNullList() {
        assertFalse(QuizLibraryController.isAttemptable(null),
            "Null question list must not throw — must return false");
    }

    // helper

    private QuizTemplateQuestion makeQuestion(int id, String text) {
        QuizTemplateQuestion q = new QuizTemplateQuestion(10, text);
        q.setId(id);
        return q;
    }
}

