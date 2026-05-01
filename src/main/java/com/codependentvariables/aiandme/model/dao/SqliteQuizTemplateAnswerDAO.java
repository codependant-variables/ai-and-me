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
    public String getSeedDataQuery() {
        return """
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (1, '20', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (1, '30', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (1, '16', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (1, '15', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (2, '7', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (2, '1', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (2, '3', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (2, '9', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (3, '9', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (3, '10', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (3, '3', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (3, '12', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (4, '6', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (4, '8', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (4, '10', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (4, '7', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (5, '17', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (5, '16', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (5, '84', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (5, '12', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (6, '49', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (6, '1', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (6, '42', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (6, '14', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (7, '50', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (7, '27.5', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (7, '55', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (7, '2', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (8, '62', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (8, '66', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (8, '14', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (8, '44', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (9, '6', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (9, '7', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (9, '46', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (9, '8', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (10, '34', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (10, '28', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (10, '43', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (10, '86', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (11, '15', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (11, '12', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (11, '18', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (11, '4', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (12, '0', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (12, '-1', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (12, '2', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (12, '-4', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (13, '89', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (13, '88', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (13, '88.5', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (13, '90.5', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (14, '12', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (14, '32', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (14, '18', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (14, '14', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (15, '31', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (15, '30', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (15, '49', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (15, '41', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (16, '2', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (16, '3', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (16, '4', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (16, '6', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (17, '29', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (17, '25', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (17, '27', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (17, '30', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (18, '-1', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (18, '2', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (18, '-2', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (18, '-4', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (19, '25', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (19, '49', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (19, '22', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (19, '29', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (20, '15', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (20, '13', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (20, '13.5', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (20, '7', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (21, 'Carrot', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (21, 'Lemon', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (21, 'Orange', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (21, 'Banana', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (22, 'Scale', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (22, 'Plate', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (22, 'Flank', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (22, 'Jowl', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (23, 'Peru', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (23, 'Iceland', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (23, 'Spain', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (23, 'Bulgaria', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (24, 'Hourglass', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (24, 'Bream', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (24, 'Mullet', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (24, 'Puffer', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (25, 'Amazon', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (25, 'Indian', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (25, 'Pacific', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (25, 'Atlantic', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (26, 'Aquifer', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (26, 'Butte', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (26, 'Tor', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (26, 'Mountain', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (27, 'Bamboo', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (27, 'Maple', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (27, 'Birch', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (27, 'Willow', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (28, 'Frog', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (28, 'Chameleon', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (28, 'Turtle', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (28, 'Crocodile', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (29, 'Remote', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (29, 'Microwave', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (29, 'Infrared', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (29, 'Gamma', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (30, 'Muscles', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (30, 'Stomach', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (30, 'Heart', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (30, 'Skin', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (31, 'Plane', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (31, 'Bus', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (31, 'Car', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (31, 'Skateboard', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (32, 'Iron', 1);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (32, 'Gold', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (32, 'Lead', 0);
            INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (32, 'Aluminum', 0);
        """;
    }

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

