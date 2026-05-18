package com.codependentvariables.aiandme.model;

/**
 * A puzzle-specific question that extends QuizTemplateQuestion.
 * Overrides isValid() to also require an image — puzzles are visual by nature.
 */
public class PuzzleTemplateQuestion extends QuizTemplateQuestion {

    public PuzzleTemplateQuestion(int quizTemplateId, String text) {
        super(quizTemplateId, text);
    }

    public PuzzleTemplateQuestion(int quizTemplateId, String text, byte[] image) {
        super(quizTemplateId, text, image);
    }

    /**
     * A puzzle question is valid only when:
     * <ul>
     *   <li>All base conditions pass (non-blank text, ≥2 answers, ≥1 correct)</li>
     *   <li>An image has been provided (not null and not empty)</li>
     * </ul>
     */
    @Override
    public boolean isValid() {
        if (!super.isValid()) return false;
        return getImage() != null && getImage().length > 0;
    }

    /** Returns true if this puzzle question has an image attached. */
    public boolean hasImage() {
        return getImage() != null && getImage().length > 0;
    }
}

