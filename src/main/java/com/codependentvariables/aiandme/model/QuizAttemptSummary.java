package com.codependentvariables.aiandme.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class QuizAttemptSummary {

    private final String quizName;
    private final int correct;
    private final int total;
    private final Timestamp completedAt;
    private final List<ResultRow> rows;
    private final List<QuizTemplateAnswer> selectedAnswers = new ArrayList<>();

    public QuizAttemptSummary(String quizName, int correct, int total,
                               Timestamp completedAt, List<ResultRow> rows) {
        if (quizName == null || quizName.isBlank())
            throw new IllegalArgumentException("quizName must not be blank");
        if (correct < 0)
            throw new IllegalArgumentException("correct must not be negative");
        if (correct > total)
            throw new IllegalArgumentException("correct (" + correct + ") must not exceed total (" + total + ")");
        this.quizName    = quizName;
        this.correct     = correct;
        this.total       = total;
        this.completedAt = completedAt;
        this.rows        = rows;
    }

    public static QuizAttemptSummary of(String quizName, int correct, int total,
                                        Timestamp completedAt,
                                        List<QuizTemplateQuestion> questions,
                                        List<QuizTemplateAnswer> selectedAnswers) {
        return new QuizAttemptSummary(
                quizName, correct, total, completedAt,
                buildResultRows(questions, selectedAnswers)
        );
    }

    // getters

    public String getQuizName()        { return quizName; }
    public int    getCorrect()         { return correct; }
    public int    getTotal()           { return total; }
    public int    getPercentage()      { return calculatePercentage(correct, total); }
    public String getFormattedDate()   { return formatCompletedAt(completedAt); }
    public List<ResultRow> getRows()   { return rows; }


    public static int calculatePercentage(int correct, int total) {
        if (total <= 0) {
            throw new IllegalArgumentException("total must be greater than 0, got: " + total);
        }
        return (int) Math.round((double) correct / total * 100);
    }

    public static String formatCompletedAt(Timestamp ts) {
        if (ts == null) return "Unknown";
        return new SimpleDateFormat("d MMM yyyy, HH:mm").format(ts);
    }

    public static List<ResultRow> buildResultRows(
            List<QuizTemplateQuestion> questions,
            List<QuizTemplateAnswer> selectedAnswers) {

        List<ResultRow> rows = new ArrayList<>();

        for (QuizTemplateQuestion question : questions) {
            QuizTemplateAnswer selected = selectedAnswers.stream()
                    .filter(answer -> answer.getQuizTemplateQuestionId() == question.getId())
                    .findFirst()
                    .orElse(null);

            if (selected != null) {
                rows.add(new ResultRow(question.getText(), selected.getText(), selected.isCorrect()));
            } else {
                rows.add(new ResultRow(question.getText(), "–", false));
            }
        }
        return List.copyOf(rows);
    }

    // types

    public record ResultRow(String questionText, String selectedAnswer, boolean correct) {}
}


