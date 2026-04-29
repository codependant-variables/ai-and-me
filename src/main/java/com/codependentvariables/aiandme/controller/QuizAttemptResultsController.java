package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.QuizTemplateAnswer;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizAttemptResultsController {

    @FXML private Label lblQuizName;
    @FXML private Label lblScore;
    @FXML private Label lblPercentage;
    @FXML private VBox  resultsContainer;

    public void initResults() {
        // todo 1: wire labels once the .fxml view file is created
    }

    // todo 2: handle "Back to Library" button action


    public static int calculatePercentage(int correct, int total) {
        if (total <= 0) {
            throw new IllegalArgumentException("total must be greater than 0, got: " + total);
        }
        return (int) Math.round((double) correct / total * 100);
    }

    public static List<ResultRow> buildResultRows(
            List<QuizTemplateQuestion> questions,
            Map<Integer, QuizTemplateAnswer> selections) {

        List<ResultRow> rows = new ArrayList<>();
        for (QuizTemplateQuestion q : questions) {
            QuizTemplateAnswer selected = selections.get(q.getId());
            if (selected != null) {
                rows.add(new ResultRow(q.getText(), selected.getText(), selected.isCorrect()));
            } else {
                rows.add(new ResultRow(q.getText(), "–", false));
            }
        }
        return List.copyOf(rows);
    }

    public record ResultRow(String questionText, String selectedAnswer, boolean correct) {}
}

