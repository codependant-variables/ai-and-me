package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.model.QuizAttemptSummary;
import com.codependentvariables.aiandme.model.QuizTemplateAnswer;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizAttemptResultsControllerTest {

    @Test
    void generalAttempt() {
        QuizTemplateQuestion q = new QuizTemplateQuestion(10, "What is 2 + 2?");
        q.setId(1);
        QuizTemplateAnswer correct = new QuizTemplateAnswer(1, "4", true);
        correct.setId(10);

        Timestamp ts = Timestamp.valueOf("2026-04-30 14:35:00");

        QuizAttemptSummary summary = QuizAttemptSummary.of(
                "Maths Quiz",
                1,
                1,
                ts,
                List.of(q),
                List.of(correct)
        );

        assertEquals("Maths Quiz", summary.getQuizName());
        assertEquals(100, summary.getPercentage());
        assertEquals("30 Apr 2026, 14:35", summary.getFormattedDate());
        assertEquals(1, summary.getRows().size());
        assertTrue(summary.getRows().get(0).correct());
    }
}
