package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;

import java.util.List;

/**
 * /**
 *  * Implementation of the IQuizAttemptQuestionDAO interface.
 *  Handles all database operations related to QuizAttemptQuestion entities.
 */
public class SqliteQuizAttemptQuestionDAO extends BaseSqliteDAO implements IQuizAttemptQuestionDAO, IDatabaseEntity {
    /**
     * Creates the QuizAttemptQuestion table if it does not exist.
     */
    private static final String schemaQuery = """
            CREATE TABLE IF NOT EXISTS quiz_attempt_questions (
            id INTEGER PRIMARY KEY,
            text VARCHAR NOT NULL,
            image BYTE[],
            FOREIGN KEY (quiz_attempt_id) REFERENCES quiz_attempts(id)
            ON DELETE CASCADE
            );
    """;

    /**
     * Seed data inserted when database is initialised.
     */
    private static final String seedDataQuery = """
            INSERT INTO quiz_attempt_questions (text, quiz_attempt_id)
            VALUES ('What is the product of 2+2?', NULL);
    """;

    @Override
    public String getSchemaQuery() {
        return schemaQuery;
    }

    @Override
    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    /**
     * Maps a ResultSet row to a QuizAttemptQuestion object.
     */
    public static final IRowMapper<QuizAttemptQuestion> QUIZ_ATTEMPT_QUESTION_MAPPER = (resultSet) -> {
        QuizAttemptQuestion quizAttemptQuestion = new QuizAttemptQuestion(
                resultSet.getInt("quiz_attempt_id"),
                resultSet.getString("text"),
                resultSet.getBytes("image")
        );
        quizAttemptQuestion.setId(resultSet.getInt("id"));
        return quizAttemptQuestion;
    };

    /**
     * Inserts a new QuizAttemptQuestion into the database.
     * The generated ID is assigned back to the object.
     */
    @Override
    public void add(QuizAttemptQuestion quizAttemptQuestion) {
        final String query = "INSERT INTO quiz_attempt_questions (text, image) VALUES (?, ?, ?)";

        int id = executeSqlWithGeneratedKeys(query, statement -> {
            statement.setInt(1, quizAttemptQuestion.getQuizAttemptId());
            statement.setString(2, quizAttemptQuestion.getText());
            statement.setBytes(3, quizAttemptQuestion.getImage());
        });

        quizAttemptQuestion.setId(id);
    }

    /**
     * Updates an existing QuizAttemptQuestion in the database.
     */
    @Override
    public void update(QuizAttemptQuestion quizAttemptQuestion) {
        final String query = "UPDATE quiz_attempt_questions SET text = ?, image = ? WHERE id = ?";

        executeSql(query, statement -> {
            statement.setString(1, quizAttemptQuestion.getText());
            statement.setBytes(2, quizAttemptQuestion.getImage());
        });
    }

    /**
     * Deletes a QuizAttempt from the database by its ID.
     */
    @Override
    public void delete(QuizAttemptQuestion quizAttemptQuestion) {
        final String query = "DELETE FROM quiz_attempt_questions WHERE id = ?";

        executeSql(query, statement -> statement.setInt(1, quizAttemptQuestion.getId()));
    }

    /**
     * Retrieves all questions for a given quiz attempt.
     */
    @Override
    public List<QuizAttemptQuestion> getByQuizAttemptId(int quizAttemptId) {
        final String query = "SELECT * FROM quiz_attempt_questions WHERE quiz_attempt_id = ?";

        return executeQuery(query, statement -> statement.setInt(1, quizAttemptId), QUIZ_ATTEMPT_QUESTION_MAPPER);
    }
}
