package com.codependentvariables.aiandme.model;

import java.sql.*;
java.util.List;

// Comments for Emma, meant to be removed before PR
// Developed based on SQliteContactDAO
public class SqliteCategoryDAO implements ICategoryDAO {
    private Connection connection;

    public SqliteCategoryDAO() { // constructor?
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        // Create table if not existing
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS categories ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "categoryName VARCHAR NOT NULL,"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCategory(Category category) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO categories(categoryName) VALUES(?)");
            statement.setString(1, category.getCategoryName());
            statement.executeUpdate();

            // Set ID of new category
            // check w team about how this works bc unsure.
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCategory(Category category) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE categories SET categoryName = ? WHERE id = ?");
            statement.setString(1, category.getCategoryName());
            statement.setInt(2, category.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(Category category) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM categories WHERE id = ?");
            statement.setInt(1, category.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
