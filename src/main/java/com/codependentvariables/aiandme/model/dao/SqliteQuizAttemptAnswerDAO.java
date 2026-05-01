package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.IDatabaseEntity;
import com.codependentvariables.aiandme.database.IRowMapper;
import com.codependentvariables.aiandme.model.QuizAttemptAnswer;

import java.util.List;

public class SqliteQuizAttemptAnswerDAO extends BaseSqliteDAO implements IQuizAttemptAnswerDAO, IDatabaseEntity {
    private static final String schemaQuery = """
            CREATE TABLE IF NOT EXISTS quiz_attempt_answers (
                id INTEGER PRIMARY KEY,
                quiz_attempt_question_id INTEGER NOT NULL REFERENCES quiz_attempt_questions(id) ON DELETE CASCADE,
                text VARCHAR NOT NULL,
                image BYTE[],
                is_correct BIT NOT NULL
            );
            """;

    private static final String seedDataQuery = "";

    public String getSchemaQuery() {
        return schemaQuery;
    }

    public String getSeedDataQuery() {
        return seedDataQuery;
    }

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

    public void add(QuizAttemptAnswer quizAttemptAnswer) {
        final String query = "INSERT INTO quiz_attempt_answers (quiz_attempt_question_id, text, is_correct) VALUES (?, ?, ?)";

        int id = executeSqlWithGeneratedKeys(query, statement -> {
            statement.setInt(1, quizAttemptAnswer.getQuizAttemptQuestionId());
            statement.setString(2, quizAttemptAnswer.getText());
            statement.setBoolean(3, quizAttemptAnswer.getIsCorrect());
        });
        quizAttemptAnswer.setId(id);
    }

    public void delete(QuizAttemptAnswer quizAttemptAnswer) {
        final String query = "DELETE FROM quiz_attempt_answers WHERE id = ?";
        executeSql(query, statement -> statement.setInt(1, quizAttemptAnswer.getId()));
    }

    public List<QuizAttemptAnswer> get(int id) {
        final String query = "SELECT * FROM quiz_attempt_answers WHERE id = ?";
        return executeQuery(query, statement -> statement.setInt(1, id), QUIZ_ATTEMPT_ANSWER_MAPPER);
    }

    public List<QuizAttemptAnswer> getByQuizAttemptQuestionId(int quizAttemptQuestionId) {
        final String query = "SELECT * FROM quiz_attempt_answers WHERE quiz_attempt_question_id = ?";

        return executeQuery(query, statement -> statement.setInt(1, quizAttemptQuestionId), QUIZ_ATTEMPT_ANSWER_MAPPER);
    }
}
