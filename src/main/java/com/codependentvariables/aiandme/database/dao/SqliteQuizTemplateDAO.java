package com.codependentvariables.aiandme.database.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.IQuizTemplateDAO;

import java.util.List;

public class SqliteQuizTemplateDAO extends BaseSqliteDAO implements IQuizTemplateDAO, IDatabaseEntity {
    private static final String schemaQuery = """
                CREATE TABLE IF NOT EXISTS quiz_templates (
                    id INTEGER PRIMARY KEY,
                    name VARCHAR NOT NULL,
                    category_id INTEGER NOT NULL REFERENCES categories(id),
                    user_id INTEGER REFERENCES users(id),
                    ispuzzle INTEGER NOT NULL,
                    status VARCHAR NOT NULL DEFAULT 'draft'
                );
            """;

    private static final String seedDataQuery = """
                INSERT INTO quiz_templates (name, category_id, user_id, ispuzzle, status) VALUES ('Mental Maths', 1, 1, 0, 'published');
                INSERT INTO quiz_templates (name, category_id, user_id, ispuzzle, status) VALUES ('Find The Pattern', 2, 1, 0, 'published');
                INSERT INTO quiz_templates (name, category_id, user_id, ispuzzle, status) VALUES ('Groupings', 2, 1, 0, 'published');
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
     * Seeds the example "Pattern Puzzle" template with PQ1.png on each question.
     * Seeding is handled by SqliteQuizTemplateQuestionDAO.seedBinaryData() because
     * the question/answer data belongs there. Nothing to do here.
     */
    @Override
    public void seedBinaryData() {}

    private static final IRowMapper<QuizTemplate> QUIZ_TEMPLATE_MAPPER = (resultSet) -> {
        QuizTemplate template = new QuizTemplate(
                resultSet.getString("name"),
                resultSet.getInt("category_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("ispuzzle") == 1,
                resultSet.getString("status")
        );
        template.setId(resultSet.getInt("id"));
        return template;
    };

    @Override
    public void add(QuizTemplate quizTemplate) {
        final String query = "INSERT INTO quiz_templates(name, category_id, user_id, ispuzzle, status) VALUES(?, ?, ?, ?, ?)";

        var id = executeSqlWithGeneratedKeys(query, statement -> {
            statement.setString(1, quizTemplate.getName());
            statement.setInt(2, quizTemplate.getCategoryId());
            if (quizTemplate.getUserId() == 0) {
                statement.setNull(3, java.sql.Types.INTEGER);
            } else {
                statement.setInt(3, quizTemplate.getUserId());
            }
            statement.setInt(4, quizTemplate.isPuzzle() ? 1 : 0);
            statement.setString(5, quizTemplate.getStatus());
        });

        quizTemplate.setId(id);
    }

    @Override
    public void update(QuizTemplate quizTemplate) {
        final String query = "UPDATE quiz_templates SET name = ?, category_id = ?, user_id = ?, ispuzzle = ?, status = ? WHERE id = ?";

        executeSql(query, statement -> {
            statement.setString(1, quizTemplate.getName());
            statement.setInt(2, quizTemplate.getCategoryId());
            if (quizTemplate.getUserId() == 0) {
                statement.setNull(3, java.sql.Types.INTEGER);
            } else {
                statement.setInt(3, quizTemplate.getUserId());
            }
            statement.setInt(4, quizTemplate.isPuzzle() ? 1 : 0);
            statement.setString(5, quizTemplate.getStatus());
            statement.setInt(6, quizTemplate.getId());
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

    @Override
    public List<QuizTemplate> getByUserId(int userId) {
        final String query = "SELECT * FROM quiz_templates WHERE user_id = ?";

        return executeQuery(query, statement -> statement.setInt(1, userId), QUIZ_TEMPLATE_MAPPER);
    }
}

