package com.codependentvariables.aiandme.model;

/**
 * Holds the split of quiz attempts between regular quizzes and puzzles,
 * including raw counts and percentages rounded to the nearest whole number
 * so that quizPercent + puzzlePercent == 100 (when total > 0).
 */
public class AttemptStatistics {

    private final int quizCount;
    private final int puzzleCount;
    private final int quizPercent;
    private final int puzzlePercent;

    public AttemptStatistics(int quizCount, int puzzleCount) {
        this.quizCount   = quizCount;
        this.puzzleCount = puzzleCount;

        int total = quizCount + puzzleCount;
        if (total == 0) {
            this.quizPercent   = 0;
            this.puzzlePercent = 0;
        } else {
            // Round puzzle share; give quiz the remainder so they always sum to 100
            int puzzleRounded  = (int) Math.round((puzzleCount * 100.0) / total);
            this.puzzlePercent = puzzleRounded;
            this.quizPercent   = 100 - puzzleRounded;
        }
    }

    public int getQuizCount()     { return quizCount; }
    public int getPuzzleCount()   { return puzzleCount; }
    public int getQuizPercent()   { return quizPercent; }
    public int getPuzzlePercent() { return puzzlePercent; }
    public int getTotal()         { return quizCount + puzzleCount; }

    @Override
    public String toString() {
        return "AttemptTypeSplit{quizzes=" + quizCount + " (" + quizPercent + "%), " +
               "puzzles=" + puzzleCount + " (" + puzzlePercent + "%)}";
    }
}

