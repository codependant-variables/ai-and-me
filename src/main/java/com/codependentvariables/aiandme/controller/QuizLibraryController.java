package com.codependentvariables.aiandme.controller;

import javafx.fxml.FXML;

/**
 * Handles displaying quiz templates and quiz management actions.
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

    /**
     * Initialises the quiz library view.
     */
    @Override
    public void initialise() {

    }
}