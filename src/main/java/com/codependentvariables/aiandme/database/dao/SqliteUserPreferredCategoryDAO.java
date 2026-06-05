package com.codependentvariables.aiandme.database.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.IUserPreferredCategoryDAO;

import java.util.List;

/**
 * SQLite implementation of the user preferred category data access object.
 * Provides CRUD operations for user preferred categories.
 */
public class SqliteUserPreferredCategoryDAO extends BaseSqliteDAO implements IUserPreferredCategoryDAO, IDatabaseEntity {
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

    private static final IRowMapper<UserPreferredCategory> USER_PREFERRED_CATEGORY_MAPPER = (resultSet) -> new UserPreferredCategory(resultSet.getInt("userId"), resultSet.getInt("categoryId"));

    public void add(UserPreferredCategory userPreferredCategory) {
        final String query = "INSERT INTO user_preferred_categories (user_id, category_id) VALUES (?, ?)";

        executeSql(query, statement -> {
            statement.setInt(1, userPreferredCategory.getUserId());
            statement.setInt(2, userPreferredCategory.getCategoryId());
        });
    }

    public void delete(UserPreferredCategory userPreferredCategory) {
        final String query = "DELETE FROM user_preferred_categories WHERE user_id = ? AND category_id = ?";

        executeSql(query, statement -> {
            statement.setInt(1, userPreferredCategory.getUserId());
            statement.setInt(2, userPreferredCategory.getCategoryId());
        });
    }

    public List<UserPreferredCategory> getByUserId(int userId) {
        final String query = "SELECT * FROM user_preferred_categories WHERE user_id = ?";

        return executeQuery(query, statement -> statement.setInt(1, userId), USER_PREFERRED_CATEGORY_MAPPER);
    }

    public void deleteByUserId(int userId) {
        final String query = "DELETE FROM user_preferred_categories WHERE user_id = ?";

        executeSql(query, statement -> statement.setInt(1, userId));
    }
}
