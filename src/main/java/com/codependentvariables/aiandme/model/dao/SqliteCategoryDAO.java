package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;

import java.util.List;

public class SqliteCategoryDAO extends BaseSqliteDAO implements ICategoryDAO, IDatabaseEntity {
    private static final String schemaQuery = """
            CREATE TABLE IF NOT EXISTS categories (
                id INTEGER PRIMARY KEY,
                name VARCHAR NOT NULL
            );
        """;

    private static final String seedDataQuery = """
            INSERT INTO categories (name) VALUES ('Mental Maths');
            INSERT INTO categories (name) VALUES ('Pattern Recognition');
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
    public void add(Category category) {
        final String query = "INSERT INTO categories(name) VALUES(?)";

        var id = executeSqlWithGeneratedKeys(query, statement -> statement.setString(1, category.getName()));

        category.setId(id);
    }

    @Override
    public void update(Category category) {
        final String query = "UPDATE categories SET name = ? WHERE id = ?";

        executeSql(query,
                statement -> {
                    statement.setString(1, category.getName());
                    statement.setInt(2, category.getId());
                });
    }

    @Override
    public void delete(Category category) {
        final String query = "DELETE FROM categories WHERE id = ?";

        executeSql(query, statement -> statement.setInt(1, category.getId()));
    }

    @Override
    public List<Category> getAll() {
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
