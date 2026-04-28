package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;

import java.util.List;
import java.time.LocalDateTime;

public class SqliteCheckinDAO extends BaseSqliteDAO implements ICheckinDAO, IDatabaseEntity {
    private static final String schemaQuery = """
            CREATE TABLE IF NOT EXISTS checkins (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                ai_use REAL NOT NULL,
                ai_happiness REAL NOT NULL,
                ai_dependence REAL NOT NULL,
                comment VARCHAR(255),
                completed_at TEXT NOT NULL,
                FOREIGN KEY (user_id) REFERENCES users(id)
            );
        """;

    private static final String seedDataQuery = """
            INSERT INTO checkins (user_id, ai_use, ai_happiness, ai_dependence, comment, completed_at) VALUES (1, 5.0, 5.0, 5.0, "Feel good about AI usage", '2026-04-14T17:28:00');
            INSERT INTO checkins (user_id, ai_use, ai_happiness, ai_dependence, comment, completed_at) VALUES (2, 7.5, 2.5, 7.5, "Could rely on ai less", '2026-04-16T14:16:00');
        """;

    public String getSchemaQuery() {
        return schemaQuery;
    }

    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    private static final IRowMapper<Checkin> CHECKIN_MAPPER = (resultSet) -> {
        Checkin checkin = new Checkin(
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

    public void add(Checkin checkin) {
        final String query = """
                INSERT INTO checkins (
                    user_id,
                    ai_use,
                    ai_happiness,
                    ai_dependence,
                    comment,
                    completed_at
                ) VALUES (?, ?, ?, ?, ?, ?)
                """;

        int id = executeSqlWithGeneratedKeys(query, statement -> {
            statement.setInt(1, checkin.getUserId());
            statement.setFloat(2, checkin.getAiUse());
            statement.setFloat(3, checkin.getAiHappiness());
            statement.setFloat(4, checkin.getAiDependence());
            statement.setString(5, checkin.getComment());
            statement.setString(6, checkin.getCompletedAt().toString());
        });

        checkin.setId(id);
    }

    public void delete(Checkin checkin) {
        final String query = "DELETE FROM checkins WHERE id = ?";

        executeSql(query, statement -> statement.setInt(1, checkin.getId()));
    }

    public List<Checkin> getAll() {
        final String query = "SELECT * FROM checkins ORDER BY completed_at DESC";
        return executeQuery(query, CHECKIN_MAPPER);
    }

    public Checkin get(int id) {
        final String query = "SELECT * FROM checkins WHERE id = ? LIMIT 1";

        List<Checkin> checkins = executeQuery(query, statement -> statement.setInt(1, id), CHECKIN_MAPPER);
        return firstOrNull(checkins);
    }

    @Override
    public List<Checkin> getAllByUserId(int userId) {
        final String query = "SELECT * FROM checkins WHERE user_id = ? ORDER BY completed_at DESC";

        return executeQuery(query, statement -> statement.setInt(1, userId), CHECKIN_MAPPER);
    }
}