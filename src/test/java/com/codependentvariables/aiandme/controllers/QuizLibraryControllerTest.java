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

        List<QuizTemplateQuestion> questions = Arrays.asList(q1, q2); // Creates a list which contains the strings
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
    void checkCategoryFiltering() {
        // Checks that only quizzes matching the selected category are returned

        QuizTemplate mathsQuiz = new QuizTemplate("Math Quiz", 1, 1, "open");
        QuizTemplate patternQuiz = new QuizTemplate("Pattern Quiz", 2, 1, "open");
        QuizTemplate anotherMathsQuiz = new QuizTemplate("Algebra Quiz", 1, 1, "open");

        List<QuizTemplate> templates = Arrays.asList(
                mathsQuiz,
                patternQuiz,
                anotherMathsQuiz
        );

        int selectedCategoryId = 1;

        List<QuizTemplate> filtered = templates.stream()
                .filter(t -> t.getCategoryId() == selectedCategoryId)
                .toList();

        assertEquals(2, filtered.size());
        assertTrue(filtered.contains(mathsQuiz));
        assertTrue(filtered.contains(anotherMathsQuiz));
        assertFalse(filtered.contains(patternQuiz));
    }
}