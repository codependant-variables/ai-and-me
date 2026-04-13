package com.codependentvariables.aiandme.model;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

// TODO:
// - Refactor db ops to use try-with-resources so Statement and ResultSet are automatically closed
// - Replace printStackTrace() with a proper logging framework for better error handling in production

// Implementation for handling category objects with SQLite
public class SqliteCategoryDAO implements ICategoryDAO {
    private Connection connection;

    // Constructor: get db connection and ensure table exists
    public SqliteCategoryDAO() {
        connection = SqliteConnection.getConnection();
        createTable();
    }

    private void createTable() {
        // Create table if not existing
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS categories ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Inserts a new category into db
    @Override
    public void addCategory(Category category) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO categories(name) VALUES(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, category.getName());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Updates an existing category's name by ID
    @Override
    public void updateCategory(Category category) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE categories SET name = ? WHERE id = ?");
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deletes a category from the db by ID
    @Override
    public void deleteCategory(Category category) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM categories WHERE id = ?");
            statement.setInt(1, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves single category by ID
    @Override
    public Category getCategory(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM categories WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                String name = resultSet.getString("name");
                Category category = new Category(name);
                category.setId(id);
                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieves all categories from db
    @Override
    public List<Category> getAllCategories() {
        List<Category> categories =new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM categories";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Category category = new Category(name);
                category.setId(id);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
