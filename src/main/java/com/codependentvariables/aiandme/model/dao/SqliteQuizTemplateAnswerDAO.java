package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.IDatabaseEntity;
import com.codependentvariables.aiandme.database.IRowMapper;
import com.codependentvariables.aiandme.model.QuizTemplateAnswer;

import java.util.List;

public class SqliteQuizTemplateAnswerDAO extends BaseSqliteDAO implements IQuizTemplateAnswerDAO, IDatabaseEntity {
    private static final String schemaQuery = """
        CREATE TABLE IF NOT EXISTS quiz_template_answers (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            quiz_template_question_id INTEGER NOT NULL,
            text VARCHAR NOT NULL,
            image BLOB,
            is_correct INTEGER NOT NULL DEFAULT 0,
            FOREIGN KEY (quiz_template_question_id) REFERENCES quiz_template_questions(id)
        );
    """;

    @Override
    public String getSchemaQuery() { return schemaQuery; }

    @Override
    public String getSeedDataQuery() { return ""; }

    private static final IRowMapper<QuizTemplateAnswer> MAPPER = (rs) -> {
        QuizTemplateAnswer answer = new QuizTemplateAnswer(
                rs.getInt("quiz_template_question_id"),
                rs.getString("text"),
                rs.getBytes("image"),
                rs.getInt("is_correct") == 1
        );
        answer.setId(rs.getInt("id"));
        return answer;
    };

    @Override
    public void addAnswer(QuizTemplateAnswer answer) {
        final String query = "INSERT INTO quiz_template_answers(quiz_template_question_id, text, image, is_correct) VALUES(?, ?, ?, ?)";
        var id = executeSqlWithGeneratedKeys(query, stmt -> {
            stmt.setInt(1, answer.getQuizTemplateQuestionId());
            stmt.setString(2, answer.getText());
            stmt.setBytes(3, answer.getImage());
            stmt.setInt(4, answer.isCorrect() ? 1 : 0);
        });
        answer.setId(id);
    }

    @Override
    public void updateAnswer(QuizTemplateAnswer answer) {
        final String query = "UPDATE quiz_template_answers SET quiz_template_question_id = ?, text = ?, image = ?, is_correct = ? WHERE id = ?";
        executeSql(query, stmt -> {
            stmt.setInt(1, answer.getQuizTemplateQuestionId());
            stmt.setString(2, answer.getText());
            stmt.setBytes(3, answer.getImage());
            stmt.setInt(4, answer.isCorrect() ? 1 : 0);
            stmt.setInt(5, answer.getId());
        });
    }

    @Override
    public void deleteAnswer(QuizTemplateAnswer answer) {
        final String query = "DELETE FROM quiz_template_answers WHERE id = ?";
        executeSql(query, stmt -> stmt.setInt(1, answer.getId()));
    }

    @Override
    public QuizTemplateAnswer get(int id) {
        final String query = "SELECT * FROM quiz_template_answers WHERE id = ? LIMIT 1";
        List<QuizTemplateAnswer> results = executeQuery(query, stmt -> stmt.setInt(1, id), MAPPER);
        return firstOrNull(results);
    }

    @Override
    public List<QuizTemplateAnswer> getAnswersByQuestion(int questionId) {
        final String query = "SELECT * FROM quiz_template_answers WHERE quiz_template_question_id = ?";
        return executeQuery(query, stmt -> stmt.setInt(1, questionId), MAPPER);
    }
}

