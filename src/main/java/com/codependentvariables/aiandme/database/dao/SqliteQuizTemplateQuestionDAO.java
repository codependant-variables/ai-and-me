package com.codependentvariables.aiandme.database.dao;

import com.codependentvariables.aiandme.database.IDatabaseEntity;
import com.codependentvariables.aiandme.database.IRowMapper;
import com.codependentvariables.aiandme.model.QuizTemplate;
import com.codependentvariables.aiandme.model.QuizTemplateAnswer;
import com.codependentvariables.aiandme.model.QuizTemplateQuestion;
import com.codependentvariables.aiandme.model.dao.IQuizTemplateQuestionDAO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SqliteQuizTemplateQuestionDAO extends BaseSqliteDAO implements IQuizTemplateQuestionDAO, IDatabaseEntity {
    private static final String schemaQuery = """
                CREATE TABLE IF NOT EXISTS quiz_template_questions (
                    id INTEGER PRIMARY KEY,
                    quiz_template_id INTEGER NOT NULL REFERENCES quiz_templates(id),
                    text VARCHAR NOT NULL,
                    image BLOB
                );
            """;

    private static final String seedDataQuery = """
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

    @Override
    public String getSchemaQuery() { return schemaQuery; }

    @Override
    public String getSeedDataQuery() {
        return seedDataQuery;
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
            byte[] imageBytes = question.getImage();
            if (imageBytes != null) {
                stmt.setBinaryStream(3, new ByteArrayInputStream(imageBytes), imageBytes.length);
            } else {
                stmt.setNull(3, java.sql.Types.BLOB);
            }
        });
        question.setId(id);
    }

    @Override
    public void updateQuestion(QuizTemplateQuestion question) {
        final String query = "UPDATE quiz_template_questions SET quiz_template_id = ?, text = ?, image = ? WHERE id = ?";
        executeSql(query, stmt -> {
            stmt.setInt(1, question.getQuizTemplateId());
            stmt.setString(2, question.getText());
            byte[] imageBytes = question.getImage();
            if (imageBytes != null) {
                stmt.setBinaryStream(3, new ByteArrayInputStream(imageBytes), imageBytes.length);
            } else {
                stmt.setNull(3, java.sql.Types.BLOB);
            }
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

    /**
     * Seeds the "Pattern Puzzle" template questions with PQ1.png images.
     * Lives here because question/answer seeding belongs with the question DAO.
     * The template row is inserted via SqliteQuizTemplateDAO so the FK is satisfied.
     */
    @Override
    public void seedBinaryData() {
        try {
            URL resource = getClass().getResource(
                    "/com/codependentvariables/aiandme/Images/PQ1.png");
            if (resource == null) {
                System.err.println("SqliteQuizTemplateQuestionDAO.seedBinaryData: PQ1.png not found — skipping.");
                return;
            }
            byte[] pq1 = Files.readAllBytes(Paths.get(resource.toURI()));

            SqliteQuizTemplateDAO templateDAO = new SqliteQuizTemplateDAO();
            SqliteQuizTemplateAnswerDAO answerDAO = new SqliteQuizTemplateAnswerDAO();

            QuizTemplate template = new QuizTemplate("Pattern Puzzle", 2, 1, true, "published");
            templateDAO.add(template);

            addPuzzleQuestion(answerDAO, template.getId(),
                    "What shape completes the pattern?", pq1,
                    new String[]{"Purple Circle", "Yellow Triangle", "Blue Square", "Red Pentagon"}, "Purple Circle");
            addPuzzleQuestion(answerDAO, template.getId(),
                    "Which colour follows the sequence?", pq1,
                    new String[]{"Red", "Blue", "Green", "Yellow"}, "Blue");
            addPuzzleQuestion(answerDAO, template.getId(),
                    "How many shapes are in the next group?", pq1,
                    new String[]{"3", "4", "5", "6"}, "4");
            addPuzzleQuestion(answerDAO, template.getId(),
                    "What is the missing symbol?", pq1,
                    new String[]{"Star", "Arrow", "Cross", "Diamond"}, "Star");
            addPuzzleQuestion(answerDAO, template.getId(),
                    "Which row continues the pattern?", pq1,
                    new String[]{"Row A", "Row B", "Row C", "Row D"}, "Row C");
            addPuzzleQuestion(answerDAO, template.getId(),
                    "What rotation comes next?", pq1,
                    new String[]{"90°", "180°", "270°", "0°"}, "90°");
            addPuzzleQuestion(answerDAO, template.getId(),
                    "Which tile fits the grid?", pq1,
                    new String[]{"Tile 1", "Tile 2", "Tile 3", "Tile 4"}, "Tile 2");
            addPuzzleQuestion(answerDAO, template.getId(),
                    "How does the pattern scale?", pq1,
                    new String[]{"x2", "x3", "x4", "x5"}, "x2");
            addPuzzleQuestion(answerDAO, template.getId(),
                    "Which element is the odd one out?", pq1,
                    new String[]{"Element A", "Element B", "Element C", "Element D"}, "Element C");
            addPuzzleQuestion(answerDAO, template.getId(),
                    "What comes at position 7 in the sequence?", pq1,
                    new String[]{"13", "15", "17", "19"}, "13");

        } catch (IOException | URISyntaxException e) {
            System.err.println("SqliteQuizTemplateQuestionDAO.seedBinaryData: image load failed — " + e.getMessage());
        }
    }

    private void addPuzzleQuestion(SqliteQuizTemplateAnswerDAO answerDAO,
                                   int templateId, String text, byte[] image,
                                   String[] options, String correct) {
        QuizTemplateQuestion question = new QuizTemplateQuestion(templateId, text, image);
        addQuestion(question);
        for (String option : options) {
            answerDAO.addAnswer(new QuizTemplateAnswer(question.getId(), option, option.equals(correct)));
        }
    }
}

