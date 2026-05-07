package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.IDatabaseEntity;
import com.codependentvariables.aiandme.database.IRowMapper;
import com.codependentvariables.aiandme.model.QuizTemplateAnswer;
import java.util.List;

public class SqliteQuizTemplateAnswerDAO extends BaseSqliteDAO implements IQuizTemplateAnswerDAO, IDatabaseEntity {
    private static final String schemaQuery = """
                CREATE TABLE IF NOT EXISTS quiz_template_answers (
                    id INTEGER PRIMARY KEY,
                    quiz_template_question_id INTEGER NOT NULL REFERENCES quiz_template_questions(id),
                    text VARCHAR NOT NULL,
                    image BLOB,
                    is_correct BIT NOT NULL DEFAULT FALSE
                );
            """;

    private static final String seedDataQuery = """
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (1, '16', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (1, '15', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (1, '20', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (1, '30', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (2, '7', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (2, '1', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (2, '9', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (2, '3', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (3, '10', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (3, '12', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (3, '3', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (3, '9', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (4, '10', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (4, '6', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (4, '7', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (4, '8', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (5, '84', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (5, '17', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (5, '16', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (5, '12', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (6, '42', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (6, '1', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (6, '14', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (6, '49', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (7, '50', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (7, '27.5', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (7, '2', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (7, '55', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (8, '44', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (8, '66', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (8, '62', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (8, '14', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (9, '6', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (9, '7', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (9, '46', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (9, '8', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (10, '28', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (10, '43', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (10, '86', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (10, '34', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (11, '4', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (11, '15', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (11, '12', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (11, '18', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (12, '-1', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (12, '2', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (12, '-4', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (12, '0', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (13, '88.5', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (13, '89', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (13, '88', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (13, '90.5', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (14, '18', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (14, '14', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (14, '12', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (14, '32', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (15, '31', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (15, '41', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (15, '30', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (15, '49', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (16, '3', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (16, '4', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (16, '2', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (16, '6', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (17, '27', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (17, '29', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (17, '25', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (17, '30', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (18, '-1', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (18, '-4', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (18, '-2', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (18, '2', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (19, '29', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (19, '22', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (19, '49', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (19, '25', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (20, '13', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (20, '15', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (20, '7', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (20, '13.5', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (21, 'Banana', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (21, 'Carrot', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (21, 'Orange', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (21, 'Lemon', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (22, 'Jowl', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (22, 'Scale', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (22, 'Plate', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (22, 'Flank', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (23, 'Spain', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (23, 'Peru', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (23, 'Bulgaria', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (23, 'Iceland', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (24, 'Bream', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (24, 'Hourglass', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (24, 'Puffer', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (24, 'Mullet', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (25, 'Tor', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (25, 'Aquifer', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (25, 'Mountain', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (25, 'Butte', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (26, 'Willow', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (26, 'Bamboo', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (26, 'Birch', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (26, 'Maple', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (27, 'Gamma', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (27, 'Remote', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (27, 'Microwave', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (27, 'Infrared', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (28, 'Skin', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (28, 'Muscles', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (28, 'Heart', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (28, 'Stomach', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (29, 'Skateboard', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (29, 'Plane', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (29, 'Car', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (29, 'Bus', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (30, 'Lead', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (30, 'Iron', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (30, 'Aluminum', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (30, 'Gold', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (31, 'Sorrow', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (31, 'Resignation', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (31, 'Pained', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (31, 'Anguish', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (32, 'To escape his brother', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (32, 'To get a view of the city', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (32, 'To avoid the sounds of the fires below', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (32, 'To get away from Uldal', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (33, 'The consequences of loyalty', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (33, 'The need for heroism', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (33, 'The consequences of Revenge', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (33, 'The contrast of Justice and Revenge', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (34, 'Avoiding responsibility for his actions', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (34, 'A temporary pause in his rampage', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (34, 'The weight of emotional exhaustion', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (34, 'Physical exhaustion from his journey', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (35, 'It reveals his inability to understand loss', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (35, 'It shows he regrets underestimating the consequences', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (35, 'It portrays him as logical and strategic', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (35, 'It emphasizes his moral detachment and ruthlessness', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (36, 'They were easy to decode', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (36, 'They required special devices to function', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (36, 'They could not be intercepted', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (36, 'They were outdated and useless', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (37, 'To block wireless signals', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (37, 'To cut through and destroy the cable', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (37, 'To intercept and access wired transmissions', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (37, 'To block wired signals', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (38, 'He is distracted and not focused', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (38, 'He is mocking Brehm', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (38, 'He is trying to hide nervousness', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (38, 'He views his work as playful and habitual', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (39, 'Wireless signals are highly secure', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (39, 'Intercepting wireless signals is not commonplace', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (39, 'Respect for technological expertise', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (39, 'Wireless communication can be intercepted easily', 1);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (40, 'The importance of enjoying your work', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (40, 'Nature''s ability to adapt to a technology centric world', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (40, 'The influence authority figures have on an individual', 0);
                INSERT INTO quiz_template_answers (quiz_template_question_id, text, is_correct) VALUES (40, 'The normalisation of surveillance technology', 1);
            """;

    @Override
    public String getSchemaQuery() {
        return schemaQuery;
    }

    @Override
    public String getSeedDataQuery() {
        return seedDataQuery;
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

