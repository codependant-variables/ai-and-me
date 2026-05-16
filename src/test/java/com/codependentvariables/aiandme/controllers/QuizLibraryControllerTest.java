package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.controller.QuizLibraryController;
import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.model.QuizTemplateDAOTest;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class QuizLibraryControllerTest {

    // Research for asList: https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html
    @Test
    void checkTwoQuestions() {
        // Checks that quiz with two questions are able to be attempted
        QuizTemplateQuestion q1 = new QuizTemplateQuestion(10, "Q1");
        QuizTemplateQuestion q2 = new QuizTemplateQuestion(10, "Q2");

        List<QuizTemplateQuestion> questions = Arrays.asList(q1, q2);
        assertTrue(QuizLibraryController.isAttemptable(questions));
    }

    @Test
    void checkOneQuestion() {
        // Checks that quiz with one question cannot be attempted
        QuizTemplateQuestion q1 = new QuizTemplateQuestion(10, "Q1");

        List<QuizTemplateQuestion> questions = Arrays.asList(q1);
        assertFalse(QuizLibraryController.isAttemptable(questions));
    }

    @Test
    void checkEmptyList() {
        // Checks that quiz with no questions cannot be attempted

        List<QuizTemplateQuestion> questions = Arrays.asList();
        assertFalse(QuizLibraryController.isAttemptable(questions));
    }

    @Test
    void checksNullList() {
        // Checks that a quiz with null instead of questions is unable to be attempted.
        // Null shouldn't throw an error and should return false
        assertFalse(QuizLibraryController.isAttemptable(null));
    }

    @Test
    void isPuzzleShown() {
        // A template with isPuzzle=true should NOT be shown in the quiz library (isPuzzle must be false)
        QuizTemplate puzzleTemplate = new QuizTemplate("Puzzle One", 1, 1, true, "draft");
        assertFalse(!puzzleTemplate.isPuzzle()); // isPuzzle is true means this does not pass quiz library filter
    }

    @Test
    void isPuzzleHidden() {
        // A template with isPuzzle=false should be shown in the quiz library
        QuizTemplate quizTemplate = new QuizTemplate("Quiz One", 1, 1, false, "draft");
        assertFalse(quizTemplate.isPuzzle()); // isPuzzle is false means this passes quiz library filter
    }
}
