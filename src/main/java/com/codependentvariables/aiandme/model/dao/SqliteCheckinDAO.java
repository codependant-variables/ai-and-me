package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;

import java.util.List;
import java.time.LocalDateTime;

public class SqliteCheckinDAO extends BaseSqliteDAO implements ICheckinDAO, IDatabaseEntity {
    private static final String schemaQuery = """
            CREATE TABLE IF NOT EXISTS checkins (
                id integer PRIMARY KEY,
                user_id integer NOT NULL REFERENCES users(id),
                ai_use decimal NOT NULL,
                ai_happiness decimal NOT NULL,
                ai_dependency decimal NOT NULL,
                completed_at datetime NOT NULL
            );
        """;

    private static final String seedDataQuery = """
            INSERT INTO checkins (user_id, ai_use, ai_happiness, ai_dependency, completed_at) VALUES (1, 5.0, 5.0, 5.0, '2026-04-14 17:28:00');
            INSERT INTO checkins (user_id, ai_use, ai_happiness, ai_dependency, completed_at) VALUES (2, 7.5, 2.5, 7.5, '2026-04-16 14:16:00');
        """;

    public String getSchemaQuery() {
        return schemaQuery;
    }

    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    private static final IRowMapper<Checkin> CHECKIN_MAPPER = (resultSet) -> {
        Checkin checkin = new Checkin(
                resultSet.getInt("user_id"),
                resultSet.getFloat("ai_use"),
                resultSet.getFloat("ai_happiness"),
                resultSet.getFloat("ai_dependence"),
                resultSet.getObject("completed_at", LocalDateTime.class)
        );
        checkin.setId(resultSet.getInt("id"));
        return checkin;
    };

    public void add(Checkin checkin) {
        final String query = "INSERT INTO checkins (user_id, ai_use, ai_happiness, ai_dependency, completed_at) VALUES (?, ?, ?, ?, ?)";

        int id = executeSqlWithGeneratedKeys(query,
                statement -> {
                    statement.setInt(1, checkin.getUserId());
                    statement.setFloat(2, checkin.getAiUse());
                    statement.setFloat(3, checkin.getAiHappiness());
                    statement.setFloat(4, checkin.getAiDependence());
                    statement.setObject(5, checkin.getCompletedAt());
                });

        checkin.setId(id);
    }

    public void delete(Checkin checkin) {
        final String query = "DELETE FROM checkins WHERE id = ?";

        executeSql(query, statement -> statement.setInt(1, checkin.getId()));
    }

    public List<Checkin> getAll() {
        final String query = "SELECT * FROM checkins";

        return executeQuery(query, CHECKIN_MAPPER);
    }

    public Checkin get(int id) {
        final String query = "SELECT * FROM checkins id = ? LIMIT 1";

        List<Checkin> checkins = executeQuery(query, statement -> statement.setInt(1, id), CHECKIN_MAPPER);
        return firstOrNull(checkins);
    }

    @Override
    public List<Checkin> getByUserId(int userId) {
        final String query = "SELECT * FROM checkins WHERE user_id = ?";

        return executeQuery(query, statement -> statement.setInt(1,userId), CHECKIN_MAPPER);
    }
}
