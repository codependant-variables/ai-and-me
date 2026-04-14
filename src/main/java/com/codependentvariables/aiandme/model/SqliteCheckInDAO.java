package com.codependentvariables.aiandme.model;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SqliteCheckInDAO implements ICheckInDAO, IDatabaseEntity {
    private final Connection connection;

    private static final String schemaQuery = """
            CREATE TABLE IF NOT EXISTS checkins (
                id integer PRIMARY KEY,
                userId integer NOT NULL,
                use integer NOT NULL,
                happiness NOT NULL,
                dependency NOT NULL,
                completedAt datetime NOT NULL
                CONSTRAINT fk_userId
                FOREIGN KEY (userId)
                REFERENCES users(id)
            );
            """;

    private static final String seedDataQuery = """
            INSERT INTO checkins (userId, use, happiness, dependency, completedAt) VALUES (1, 50, 50, 50, '2026-04-14 17:28:00');
            INSERT INTO checkins (userId, use, happiness, dependency, completedAt) VALUES (2, 75, 25, 75, '2026-04-14 17:29:00');
            """; // assuming the userId of seed values in table users are 1 and 2.

    public SqliteCheckInDAO() { connection = SqliteConnection.getConnection(); }

    public String getSchemaQuery() { return schemaQuery; }

    public String getSeedDataQuery() { return seedDataQuery; }

    public void addCheckIn(CheckIn checkIn) {
        try {
            String query = "INSERT INTO checkins (userId, use, happiness, dependency, completedAt) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // ensures correct format before adding date to db (hopefully)
            statement.setInt(1, checkIn.getUserId());
            statement.setInt(2, checkIn.getUse());
            statement.setInt(3, checkIn.getHappiness());
            statement.setInt(4, checkIn.getDependence());
            statement.setString(5, checkIn.getCompletedAt().format(datetimeFormat));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                checkIn.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCheckIn(CheckIn checkIn) {
        try {
            String query = "DELETE FROM checkins WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,checkIn.getId());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private CheckIn mapCheckIn(ResultSet results) throws SQLException {
        CheckIn checkIn = new CheckIn(
                results.getInt("userId"),
                results.getInt("use"),
                results.getInt("happiness"),
                results.getInt("dependence")
        );
        checkIn.setId(results.getInt("id"));
        return checkIn;
    }

    public CheckIn get(int id) {
        try {
            String query = "SELECT * FROM checkins WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return mapCheckIn(results);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CheckIn> getAllCheckIns() {
        String query = "SELECT * FROM checkins";
        List<CheckIn> checkIns = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                checkIns.add(mapCheckIn(results));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (checkIns.isEmpty()) { return null; }
        return checkIns;
    }

    @Override
    public List<CheckIn> getAllCheckInsByUserId(int userId) {
        String query = "SELECT * FROM checkins WHERE userId = ?";
        List<CheckIn> checkIns = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,userId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                checkIns.add(mapCheckIn(results));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (checkIns.isEmpty()) { return null; }
        return checkIns;
    }
}
