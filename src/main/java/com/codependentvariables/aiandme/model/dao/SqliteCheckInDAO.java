package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;

import java.util.List;
import java.time.LocalDateTime;

public class SqliteCheckInDAO extends BaseSqliteDAO implements ICheckInDAO, IDatabaseEntity {
    private static final String schemaQuery = """
            CREATE TABLE IF NOT EXISTS check_ins (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                ai_use REAL NOT NULL,
                ai_happiness REAL NOT NULL,
                ai_dependence REAL NOT NULL,
                comment VARCHAR(255),
                completed_at TEXT NOT NULL
            );
        """;

    private static final String seedDataQuery = """
            INSERT INTO check_ins (user_id, ai_use, ai_happiness, ai_dependence, comment, completed_at) VALUES (1, 5.0, 5.0, 5.0, "Feel good about AI usage", '2026-04-14T17:28:00');
            INSERT INTO check_ins (user_id, ai_use, ai_happiness, ai_dependence, comment, completed_at) VALUES (2, 7.5, 2.5, 7.5, "Could rely on ai less", '2026-04-16T14:16:00');
        """;

    public String getSchemaQuery() {
        return schemaQuery;
    }

    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    private static final IRowMapper<CheckIn> CHECKIN_MAPPER = (resultSet) -> {
        CheckIn checkin = new CheckIn(
                resultSet.getFloat("ai_use"),
                resultSet.getFloat("ai_happiness"),
                resultSet.getFloat("ai_dependence"),
                resultSet.getString("comment"),
                LocalDateTime.parse(resultSet.getString("completed_at"))
        );

        checkin.setId(resultSet.getInt("id"));
        checkin.setUserId(resultSet.getInt("user_id"));

        return checkin;
    };

    public void add(CheckIn checkIn) {
        final String query = """
                INSERT INTO check_ins (
                    user_id,
                    ai_use,
                    ai_happiness,
                    ai_dependence,
                    comment,
                    completed_at
                ) VALUES (?, ?, ?, ?, ?, ?)
                """;

        int id = executeSqlWithGeneratedKeys(query, statement -> {
            statement.setInt(1, checkIn.getUserId());
            statement.setFloat(2, checkIn.getAiUse());
            statement.setFloat(3, checkIn.getAiHappiness());
            statement.setFloat(4, checkIn.getAiDependence());
            statement.setString(5, checkIn.getComment());
            statement.setString(6, checkIn.getCompletedAt().toString());
        });

        checkIn.setId(id);
    }

    public void delete(CheckIn checkIn) {
        final String query = "DELETE FROM check_ins WHERE id = ?";

        executeSql(query, statement -> statement.setInt(1, checkIn.getId()));
    }

    public List<CheckIn> getAll() {
        final String query = "SELECT * FROM check_ins ORDER BY completed_at DESC";
        return executeQuery(query, CHECKIN_MAPPER);
    }

    public CheckIn get(int id) {
        final String query = "SELECT * FROM check_ins WHERE id = ? LIMIT 1";

        List<CheckIn> checkIns = executeQuery(query, statement -> statement.setInt(1, id), CHECKIN_MAPPER);
        return firstOrNull(checkIns);
    }

    @Override
    public List<CheckIn> getAllByUserId(int userId) {
        final String query = "SELECT * FROM check_ins WHERE user_id = ? ORDER BY completed_at DESC";

        return executeQuery(query, statement -> statement.setInt(1, userId), CHECKIN_MAPPER);
    }
}