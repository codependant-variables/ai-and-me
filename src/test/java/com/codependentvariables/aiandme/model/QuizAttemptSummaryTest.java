package com.codependentvariables.aiandme.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizAttemptSummaryTest {
    @Test
    void createSummary() {
        // Create an empty list of rows for displaying all quiz attempts
        List<QuizAttemptSummary.ResultRow> rows = new ArrayList<>();

        QuizAttemptSummary summary = new QuizAttemptSummary("Math Quiz", 2, 3, null, rows);

        assertEquals("Math Quiz", summary.getQuizName()); // Checks quiz name set correctly
        assertEquals(2, summary.getCorrect());
        assertEquals(3, summary.getTotal());
    }

    @Test
    void calculatePercent() {
        // Checks percentage calculation for a quiz attempt
        assertEquals(100, QuizAttemptSummary.calculatePercentage(3, 3));
        assertEquals(50, QuizAttemptSummary.calculatePercentage(1,2));
    }

    @Test
    void formatDate() {
        // Checks that the date is formatted correctly
        // https://docs.oracle.com/javase/8/docs/api/java/sql/Timestamp.html
        Timestamp time = Timestamp.valueOf("2026-04-30 14:35:00");

        String result = QuizAttemptSummary.formatCompletedAt(time);

        assertEquals("30 Apr 2026, 14:35", result);
    }

    @Test
    void testAttempt() {
        // replicates process of user taking a quiz
        QuizTemplateQuestion question = new QuizTemplateQuestion(10, "What is 2+2?");
        question.setId(1);

        List<QuizTemplateQuestion> quizQuestions = new ArrayList<>();
        quizQuestions.add(question);

        QuizTemplateAnswer answer = new QuizTemplateAnswer(1, "4", true);

        Timestamp time = Timestamp.valueOf("2026-04-30 09:00:00");

        QuizAttemptSummary summary = new QuizAttemptSummary(
                "Math Quiz",
                1,
                1,
                time,
                new ArrayList<>()
        );
    }
}