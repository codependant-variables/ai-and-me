package com.codependentvariables.aiandme.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDAO implements IUserDAO, IDatabaseEntity {
    private final Connection connection;

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

    private static final String EncryptionKey = "";

    public SqliteUserDAO() {
        connection = SqliteConnection.getInstance();
    }

    public String getSchemaQuery() {
        return schemaQuery;
    }

    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    @Override
    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, email, password, salt) VALUES (?, ?, ?, ?)");
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getSalt());
            statement.executeUpdate();
            // Set the id of the new user
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET name = ?, email = ?, password = ?, salt = ? WHERE id = ?");
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getSalt());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        User user = new User(
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("salt")
        );
        user.setId(resultSet.getInt("id"));
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        final String query = "SELECT * FROM users";

        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User get(int id) {
        try {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapUser(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        try {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapUser(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}