package com.codependentvariables.aiandme.model;

/**
 * Stores statistics for a specific quiz or puzzle category.
 * Used to generate pie chart data on the dashboard.
 */
public class CategoryStat {

    private String categoryName;
    private int count; // Number of attempts in  category
    private boolean puzzle;

    /**
     * Creates a new category statistic object.
     *
     * @param categoryName Name of the category
     * @param count Number of attempts in this category
     * @param puzzle Whether the category is a puzzle or quiz
     */
    public CategoryStat(String categoryName, int count, boolean puzzle) {
        this.categoryName = categoryName;
        this.count = count;
        this.puzzle = puzzle;
    }

    /**
     * Gets the category name.
     *
     * @return category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Gets the number of attempts in this category.
     *
     * @return attempt count
     */
    public int getCount() {
        return count;
    }

    /**
     * Checks whether this category belongs to a puzzle.
     *
     * @return true if puzzle, false if quiz
     */
    public boolean isPuzzle() {
        return puzzle;
    }

    /**
     * Updates the number of attempts in this category.
     *
     * @param count updated attempt count
     */
    public void setCount(int count) {
        this.count = count;
    }
}