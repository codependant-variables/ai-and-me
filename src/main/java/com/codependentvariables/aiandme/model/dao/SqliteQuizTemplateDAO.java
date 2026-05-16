package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
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
     * Kept here rather than in SqliteConnection because this data belongs to quiz templates.
     */
    @Override
    public void seedBinaryData() {
        try {
            URL resource = getClass().getResource(
                    "/com/codependentvariables/aiandme/Images/PQ1.png");
            if (resource == null) {
                System.err.println("SqliteQuizTemplateDAO.seedBinaryData: PQ1.png not found — skipping.");
                return;
            }
            byte[] pq1 = Files.readAllBytes(Paths.get(resource.toURI()));

            SqliteQuizTemplateQuestionDAO questionDAO = new SqliteQuizTemplateQuestionDAO();
            SqliteQuizTemplateAnswerDAO   answerDAO   = new SqliteQuizTemplateAnswerDAO();

            QuizTemplate template = new QuizTemplate("Pattern Puzzle", 2, 1, true, "published");
            add(template);

            addPuzzleQuestion(questionDAO, answerDAO, template.getId(),
                    "What shape completes the pattern?", pq1,
                    new String[]{"Circle", "Triangle", "Square", "Pentagon"}, "Circle");
            addPuzzleQuestion(questionDAO, answerDAO, template.getId(),
                    "Which colour follows the sequence?", pq1,
                    new String[]{"Red", "Blue", "Green", "Yellow"}, "Blue");
            addPuzzleQuestion(questionDAO, answerDAO, template.getId(),
                    "How many shapes are in the next group?", pq1,
                    new String[]{"3", "4", "5", "6"}, "4");
            addPuzzleQuestion(questionDAO, answerDAO, template.getId(),
                    "What is the missing symbol?", pq1,
                    new String[]{"Star", "Arrow", "Cross", "Diamond"}, "Star");
            addPuzzleQuestion(questionDAO, answerDAO, template.getId(),
                    "Which row continues the pattern?", pq1,
                    new String[]{"Row A", "Row B", "Row C", "Row D"}, "Row C");
            addPuzzleQuestion(questionDAO, answerDAO, template.getId(),
                    "What rotation comes next?", pq1,
                    new String[]{"90°", "180°", "270°", "0°"}, "90°");
            addPuzzleQuestion(questionDAO, answerDAO, template.getId(),
                    "Which tile fits the grid?", pq1,
                    new String[]{"Tile 1", "Tile 2", "Tile 3", "Tile 4"}, "Tile 2");
            addPuzzleQuestion(questionDAO, answerDAO, template.getId(),
                    "How does the pattern scale?", pq1,
                    new String[]{"x2", "x3", "x4", "x5"}, "x2");
            addPuzzleQuestion(questionDAO, answerDAO, template.getId(),
                    "Which element is the odd one out?", pq1,
                    new String[]{"Element A", "Element B", "Element C", "Element D"}, "Element C");
            addPuzzleQuestion(questionDAO, answerDAO, template.getId(),
                    "What comes at position 7 in the sequence?", pq1,
                    new String[]{"13", "15", "17", "19"}, "13");

        } catch (IOException | URISyntaxException e) {
            System.err.println("SqliteQuizTemplateDAO.seedBinaryData: image load failed — " + e.getMessage());
        }
    }

    private void addPuzzleQuestion(SqliteQuizTemplateQuestionDAO questionDAO,
                                   SqliteQuizTemplateAnswerDAO answerDAO,
                                   int templateId, String text, byte[] image,
                                   String[] options, String correct) {
        QuizTemplateQuestion question = new QuizTemplateQuestion(templateId, text, image);
        questionDAO.addQuestion(question);
        for (String option : options) {
            answerDAO.addAnswer(new QuizTemplateAnswer(question.getId(), option, option.equals(correct)));
        }
    }

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
            statement.setInt(3, quizTemplate.getUserId());
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
            statement.setInt(3, quizTemplate.getUserId());
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

