package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.database.dao.*;
import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;
import com.codependentvariables.aiandme.services.home.AttemptStatistics;
import com.codependentvariables.aiandme.services.home.CategoryStat;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/****
 * Service layer for handling quiz attempts.
 * Coordinates between the UI and the data access layer to save and retrieve quiz attempt data.
 */
public class QuizAttemptService {
    private static QuizAttemptService instance;

    private final IQuizAttemptDAO quizAttemptDAO;
    private final IQuizAttemptQuestionDAO quizAttemptQuestionDAO;
    private final IQuizAttemptAnswerDAO quizAttemptAnswerDAO;
    private final ICategoryDAO categoryDAO;

    private QuizAttemptService(IQuizAttemptDAO quizAttemptDAO, IQuizAttemptQuestionDAO quizAttemptQuestionDAO, IQuizAttemptAnswerDAO quizAttemptAnswerDAO, ICategoryDAO categoryDAO) {
        this.quizAttemptDAO = quizAttemptDAO;
        this.quizAttemptQuestionDAO = quizAttemptQuestionDAO;
        this.quizAttemptAnswerDAO = quizAttemptAnswerDAO;
        this.categoryDAO = categoryDAO;
    }

    public static QuizAttemptService getInstance() {
        if (instance == null) {
            instance = new QuizAttemptService(new SqliteQuizAttemptDAO(), new SqliteQuizAttemptQuestionDAO(), new SqliteQuizAttemptAnswerDAO(), new SqliteCategoryDAO());
        }
        return instance;
    }

    public static QuizAttemptService createForTest(IQuizAttemptDAO quizAttemptDAO, IQuizAttemptQuestionDAO quizAttemptQuestionDAO, IQuizAttemptAnswerDAO quizAttemptAnswerDAO, ICategoryDAO categoryDAO) {
        instance = new QuizAttemptService(quizAttemptDAO, quizAttemptQuestionDAO, quizAttemptAnswerDAO, categoryDAO);
        return instance;
    }

    public int saveAttempt(QuizTemplate template,
                           int userId,
                           List<QuizTemplateAnswer> selectedAnswers) {

        int correct = 0;

        // Get the real category from the database
        Category category = categoryDAO.get(template.getCategoryId());

        String categoryName = "General";

        if (category != null) {
            categoryName = category.getName();
        }

        QuizAttempt attempt = new QuizAttempt(
                userId,
                template.getName(),
                Timestamp.from(Instant.now()),
                0,
                categoryName,
                template.isPuzzle()
        );
        quizAttemptDAO.add(attempt); // sets attempt.id

        // Snapshot each question and the user's selected answer
        for (QuizTemplateQuestion tq : template.getQuestions()) {
            QuizAttemptQuestion aq = new QuizAttemptQuestion(
                    attempt.getId(),
                    tq.getText(),
                    null
            );
            quizAttemptQuestionDAO.add(aq); // sets aq.id

            QuizTemplateAnswer selected = null;

            for (QuizTemplateAnswer answer : selectedAnswers) {
                if (answer.getQuizTemplateQuestionId() == tq.getId()) {
                    selected = answer;
                    break;
                }
            }
            if (selected != null) {
                boolean isCorrect = selected.isCorrect();
                QuizAttemptAnswer aa = new QuizAttemptAnswer(
                        aq.getId(),
                        selected.getText(),
                        null,
                        isCorrect
                );
                quizAttemptAnswerDAO.add(aa);
                if (isCorrect) correct++;
            }
        }

        // 3 — Update the attempt record with the final correct-answer tally
        attempt.setResults(correct);
        quizAttemptDAO.update(attempt);

        return correct;
    }

    /** Returns all attempts ever recorded (useful for history views). */
    public List<QuizAttempt> getAllAttempts() {
        return quizAttemptDAO.getAll();
    }

    /** Returns all attempts for a specific user. */
    public List<QuizAttempt> getByUserId(int userId) {
        return quizAttemptDAO.getByUserId(userId);
    }

    /**
     * Returns the split of quiz vs puzzle attempts for a user.
     * Also generates category statistics used for the dashboard pie chart.
     *
     * @param userId the user to query
     * @return AttemptStatistics containing attempt counts and category data
     */
    public AttemptStatistics getAttemptCountByUser(int userId) {

        // Get all attempts made by the user
        List<QuizAttempt> attempts = quizAttemptDAO.getByUserId(userId);

        int quizCount = 0;
        int puzzleCount = 0;

        // Stores all category statistics
        List<CategoryStat> categoryStats = new ArrayList<>();

        // Loop through every attempt
        for (QuizAttempt attempt : attempts) {

            // Get category name
            String category = attempt.getCategory();

            // Prevent null category values
            if (category == null || category.isBlank()) {
                category = "General";
            }

            boolean isPuzzle = attempt.isPuzzle();

            // Count quizzes and puzzles
            if (isPuzzle) {
                puzzleCount++;
            } else {
                quizCount++;
            }

            boolean found = false;

            // Check if category already exists
            for (CategoryStat stat : categoryStats) {

                // Match both category name and type
                if (category.equals(stat.getCategoryName()) && stat.isPuzzle() == isPuzzle) {
                    // Increase count
                    stat.setCount(stat.getCount() + 1);

                    found = true;
                    break;
                }
            }

            // Create new category if it does not exist
            if (!found) {
                categoryStats.add(new CategoryStat(category, 1, isPuzzle)
                );
            }
        }

        // Create final statistics object
        AttemptStatistics stats = new AttemptStatistics(quizCount, puzzleCount);

        // Attach category data
        stats.setCategoryStats(categoryStats);

        return stats;
    }
}
