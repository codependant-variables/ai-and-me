package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class SqliteUserDAO extends BaseSqliteDAO implements IUserDAO, IDatabaseEntity {
    private static final String schemaQuery = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY,
                name VARCHAR NOT NULL,
                email VARCHAR UNIQUE NOT NULL,
                password VARCHAR NOT NULL,
                salt CHAR(24) NOT NULL,
                totp_secret CHAR(30),
                is_dark_mode BIT NOT NULL DEFAULT FALSE,
                is_vertical BIT NOT NULL DEFAULT FALSE,
                last_login_at TIMESTAMP NOT NULL
            );
        """;

    private static final String seedDataQuery = """ 
            INSERT INTO users (name, email, password, salt, last_login_at) VALUES ('Amy Adams', 'amy.adams@mydomain.gov', 'Zl3QG3XY5/Gsus8Ec4WTi6jMcM7EkrCGCqBMgwwYUzg=', 'sKH9XkLaT2i1XR687zjlHQ==', '1778475998777');
            INSERT INTO users (name, email, password, salt, last_login_at) VALUES ('Bob Builder', 'bobthebulider23@swagmail.net', 'Qf15LlrXz/ghNuMGZG3heBeqH3xeuzITnsRhHTDxzR4=', '0akeeTvljQojvcWqb4cg/Q==', '1778475998777');
        """;

    public String getSchemaQuery() {
        return schemaQuery;
    }

    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    private static final IRowMapper<User> USER_MAPPER = (resultSet) -> {
        User user = new User(
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("salt"),
                resultSet.getString("totp_secret"),
                resultSet.getBoolean("is_dark_mode"),
                resultSet.getBoolean("is_vertical"),
                resultSet.getTimestamp("last_login_at")
        );
        user.setId(resultSet.getInt("id"));
        return user;
    };

    @Override
    public void add(User user) {
        final String query = "INSERT INTO users (name, email, password, salt, totp_secret, is_dark_mode, is_vertical, last_login_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int id = executeSqlWithGeneratedKeys(query,
                statement -> {
                    statement.setString(1, user.getName());
                    statement.setString(2, user.getEmail());
                    statement.setString(3, user.getPassword());
                    statement.setString(4, user.getSalt());
                    statement.setString(5, user.getTotpSecret());
                    statement.setBoolean(6, user.getIsDarkMode());
                    statement.setBoolean(7, user.getIsVertical());
                    statement.setTimestamp(8, user.getLastLoginAt());
                });
        user.setId(id);
    }

    @Override
    public void update(User user) {
        final String query = "UPDATE users SET name = ?, email = ?, password = ?, salt = ?, totp_secret = ?, is_dark_mode = ?, is_vertical = ? WHERE id = ?";

        executeSql(query,
                statement -> {
                    statement.setString(1, user.getName());
                    statement.setString(2, user.getEmail());
                    statement.setString(3, user.getPassword());
                    statement.setString(4, user.getSalt());
                    statement.setString(5, user.getTotpSecret());
                    statement.setBoolean(6, user.getIsDarkMode());
                    statement.setBoolean(7, user.getIsVertical());
                    statement.setInt(8, user.getId());
                });
    }

    @Override
    public void updateLastLoginAt(User user) {
        Timestamp lastLoginAt = Timestamp.valueOf(LocalDateTime.now());
        String query = "UPDATE users SET last_login_at = ? WHERE id = ?";

        executeSql(query, statement -> {
            statement.setTimestamp(1, lastLoginAt);
            statement.setInt(2, user.getId());
        });
        user.setLastLoginAt(lastLoginAt);
        System.out.println("Updated last login");
    }

    @Override
    public void delete(User user) {
        final String query = "DELETE FROM users WHERE id = ?";

        executeSql(query, statement -> statement.setInt(1, user.getId()));
    }

    @Override
    public List<User> getAll() {
        final String query = "SELECT * FROM users";

        return executeQuery(query, USER_MAPPER);
    }

    @Override
    public User get(int id) {
        final String query = "SELECT * FROM users WHERE id = ? LIMIT 1";

        List<User> users = executeQuery(query, statement -> statement.setInt(1, id), USER_MAPPER);
        return firstOrNull(users);
    }

    @Override
    public User getByEmail(String email) {
        final String query = "SELECT * FROM users WHERE email = ? LIMIT 1";

        List<User> users = executeQuery(query, statement -> statement.setString(1, email), USER_MAPPER);
        return firstOrNull(users);
    }
}