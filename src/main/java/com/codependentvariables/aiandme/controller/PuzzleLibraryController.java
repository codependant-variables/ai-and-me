package com.codependentvariables.aiandme.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for the Puzzle Library screen.
 * Reuses quiz-library.fxml which is the same layout as the Quiz Library.
 * Puzzle is a type of quiz and will be added here, separating the logic.
 */
public class PuzzleLibraryController extends QuizController {

    @FXML private Label pageTitle;
    @FXML private Button btnCreate;
    @FXML private Button btnModify;
    @FXML private Button btnDelete;
    @FXML private Button btnEditQuestions;
    @FXML private Button btnAttemptQuiz;

    @FXML
    @Override
    public void initialize() {
        pageTitle.setText(getPageTitle());
        btnCreate.setDisable(true);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
        btnEditQuestions.setDisable(true);
        btnAttemptQuiz.setDisable(true);
    }
    @Override
    protected String getPageTitle() {
        return "Puzzle Library";
    }

    @Override
    public void initialise() {
    }

    protected void refreshTemplates() {

    }

    protected void handleAttemptQuiz() {

    }
}
