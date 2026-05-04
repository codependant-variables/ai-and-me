package com.codependentvariables.aiandme.model.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;

import java.util.List;

public class SqlitePreferredCategoryDAO extends BaseSqliteDAO implements IPreferredCategoryDAO, IDatabaseEntity {
    private static final String schemaQuery = """
                CREATE TABLE IF NOT EXISTS user_preferred_categories (
                    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                    category_id INTEGER NOT NULL REFERENCES categories(id),
                    PRIMARY KEY (user_id, category_id)
                );
            """;

    private static final String seedDataQuery = """
                INSERT INTO user_preferred_categories (user_id, category_id) VALUES (1, 1);
                INSERT INTO user_preferred_categories (user_id, category_id) VALUES (1, 2);
                INSERT INTO user_preferred_categories (user_id, category_id) VALUES (2, 1);
            """;

    public String getSchemaQuery() {
        return schemaQuery;
    }

    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    private static final IRowMapper<Category> PREFERRED_CATEGORY_MAPPER = (resultSet) -> {
        Category category = new Category(resultSet.getString("name"));
        category.setId(resultSet.getInt("category_id"));
        return category;
    };

    public void add(int userId, int categoryId) {
        final String query = "INSERT INTO user_preferred_categories (user_id, category_id) VALUES (?, ?)";

        executeSql(query, statement -> {
            statement.setInt(1, userId);
            statement.setInt(2, categoryId);
        });
    }

    public void delete(int userId, int categoryId) {
        final String query = "DELETE FROM user_preferred_categories WHERE user_id = ? AND category_id = ?";

        executeSql(query, statement -> {
            statement.setInt(1, userId);
            statement.setInt(2, categoryId);
        });
    }

    public void deleteAllByUserId(int userId) {
        final String query = "DELETE FROM user_preferred_categories WHERE user_id = ?";

        executeSql(query, statement -> statement.setInt(1, userId));
    }

    public List<Category> getByUserId(int userId) {
        final String query = """
                SELECT user_preferred_categories.category_id, categories.name FROM user_preferred_categories
                INNER JOIN categories ON user_preferred_categories.category_id = categories.id
                WHERE user_preferred_categories.user_id = ?
                """;

        return executeQuery(query, statement -> statement.setInt(1, userId), PREFERRED_CATEGORY_MAPPER);
    }
}
