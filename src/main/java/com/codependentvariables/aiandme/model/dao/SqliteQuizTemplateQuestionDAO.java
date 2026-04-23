package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.IDatabaseEntity;
import com.codependentvariables.aiandme.database.IRowMapper;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;

import java.util.List;

public class SqliteQuizTemplateQuestionDAO extends BaseSqliteDAO implements IQuizTemplateQuestionDAO, IDatabaseEntity {
    private static final String schemaQuery = """
        CREATE TABLE IF NOT EXISTS quiz_template_questions (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            quiz_template_id INTEGER NOT NULL,
            text VARCHAR NOT NULL,
            image BLOB,
            FOREIGN KEY (quiz_template_id) REFERENCES quiz_templates(id)
        );
    """;

    @Override
    public String getSchemaQuery() { return schemaQuery; }

    @Override
    public String getSeedDataQuery() { return ""; }

    private static final IRowMapper<QuizTemplateQuestion> MAPPER = (rs) -> {
        QuizTemplateQuestion question = new QuizTemplateQuestion(
                rs.getInt("quiz_template_id"),
                rs.getString("text"),
                rs.getBytes("image")
        );
        question.setId(rs.getInt("id"));
        return question;
    };

    @Override
    public void addQuestion(QuizTemplateQuestion question) {
        final String query = "INSERT INTO quiz_template_questions(quiz_template_id, text, image) VALUES(?, ?, ?)";
        var id = executeSqlWithGeneratedKeys(query, stmt -> {
            stmt.setInt(1, question.getQuizTemplateId());
            stmt.setString(2, question.getText());
            stmt.setBytes(3, question.getImage());
        });
        question.setId(id);
    }

    @Override
    public void updateQuestion(QuizTemplateQuestion question) {
        final String query = "UPDATE quiz_template_questions SET quiz_template_id = ?, text = ?, image = ? WHERE id = ?";
        executeSql(query, stmt -> {
            stmt.setInt(1, question.getQuizTemplateId());
            stmt.setString(2, question.getText());
            stmt.setBytes(3, question.getImage());
            stmt.setInt(4, question.getId());
        });
    }

    @Override
    public void deleteQuestion(QuizTemplateQuestion question) {
        final String query = "DELETE FROM quiz_template_questions WHERE id = ?";
        executeSql(query, stmt -> stmt.setInt(1, question.getId()));
    }

    @Override
    public QuizTemplateQuestion get(int id) {
        final String query = "SELECT * FROM quiz_template_questions WHERE id = ? LIMIT 1";
        List<QuizTemplateQuestion> results = executeQuery(query, stmt -> stmt.setInt(1, id), MAPPER);
        return firstOrNull(results);
    }

    @Override
    public List<QuizTemplateQuestion> getQuestionsByTemplate(int quizTemplateId) {
        final String query = "SELECT * FROM quiz_template_questions WHERE quiz_template_id = ?";
        return executeQuery(query, stmt -> stmt.setInt(1, quizTemplateId), MAPPER);
    }
}

