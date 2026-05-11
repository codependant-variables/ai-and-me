package com.codependentvariables.aiandme.database;

import com.codependentvariables.aiandme.model.dao.*;
import org.sqlite.SQLiteConfig;

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
            // Foreign key support not enabled by default in SQLite.
            // Need to enable via SQLiteConfig.
            // https://stackoverflow.com/questions/5890250/on-delete-cascade-in-sqlite3
            // https://stackoverflow.com/questions/9774923/how-do-you-enforce-foreign-key-constraints-in-sqlite-through-java
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(url, config.toProperties());
            if (isNewDatabase)
                setupSchema();
            else
                deleteInactiveAccounts();
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
                new SqliteCheckInDAO(),
                new SqliteQuizTemplateDAO(),
                new SqliteQuizTemplateQuestionDAO(),
                new SqliteQuizTemplateAnswerDAO(),
                new SqliteQuizAttemptDAO(),
                new SqliteQuizAttemptQuestionDAO(),
                new SqliteQuizAttemptAnswerDAO(),
                new SqlitePreferredCategoryDAO()
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

    private void deleteInactiveAccounts() {
        String query = "DELETE FROM users WHERE last_activity < (strftime('%s', 'now') * 1000 - 30 * 24 * 60 * 60 * 1000);";
        try {
            Statement deleteStatement = connection.createStatement();
            int accountsNuked = deleteStatement.executeUpdate(query);
            System.out.println("Inactive accounts deleted: " + accountsNuked);
        } catch (SQLException e) {
            System.err.println("Failed to delete inactive accounts.");
        }
    }
}