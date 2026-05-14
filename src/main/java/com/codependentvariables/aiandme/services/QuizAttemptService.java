package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/****
 * Service layer for handling quiz attempts.
 * Coordinates between the UI and the data access layer to save and retrieve quiz attempt data.
 */
public class QuizAttemptService {

    private static QuizAttemptService instance;

    private final IQuizAttemptDAO attemptDAO;
    private final IQuizAttemptQuestionDAO attemptQuestionDAO;
    private final IQuizAttemptAnswerDAO attemptAnswerDAO;

    private QuizAttemptService() {
        this(new SqliteQuizAttemptDAO(),
             new SqliteQuizAttemptQuestionDAO(),
             new SqliteQuizAttemptAnswerDAO());
    }

    // Not public but tests in this package can use it
    QuizAttemptService(IQuizAttemptDAO attemptDAO,
                       IQuizAttemptQuestionDAO attemptQuestionDAO,
                       IQuizAttemptAnswerDAO attemptAnswerDAO) {
        this.attemptDAO         = attemptDAO;
        this.attemptQuestionDAO = attemptQuestionDAO;
        this.attemptAnswerDAO   = attemptAnswerDAO;
    }

    public static QuizAttemptService getInstance() {
        if (instance == null) {
            instance = new QuizAttemptService();
        }
        return instance;
    }

    public int saveAttempt(QuizTemplate template,
                           int userId,
                           List<QuizTemplateAnswer> selectedAnswers) {

        // 1 — Create the top-level attempt record
        QuizAttempt attempt = new QuizAttempt(
                userId,
                template.getName(),
                Timestamp.from(Instant.now())
        );
        attemptDAO.add(attempt); // sets attempt.id

        int correct = 0;

        // 2 — Snapshot each question and the user's selected answer
        for (QuizTemplateQuestion tq : template.getQuestions()) {
            QuizAttemptQuestion aq = new QuizAttemptQuestion(
                    attempt.getId(),
                    tq.getText(),
                    null
            );
            attemptQuestionDAO.add(aq); // sets aq.id

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
                attemptAnswerDAO.add(aa);
                if (isCorrect) correct++;
            }
        }

        return correct;
    }

    /** Returns all attempts ever recorded (useful for history views). */
    public List<QuizAttempt> getAllAttempts() {
        return attemptDAO.getAll();
    }

    /** Returns all attempts for a specific user. */
    public List<QuizAttempt> getAttemptsByUser(int userId) {
        return attemptDAO.getByUserId(userId);
    }

    /**
     * Returns the split of quiz vs puzzle attempts for a user.
     * Puzzle counting is not yet implemented, therefore all attempts are treated as quizzes.
     *
     * @param userId the user to query
     * @return an {@link AttemptStatistics} with quiz count and percentages ready for charts in controller's
     */
    public AttemptStatistics getAttemptCountByUser(int userId) {
        int quizCount = attemptDAO.getByUserId(userId).size();
        return new AttemptStatistics(quizCount, 0);
    }
}
