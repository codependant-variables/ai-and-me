package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.QuizTemplateAnswer;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizAttemptResultsController {

    @FXML private Label lblQuizName;
    @FXML private Label lblScore;
    @FXML private Label lblPercentage;
    @FXML private VBox  resultsContainer;

    public void initResults(String quizName, int correct, int total, List<ResultRow> rows) {
        lblQuizName.setText(quizName);
        lblScore.setText("Score:  " + correct + " / " + total);
        lblPercentage.setText(calculatePercentage(correct, total) + "%");

        resultsContainer.getChildren().clear();
        for (ResultRow row : rows) {
            resultsContainer.getChildren().add(buildRowNode(row));
        }
    }

    private HBox buildRowNode(ResultRow row) {
        Label indicator = new Label(row.correct() ? "✓" : "✗");
        indicator.setFont(Font.font("System", FontWeight.BOLD, 14));
        indicator.setTextFill(row.correct() ? Color.web("#388e3c") : Color.web("#d32f2f"));
        indicator.setMinWidth(24);

        Label questionLbl = new Label(row.questionText());
        questionLbl.setWrapText(true);
        questionLbl.setFont(Font.font("System", 13));
        HBox.setHgrow(questionLbl, Priority.ALWAYS);

        Label answerLbl = new Label(row.selectedAnswer());
        answerLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        answerLbl.setTextFill(row.correct() ? Color.web("#388e3c") : Color.web("#d32f2f"));
        answerLbl.setMinWidth(80);

        HBox hbox = new HBox(10, indicator, questionLbl, answerLbl);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(8, 10, 8, 10));
        String bg = row.correct() ? "#f1f8e9" : "#ffebee";
        hbox.setStyle("-fx-background-color: " + bg + "; -fx-background-radius: 6;");
        return hbox;
    }

    // todo: @FXML private void handleBackToLibrary() method

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