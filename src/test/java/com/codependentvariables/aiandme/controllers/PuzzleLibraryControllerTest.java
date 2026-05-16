package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PuzzleLibraryControllerTest {

    private QuizTemplate puzzleTemplate;
    private QuizTemplate quizTemplate;

    @BeforeEach
    void setUp() {
        puzzleTemplate = new QuizTemplate("My Puzzle", 1, 1, true, "draft");
        quizTemplate   = new QuizTemplate("My Quiz",   1, 1, false, "draft");
    }

    @Test
    void isPuzzleHidden() {
        // Puzzle template should be flagged as a puzzle
        assertTrue(puzzleTemplate.isPuzzle());
    }

    @Test
    void isPuzzleShown() {
        // A non-puzzle template should NOT pass the puzzle library filter
        assertFalse(quizTemplate.isPuzzle());
    }

    @Test
    void isQuestionStored() {
        // A puzzle question with an image should retain that image
        byte[] img = {1, 2, 3};
        QuizTemplateQuestion q = new QuizTemplateQuestion(puzzleTemplate.getId(), "Spot the pattern", img);
        assertNotNull(q.getImage());
        assertArrayEquals(img, q.getImage());
    }

    @Test
    void puzzleFailNoImage() {
        // A puzzle question without an image should have a null image
        QuizTemplateQuestion q = new QuizTemplateQuestion(puzzleTemplate.getId(), "No image question");
        assertNull(q.getImage());
    }
}
