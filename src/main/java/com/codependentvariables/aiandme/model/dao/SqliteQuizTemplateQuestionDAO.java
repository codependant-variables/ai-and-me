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
    public String getSeedDataQuery() {
        return """
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (1, 'Solve the Equation; 2 + 3 * 6 =');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (1, 'Solve the Equation; 4 + 5 - 4 / 2 =');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (1, 'Solve the Equation; 3 * 4 - 6 / 2 =');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (1, 'Solve the Equation; 4 - ( 2 * 4 ) / 2 + 6 =');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (1, 'Solve the Equation; ( 81 / 9 ) + 2 * 4 =');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (1, 'Solve the Equation; 14 / 2 * ( 5 + 2 ) =');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (1, 'Solve the Equation; 50 / 4 * 2 + (17 + 8) =');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (1, 'Solve the Equation; 6 * (2 + 3^2) - 4 =');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (1, 'Solve the Equation; 42 / ( ( 141 / ( 11 * 4 + 3) ) + 4 ) =');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (1, 'Solve the Equation; 8 * 6 + 12 - ( 4^2 + (3^4 / 40.5 ) * 5 ) =');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (2, 'Identify the missing value; 3, 7, 11, ?, 19, 23');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (2, 'Identify the missing value; 1, ?, -2, -6, -14, -30');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (2, 'Identify the missing value; 60, 74.5, ?, 103.5, 118');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (2, 'Identify the missing value; 4, ?, 36, 108, 324');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (2, 'Identify the missing value; 5, 7, 12, 19, ?, 50, 81');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (2, 'Identify the missing value; 2, ?, 4, 6, 8, 12, 18, 26, 38');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (2, 'Identify the missing value; 11, 13, 17, 19, 23, ?, 31');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (2, 'Identify the missing value; 2, ?, -2, 2, -4, -8, 32, -256');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (2, 'Identify the missing value; 1, 4, 9, 16, ?, 32');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (2, 'Identify the missing value; 0, 15, ?, 7.5, 5, 3,');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Apple, Orange, Carrot, Banana, Lemon');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Plate, Brisket, Flank, Jowl, Scale');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; France, Spain, Bulgaria, Peru, Iceland');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Puffer, Hourglass, Bream, Flathead, Mullet');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Pacific, Indian, Atlantic, Arctic, Amazon');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Aquifer, Mountain, Tor, Mesa, Butte');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Maple, Bamboo, Willow, Sycamore, Birch');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Frog, Turtle, Crocodile, Chameleon, Snake');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Radio, Infrared, Gamma, Microwave, Remote');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Lungs, Heart, Muscles, Skin, Stomach');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Bus, Car, Bicycle, Plane, Skateboard');
            INSERT INTO quiz_template_questions (quiz_template_id, text) VALUES (3, 'Identify the Outlier amongst these words; Gold, Iron, Copper, Aluminum, Lead');
        """;
    }

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

