package com.codependentvariables.aiandme.model;

import java.util.List;

public class SqliteUserDAO extends BaseSqliteDAO implements IUserDAO, IDatabaseEntity {
    private static final String schemaQuery = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY,
                name VARCHAR NOT NULL,
                email VARCHAR UNIQUE NOT NULL,
                password VARCHAR NOT NULL,
                salt CHAR(24) NOT NULL
            );
        """;

    private static final String seedDataQuery = """
            INSERT INTO users (name, email, password, salt) VALUES ('Amy Adams', 'amy.adams@mydomain.gov', 'Zl3QG3XY5/Gsus8Ec4WTi6jMcM7EkrCGCqBMgwwYUzg=', 'sKH9XkLaT2i1XR687zjlHQ==');
            INSERT INTO users (name, email, password, salt) VALUES ('Bob Builder', 'bobthebulider23@swagmail.net', 'Qf15LlrXz/ghNuMGZG3heBeqH3xeuzITnsRhHTDxzR4=', '0akeeTvljQojvcWqb4cg/Q==');
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
                resultSet.getString("salt")
        );
        user.setId(resultSet.getInt("id"));
        return user;
    };

    @Override
    public void add(User user) {
        final String query = "INSERT INTO users (name, email, password, salt) VALUES (?, ?, ?, ?)";

        int id = executeSqlWithGeneratedKeys(query,
                statement -> {
                    statement.setString(1, user.getName());
                    statement.setString(2, user.getEmail());
                    statement.setString(3, user.getPassword());
                    statement.setString(4, user.getSalt());
                });

        user.setId(id);
    }

    @Override
    public void update(User user) {
        final String query = "UPDATE users SET name = ?, email = ?, password = ?, salt = ? WHERE id = ?";

        executeSql(query,
                statement -> {
                    statement.setString(1, user.getName());
                    statement.setString(2, user.getEmail());
                    statement.setString(3, user.getPassword());
                    statement.setString(4, user.getSalt());
                    statement.setInt(5, user.getId());
                });
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