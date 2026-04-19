package com.codependentvariables.aiandme.model;

import java.sql.*;
import java.util.List;

public class SqliteQuizTemplateDAO extends BaseSqliteDAO implements IQuizTemplateDAO, IDatabaseEntity {
    private static final String schemaQuery = """
        CREATE TABLE IF NOT EXISTS quiz_templates (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name VARCHAR NOT NULL,
            category_id INTEGER NOT NULL,
            status VARCHAR NOT NULL DEFAULT 'draft',
            FOREIGN KEY (category_id) REFERENCES categories(id)
        );
    """;

    private static final String seedDataQuery = "";

    @Override
    public String getSchemaQuery() {
        return schemaQuery;
    }

    @Override
    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    private static final IRowMapper<QuizTemplate> QUIZ_TEMPLATE_MAPPER = (resultSet) -> {
        QuizTemplate template = new QuizTemplate(
                resultSet.getString("name"),
                resultSet.getInt("category_id"),
                resultSet.getString("status")
        );
        template.setId(resultSet.getInt("id"));
        return template;
    };

    @Override
    public void add(QuizTemplate quizTemplate) {
        final String query = "INSERT INTO quiz_templates(name, category_id, status) VALUES(?, ?, ?)";

        var id = executeSqlWithGeneratedKeys(query, statement -> {
            statement.setString(1, quizTemplate.getName());
            statement.setInt(2, quizTemplate.getCategoryId());
            statement.setString(3, quizTemplate.getStatus());
        });

        quizTemplate.setId(id);
    }

    @Override
    public void update(QuizTemplate quizTemplate) {
        final String query = "UPDATE quiz_templates SET name = ?, category_id = ?, status = ? WHERE id = ?";

        executeSql(query, statement -> {
            statement.setString(1, quizTemplate.getName());
            statement.setInt(2, quizTemplate.getCategoryId());
            statement.setString(3, quizTemplate.getStatus());
            statement.setInt(4, quizTemplate.getId());
        });
    }

    @Override
    public void delete(QuizTemplate quizTemplate) {
        final String query = "DELETE FROM quiz_templates WHERE id = ?";

        executeSql(query, statement -> statement.setInt(1, quizTemplate.getId()));
    }

    @Override
    public QuizTemplate get(int id) {
        final String query = "SELECT * FROM quiz_templates WHERE id = ? LIMIT 1";

        List<QuizTemplate> templates = executeQuery(query, statement -> statement.setInt(1, id), QUIZ_TEMPLATE_MAPPER);
        return firstOrNull(templates);
    }

    @Override
    public List<QuizTemplate> getAll() {
        final String query = "SELECT * FROM quiz_templates";

        return executeQuery(query, QUIZ_TEMPLATE_MAPPER);
    }

    @Override
    public List<QuizTemplate> getByCategoryId(int categoryId) {
        final String query = "SELECT * FROM quiz_templates WHERE category_id = ?";

        return executeQuery(query, statement -> statement.setInt(1, categoryId), QUIZ_TEMPLATE_MAPPER);
    }
}

