package com.codependentvariables.aiandme.controller;

import javafx.fxml.FXML;

/**
 * Controller for the Quiz Library screen.
 * Uses quiz-library.fxml which is used also for PuzzleLibrary.
 * Handles displaying and showing functionality for quiz templates.
 */

public class QuizLibraryController extends QuizController {
    @FXML
    @Override
    public void initialize() {
        super.initialize();
    }
    @Override
    protected String getPageName() {
        return "Quiz";
    }

    /** Only show templates that are NOT puzzles. */
    @Override
    protected Boolean isPuzzleFilter() {
        return false;
    }

    @Override
    public void initialise() {

    }
}