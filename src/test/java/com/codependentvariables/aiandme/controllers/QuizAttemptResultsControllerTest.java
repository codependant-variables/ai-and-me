package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.controller.QuizAttemptResultsController;
import com.codependentvariables.aiandme.controller.QuizAttemptResultsController.ResultRow;
import com.codependentvariables.aiandme.model.QuizTemplateAnswer;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class QuizAttemptResultsControllerTest {

    private QuizTemplateQuestion q1;
    private QuizTemplateQuestion q2;
    private QuizTemplateQuestion q3;

    private QuizTemplateAnswer correctA1;
    private QuizTemplateAnswer incorrectA1;
    private QuizTemplateAnswer correctA2;
    private QuizTemplateAnswer incorrectA2;

    @BeforeEach
    void setUp() {
        q1 = new QuizTemplateQuestion(10, "What is 2 + 2?");
        q1.setId(1);
        q2 = new QuizTemplateQuestion(10, "What is 3 × 3?");
        q2.setId(2);
        q3 = new QuizTemplateQuestion(10, "What is 10 ÷ 2?");
        q3.setId(3);

        correctA1   = new QuizTemplateAnswer(1, "4",   true);  correctA1.setId(10);
        incorrectA1 = new QuizTemplateAnswer(1, "5",  false);  incorrectA1.setId(11);
        correctA2   = new QuizTemplateAnswer(2, "9",   true);  correctA2.setId(20);
        incorrectA2 = new QuizTemplateAnswer(2, "6",  false);  incorrectA2.setId(21);
    }

    @Test
    void calculatePercentage_returns100_forPerfectScore() {
        assertEquals(100, QuizAttemptResultsController.calculatePercentage(10, 10));
    }

    @Test
    void calculatePercentage_returnsZero_forZeroScore() {
        assertEquals(0, QuizAttemptResultsController.calculatePercentage(0, 10));
    }

    @Test
    void calculatePercentage_roundsDown_forOneThird() {
        assertEquals(33, QuizAttemptResultsController.calculatePercentage(1, 3));
    }

    @Test
    void calculatePercentage_roundsUp_forTwoThirds() {
        assertEquals(67, QuizAttemptResultsController.calculatePercentage(2, 3));
    }


    @Test
    void calculatePercentage_returns100_forSingleCorrectQuestion() {
        assertEquals(100, QuizAttemptResultsController.calculatePercentage(1, 1));
    }

    @Test
    void calculatePercentage_returnsZero_forSingleIncorrectQuestion() {
        assertEquals(0, QuizAttemptResultsController.calculatePercentage(0, 1));
    }

    @Test
    void calculatePercentage_throwsIllegalArgument_whenTotalIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> QuizAttemptResultsController.calculatePercentage(0, 0)
        );
    }

    @Test
    void buildResultRows_marksCorrect_whenCorrectAnswerSelected() {
        Map<Integer, QuizTemplateAnswer> sel = Map.of(q1.getId(), correctA1);

        List<ResultRow> rows = QuizAttemptResultsController.buildResultRows(List.of(q1), sel);

        assertTrue(rows.get(0).correct());
    }

    @Test
    void buildResultRows_marksIncorrect_whenWrongAnswerSelected() {
        Map<Integer, QuizTemplateAnswer> sel = Map.of(q1.getId(), incorrectA1);

        List<ResultRow> rows = QuizAttemptResultsController.buildResultRows(List.of(q1), sel);

        assertFalse(rows.get(0).correct());
    }

    @Test
    void buildResultRows_includesFallbackRow_whenNoSelectionRecorded() {
        List<ResultRow> rows = QuizAttemptResultsController.buildResultRows(
            List.of(q1), new HashMap<>()
        );

        assertEquals(1, rows.size());
        assertFalse(rows.get(0).correct(), "Unanswered question must not count as correct");
        assertEquals("–", rows.get(0).selectedAnswer(), "Unanswered question must show dash placeholder");
    }

    @Test
    void buildResultRows_appliesCorrectFlagPerQuestion_inMixedAttempt() {
        Map<Integer, QuizTemplateAnswer> sel = new HashMap<>();
        sel.put(q1.getId(), incorrectA1);  // wrong
        sel.put(q2.getId(), correctA2);    // right

        List<ResultRow> rows = QuizAttemptResultsController.buildResultRows(
            List.of(q1, q2), sel
        );

        assertFalse(rows.get(0).correct(), "Q1 was answered incorrectly");
        assertTrue(rows.get(1).correct(),  "Q2 was answered correctly");
    }

    @Test
    void buildResultRows_rowCountMatchesQuestionCount() {
        Map<Integer, QuizTemplateAnswer> sel = Map.of(q1.getId(), correctA1);

        List<ResultRow> rows = QuizAttemptResultsController.buildResultRows(
            List.of(q1, q2, q3), sel
        );

        assertEquals(3, rows.size(), "One ResultRow must be produced for every question");
    }

    @Test
    void buildResultRows_preservesQuestionOrder() {
        Map<Integer, QuizTemplateAnswer> sel = new HashMap<>();
        sel.put(q1.getId(), correctA1);
        sel.put(q2.getId(), incorrectA2);

        List<ResultRow> rows = QuizAttemptResultsController.buildResultRows(
            List.of(q1, q2), sel
        );

        assertEquals(q1.getText(), rows.get(0).questionText(), "First row must match first question");
        assertEquals(q2.getText(), rows.get(1).questionText(), "Second row must match second question");
    }

    @Test
    void buildResultRows_rowCarriesSelectedAnswerText() {
        Map<Integer, QuizTemplateAnswer> sel = Map.of(q1.getId(), incorrectA1);

        List<ResultRow> rows = QuizAttemptResultsController.buildResultRows(List.of(q1), sel);

        assertEquals(incorrectA1.getText(), rows.get(0).selectedAnswer(),
            "Row must carry the exact text of the selected answer");
    }

    @Test
    void buildResultRows_returnsEmptyList_forEmptyQuestionList() {
        List<ResultRow> rows = QuizAttemptResultsController.buildResultRows(
            List.of(), new HashMap<>()
        );

        assertNotNull(rows);
        assertTrue(rows.isEmpty(), "Empty question list must produce empty result list");
    }

    @Test
    void formatCompletedAt_returnsFormattedString_forValidTimestamp() {
        Timestamp ts = Timestamp.valueOf("2026-04-30 14:35:00");

        String result = QuizAttemptResultsController.formatCompletedAt(ts);

        assertEquals("30 Apr 2026, 14:35", result);
    }

    @Test
    void formatCompletedAt_returnsFallback_forNullTimestamp() {
        String result = QuizAttemptResultsController.formatCompletedAt(null);

        assertNotNull(result);
        assertFalse(result.isBlank(), "Null timestamp must produce a non-blank fallback string");
    }
}
