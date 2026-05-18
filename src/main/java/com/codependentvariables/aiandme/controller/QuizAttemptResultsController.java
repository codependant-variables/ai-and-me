package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.QuizAttemptSummary;
import com.codependentvariables.aiandme.model.QuizAttemptSummary.ResultRow;
import com.codependentvariables.aiandme.modules.Router;
import com.codependentvariables.aiandme.modules.View;
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

/**
 * Controller responsible for displaying the results of a completed quiz attempt.
 */
public class QuizAttemptResultsController {
    @FXML
    private Label lblQuizName;
    @FXML
    private Label lblScore;
    @FXML
    private Label lblPercentage;
    @FXML
    private Label lblCompletedAt;
    @FXML
    private VBox  resultsContainer;

    /**
     * Initializes the results view with summary data.
     * Populates header info and dynamically builds result rows.
     */
    public void initialiseData(QuizAttemptSummary summary) {
        lblQuizName.setText(summary.getQuizName());
        lblScore.setText("Score:  " + summary.getCorrect() + " / " + summary.getTotal());
        lblPercentage.setText(summary.getPercentage() + "%");
        lblCompletedAt.setText("Completed: " + summary.getFormattedDate());

        resultsContainer.getChildren().clear();
        for (ResultRow row : summary.getRows()) {
            resultsContainer.getChildren().add(buildRowNode(row));
        }
    }

    /**
     * Creates a visual row representing a single question result.
     * Includes correctness indicator, question text, and selected answer.
     */
    private HBox buildRowNode(ResultRow row) {
        // Correct/Incorrect indicator label
        Label indicator = new Label(row.correct() ? "Correct" : "Incorrect");
        indicator.setFont(Font.font("System", FontWeight.BOLD, 14));
        indicator.setTextFill(row.correct() ? Color.web("#388e3c") : Color.web("#d32f2f"));
        indicator.setMinWidth(24);

        // Question text label
        Label questionLbl = new Label(row.questionText());
        questionLbl.setWrapText(true);
        questionLbl.setFont(Font.font("System", 13));
        HBox.setHgrow(questionLbl, Priority.ALWAYS);

        // Selected answer label
        Label answerLbl = new Label(row.selectedAnswer());
        answerLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        //answerLbl.setTextFill(row.correct() ? Color.web("#388e3c") : Color.web("#d32f2f"));
        answerLbl.setMinWidth(80);

        // Row layout container
        HBox hbox = new HBox(10, indicator, questionLbl, answerLbl);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(8, 10, 8, 10));

        // Border color based on correctness        //TODO: figure out how to make this work
        //String border = row.correct() ? "#f1f8e9" : "#ffebee";
        //hbox.setStyle("-fx-border-color{$border: " + "; -fx-border-radius: 6;");

        return hbox;
    }

    @FXML
    public void handleBack() {
        Router.navigateLayout(View.QUIZ_LIBRARY);
    }
}