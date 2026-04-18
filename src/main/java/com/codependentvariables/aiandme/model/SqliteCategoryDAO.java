package com.codependentvariables.aiandme.model;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class SqliteCategoryDAO extends BaseDAO implements ICategoryDAO, IDatabaseEntity {
    private static final String schemaQuery = """
        CREATE TABLE IF NOT EXISTS categories (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name VARCHAR NOT NULL
        );
    """;

    private static final String seedDataQuery = """
        INSERT OR IGNORE INTO categories (id, name) VALUES (1, 'Arithmetic'), (2, 'Comprehension');
    """;

    public String getSchemaQuery() {
        return schemaQuery;
    }

    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    private static final IRowMapper<Category> CATEGORY_MAPPER = (resultSet) -> {
        Category category = new Category(resultSet.getString("name"));
        category.setId(resultSet.getInt("id"));
        return category;
    };

    @Override
    public void addCategory(Category category) {
        final String query = "INSERT INTO categories(name) VALUES(?)";

        var id = executeSqlWithGeneratedKeys(query, statement -> statement.setString(1, category.getName()));

        category.setId(id);
    }

    @Override
    public void updateCategory(Category category) {
        final String query = "UPDATE categories SET name = ? WHERE id = ?";

        executeSql(query,
                statement -> {
                    statement.setString(1, category.getName());
                    statement.setInt(2, category.getId());
                });
    }

    @Override
    public void deleteCategory(Category category) {
        final String query = "DELETE FROM categories WHERE id = ?";

        executeSql(query, statement -> statement.setInt(1, category.getId()));
    }

    @Override
    public List<Category> getAllCategories() {
        final String query = "SELECT * FROM categories";

        return executeQuery(query, CATEGORY_MAPPER);
    }

    @Override
    public Category get(int id) {
        final String query = "SELECT * FROM categories WHERE id = ? LIMIT 1";

        List<Category> categories = executeQuery(query, statement -> statement.setInt(1, id), CATEGORY_MAPPER);
        return firstOrNull(categories);
    }
}
