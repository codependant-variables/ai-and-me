package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.model.PuzzleTemplateQuestion;
import com.codependentvariables.aiandme.model.QuizTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PuzzleLibraryControllerTest {

    private QuizTemplate puzzleTemplate;
    private PuzzleTemplateQuestion puzzleQuestion;

    @BeforeEach
    void setUp() {
        puzzleTemplate = new QuizTemplate("My Puzzle", 1, 1, true, "draft");
    }

    @Test
    void isPuzzleTrue() {
        // TODO
    }

    @Test
    void questionImageIsStored() {
        // TODO
    }

    @Test
    void puzzleFailsNoImage() {
        // TODO
    }
}
