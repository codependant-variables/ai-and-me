package com.codependentvariables.aiandme.model;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

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
