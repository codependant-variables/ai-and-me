package com.codependentvariables.aiandme.database;

import com.codependentvariables.aiandme.model.dao.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection {
    private static SqliteConnection instance;
    private final Connection connection;
    private static final String DB_PATH = "app.db";

    private SqliteConnection() {
        try {
            File dbFile = new File(DB_PATH);
            boolean isNewDatabase = !dbFile.exists();

            String url = "jdbc:sqlite:" + DB_PATH;
            connection = DriverManager.getConnection(url);

            if (isNewDatabase)
                setupSchema();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to SQLite " + DB_PATH, e);
        }
    }

    public static SqliteConnection getInstance() {
        if (instance == null) {
            instance = new SqliteConnection();
        }
        return instance;
    }

    /**
     * Creates schema and seeds data for any class that implements IDatabaseEntity
     */
    private void setupSchema() throws SQLException {
        // Add instances of each IDatabaseEntity class to this array
        IDatabaseEntity[] entities = {
                new SqliteUserDAO(),
                new SqliteCategoryDAO(),
                new SqliteCheckinDAO(),
                new SqliteQuizTemplateDAO(),
                new SqliteQuizTemplateQuestionDAO(),
                new SqliteQuizTemplateAnswerDAO()
        };

        try {
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            for (IDatabaseEntity entity : entities) {
                String schemaQuery = entity.getSchemaQuery();
                statement.executeUpdate(schemaQuery);
                String seedDataQuery = entity.getSeedDataQuery();
                statement.executeUpdate(seedDataQuery);
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Transaction failed; rolling back schema changes.");
            connection.rollback();
            throw e;
        }
    }

    /**
     * Gets shared Sqlite connection.
     */
    public static Connection getConnection() {
        return getInstance().connection;
    }
}