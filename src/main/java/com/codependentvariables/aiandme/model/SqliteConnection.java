package com.codependentvariables.aiandme.model;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection {
    private static final Connection Instance;
    private static final String DB_PATH = "app.db";

    static {
        try {
            boolean isNewDatabase = false;
            File dbFile = new File(DB_PATH);
            if (!dbFile.exists()) {
                isNewDatabase = true;
            }

            String url = "jdbc:sqlite:" + DB_PATH;
            Instance = DriverManager.getConnection(url);

            if (isNewDatabase)
                setupSchema();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to SQLite " + DB_PATH, e);
        }
    }

    private static void setupSchema() throws SQLException {
        IDatabaseEntity[] entities = {
            new SqliteUserDAO()
        };

        try {
            Instance.setAutoCommit(false);

            Statement statement = Instance.createStatement();
            for (IDatabaseEntity entity : entities) {
                String schemaQuery = entity.getSchemaQuery();
                statement.executeUpdate(schemaQuery);
                String seedDataQuery = entity.getSeedDataQuery();
                statement.executeUpdate(seedDataQuery);
            }

            Instance.commit();
            Instance.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Transaction failed; rolling back schema changes.");
            Instance.rollback();
            throw e;
        }
    }

    public static Connection getInstance() {
        return Instance;
    }
}