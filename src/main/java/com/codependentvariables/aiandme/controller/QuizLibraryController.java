package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;
import com.codependentvariables.aiandme.modules.Router;
import com.codependentvariables.aiandme.modules.View;
import com.codependentvariables.aiandme.services.CategoryService;
import com.codependentvariables.aiandme.services.QuizAttemptService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.state.AppState;
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
    protected String getPageTitle() {
        return "Quiz Library";
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