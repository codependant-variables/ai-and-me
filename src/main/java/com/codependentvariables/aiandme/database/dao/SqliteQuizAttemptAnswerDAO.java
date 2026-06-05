package com.codependentvariables.aiandme.database.dao;

import com.codependentvariables.aiandme.database.IDatabaseEntity;
import com.codependentvariables.aiandme.database.IRowMapper;
import com.codependentvariables.aiandme.model.QuizAttemptAnswer;
import com.codependentvariables.aiandme.model.dao.IQuizAttemptAnswerDAO;

import java.util.List;

/**
 * SQLite implementation of the QuizAttemptAnswer data access object.
 * Provides methods for storing, retrieving, and deleting quiz attempt answers.
 */
public class SqliteQuizAttemptAnswerDAO extends BaseSqliteDAO implements IQuizAttemptAnswerDAO, IDatabaseEntity {
    /**
     * SQL statement used to create the quiz_attempt_answers table.
     */
    private static final String schemaQuery = """
                CREATE TABLE IF NOT EXISTS quiz_attempt_answers (
                    id INTEGER PRIMARY KEY,
                    quiz_attempt_question_id INTEGER NOT NULL REFERENCES quiz_attempt_questions(id) ON DELETE CASCADE,
                    text VARCHAR NOT NULL,
                    image BYTE[],
                    is_correct BIT NOT NULL DEFAULT FALSE
                );
            """;

    /**
     * Initial seed data inserted into the quiz_attempt_answers table.
     */
    private static final String seedDataQuery = """
                INSERT INTO quiz_attempt_answers (quiz_attempt_question_id, text) VALUES (1, '45');
                INSERT INTO quiz_attempt_answers (quiz_attempt_question_id, text) VALUES (1, '41');
                INSERT INTO quiz_attempt_answers (quiz_attempt_question_id, text) VALUES (1, '38');
                INSERT INTO quiz_attempt_answers (quiz_attempt_question_id, text, is_correct) VALUES (1, '42', TRUE);
            """;

    /**
     * Returns the SQL statement used to create the quiz_attempt_answers table.
     *
     * @return create table SQL statement
     */
    public String getSchemaQuery() {
        return schemaQuery;
    }

    /**
     * Returns the SQL statement used to seed initial quiz attempt answer data.
     *
     * @return seed data SQL statement
     */
    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    /**
     * Converts rows returned from the quiz_attempt_answers table into QuizAttemptAnswer objects.
     */
    private static final IRowMapper<QuizAttemptAnswer> QUIZ_ATTEMPT_ANSWER_MAPPER = (resultSet) -> {
        QuizAttemptAnswer quizAttemptAnswer = new QuizAttemptAnswer(
                resultSet.getInt("quiz_attempt_question_id"),
                resultSet.getString("text"),
                resultSet.getBytes("image"),
                resultSet.getBoolean("is_correct")
        );
        quizAttemptAnswer.setId(resultSet.getInt("id"));
        return quizAttemptAnswer;
    };

    /**
     * Adds a new quiz attempt answer to the database.
     *
     * @param quizAttemptAnswer the answer to add
     */
    public void add(QuizAttemptAnswer quizAttemptAnswer) {
        final String query = "INSERT INTO quiz_attempt_answers (quiz_attempt_question_id, text, is_correct) VALUES (?, ?, ?)";

        int id = executeSqlWithGeneratedKeys(query, statement -> {
            statement.setInt(1, quizAttemptAnswer.getQuizAttemptQuestionId());
            statement.setString(2, quizAttemptAnswer.getText());
            statement.setBoolean(3, quizAttemptAnswer.getIsCorrect());
        });
        quizAttemptAnswer.setId(id);
    }

    /**
     * Deletes a quiz attempt answer from the database.
     *
     * @param quizAttemptAnswer the answer to delete
     */
    public void delete(QuizAttemptAnswer quizAttemptAnswer) {
        final String query = "DELETE FROM quiz_attempt_answers WHERE id = ?";
        executeSql(query, statement -> statement.setInt(1, quizAttemptAnswer.getId()));
    }

    /**
     * Retrieves a quiz attempt answer by its identifier.
     *
     * @param id the answer identifier
     * @return the matching answer, or null if not found
     */
    public QuizAttemptAnswer get(int id) {
        final String query = "SELECT * FROM quiz_attempt_answers WHERE id = ?";
        List<QuizAttemptAnswer> answers = executeQuery(query, statement -> statement.setInt(1, id), QUIZ_ATTEMPT_ANSWER_MAPPER);
        return firstOrNull(answers);
    }

    /**
     * Retrieves all answers associated with a quiz attempt question.
     *
     * @param quizAttemptQuestionId the quiz attempt question identifier
     * @return list of answers for the specified question
     */
    public List<QuizAttemptAnswer> getByQuestionId(int quizAttemptQuestionId) {
        final String query = "SELECT * FROM quiz_attempt_answers WHERE quiz_attempt_question_id = ?";

        return executeQuery(query, statement -> statement.setInt(1, quizAttemptQuestionId), QUIZ_ATTEMPT_ANSWER_MAPPER);
    }
}
