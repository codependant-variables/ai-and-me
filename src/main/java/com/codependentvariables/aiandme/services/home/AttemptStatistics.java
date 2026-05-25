package com.codependentvariables.aiandme.services.home;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the split of quiz attempts between regular quizzes and puzzles,
 * including raw counts and percentages rounded to the nearest whole number
 * so that quizPercent + puzzlePercent == 100 (when total > 0).
 */
public class AttemptStatistics {

    private final int quizCount;
    private int puzzleCount = 0;
    private final int quizPercent;
    private final int puzzlePercent;
    private List<CategoryStat> categoryStats = new ArrayList<>();

    /**
     * Creates a new AttemptStatistics object.
     *
     * @param quizCount total quiz attempts
     * @param puzzleCount total puzzle attempts
     */
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

    /**
     * Gets the total number of quiz attempts.
     *
     * @return quiz count
     */
    public int getQuizCount()     { return quizCount; }

    /**
     * Gets the total number of puzzle attempts.
     *
     * @return puzzle count
     */
    public int getPuzzleCount()   { return puzzleCount; }

    /**
     * Gets the percentage of quiz attempts.
     *
     * @return quiz percentage
     */
    public int getQuizPercent()   { return quizPercent; }

    /**
     * Gets the percentage of puzzle attempts.
     *
     * @return puzzle percentage
     */
    public int getPuzzlePercent() { return puzzlePercent; }

    /**
     * Gets the combined total number of attempts.
     *
     * @return total attempts
     */
    public int getTotal()         { return quizCount + puzzleCount; }

    /**
     * Gets all category-based statistics.
     * Used for generating dynamic pie chart slices.
     *
     * @return list of category statistics
     */
    public List<CategoryStat> getCategoryStats() {
        return categoryStats;
    }

    /**
     * Sets the category statistics list.
     *
     * @param categoryStats category statistics data
     */
    public void setCategoryStats(List<CategoryStat> categoryStats) {
        this.categoryStats = categoryStats;
    }

    /**
     * Returns a readable summary of the attempt statistics.
     *
     * @return formatted statistics string
     */
    @Override
    public String toString() {
        return "AttemptTypeSplit{quizzes=" + quizCount + " (" + quizPercent + "%), " +
               "puzzles=" + puzzleCount + " (" + puzzlePercent + "%)}";
    }
}

