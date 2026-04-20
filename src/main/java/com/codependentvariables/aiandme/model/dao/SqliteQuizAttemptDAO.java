package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;

import java.sql.*;
import java.util.List;

/**
 * SQLite implementation of the IQuizAttemptDAO interface.
 * Handles all database operations related to QuizAttempt entities,
 * including schema creation, seeding, and CRUD operations.
 */
public class SqliteQuizAttemptDAO extends BaseSqliteDAO implements IQuizAttemptDAO, IDatabaseEntity {
    /**
     * SQL statement to create the quizAttempts table if it does not exist.
     * Includes a foreign key reference to the users table.
     */
    private static final String schemaQuery = """
        CREATE TABLE IF NOT EXISTS quiz_attempts (
            id INTEGER PRIMARY KEY,
            user_id INTEGER NOT NULL,
            name VARCHAR NOT NULL,
            completed_at TIMESTAMP NOT NULL,
            FOREIGN KEY (user_id) REFERENCES users(id)
        );
    """;

    /**
     * Seed data inserted when the database is initialised.
     */
    private static final String seedDataQuery = """
        INSERT INTO quiz_attempts (user_id, name, completed_at)
        VALUES (1, 'Emma''s Quiz Attempt', '2026-04-17 00:00:00');
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
     * Maps a ResultSet row to a QuizAttempt object.
     */
    public static final IRowMapper<QuizAttempt> QUIZ_ATTEMPT_MAPPER = (resultSet) -> {
        QuizAttempt quizAttempt = new QuizAttempt(
                resultSet.getInt("user_id"),
                resultSet.getString("name"),
                resultSet.getTimestamp("completed_at")
        );
        quizAttempt.setId(resultSet.getInt("id"));
        return quizAttempt;
    };

    /**
     * Inserts a new QuizAttempt into the database.
     * The generated ID is assigned back to the object.
     */
    @Override
    public void add(QuizAttempt quizAttempt) {
        final String query = "INSERT INTO quiz_attempts (user_id, name, completed_at) VALUES (?, ?, ?)";

        int id = executeSqlWithGeneratedKeys(query, statement -> {
            statement.setInt(1, quizAttempt.getUserId());
            statement.setString(2, quizAttempt.getName());
            statement.setTimestamp(3, quizAttempt.getCompletedAt());
        });

        quizAttempt.setId(id);
    }

    /**
     * Updates an existing QuizAttempt in the database.
     */
    @Override
    public void update(QuizAttempt quizAttempt) {
        final String query = "UPDATE quiz_attempts SET user_id = ?, name = ?, completed_at = ? WHERE id = ?";

        executeSql(query, statement -> {
            statement.setInt(1, quizAttempt.getUserId());
            statement.setString(2, quizAttempt.getName());
            statement.setTimestamp(3, quizAttempt.getCompletedAt());
            statement.setInt(4, quizAttempt.getId());
        });
    }

    /**
     * Deletes a QuizAttempt from the database by its ID.
     */
    @Override
    public void delete(QuizAttempt quizAttempt) {
        final String query = "DELETE FROM quiz_attempts WHERE id = ?";

        executeSql(query, statement -> statement.setInt(1, quizAttempt.getId()));
    }

    /**
     * Retrieves all QuizAttempts from the database.
     *
     * @return list of all quiz attempts
     */
    @Override
    public List<QuizAttempt> getAll() {
        final String query = "SELECT * FROM quiz_attempts";

        return executeQuery(query, QUIZ_ATTEMPT_MAPPER);
    }

    /**
     * Retrieves a QuizAttempt by its ID.
     *
     * @param id the quiz attempt ID
     * @return the matching QuizAttempt or null if not found
     */
    @Override
    public QuizAttempt get(int id) {
        final String query = "SELECT * FROM quiz_attempts WHERE id = ? LIMIT 1";

        List<QuizAttempt> quizAttempts = executeQuery(query, preparedStatement -> preparedStatement.setInt(1, id), QUIZ_ATTEMPT_MAPPER);
        return firstOrNull(quizAttempts);
    }

    /**
     * Retrieves all QuizAttempts for a specific user.
     * @param userId the user ID
     * @return list of quiz attempts belonging to the user
     */
    @Override
    public List<QuizAttempt> getByUserId(int userId) {
        final String query = "SELECT * FROM quiz_attempts WHERE user_id = ?";

        return executeQuery(query, preparedStatement -> preparedStatement.setInt(1, userId), QUIZ_ATTEMPT_MAPPER);
    }

    /**
     * Retrieves the most recent QuizAttempt for a given user.
     * @param userId the user ID
     * @return the latest quiz attempt or null if none exist
     */
    public QuizAttempt getLatestByUserId(int userId) {
        final String query = "SELECT * FROM quiz_attempts WHERE user_id = ? ORDER BY completed_at DESC LIMIT 1";

        List<QuizAttempt> attempts = executeQuery(query, preparedStatement -> preparedStatement.setInt(1, userId), QUIZ_ATTEMPT_MAPPER);
        return firstOrNull(attempts);
    }
}