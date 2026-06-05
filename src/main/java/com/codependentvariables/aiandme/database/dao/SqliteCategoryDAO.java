package com.codependentvariables.aiandme.database.dao;

import com.codependentvariables.aiandme.database.*;
import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.ICategoryDAO;

import java.util.List;

/**
 * SQLite implementation of ICategoryDAO
 * Provides CRUD operations for Categories
 */
public class SqliteCategoryDAO extends BaseSqliteDAO implements ICategoryDAO, IDatabaseEntity {
    /**
     * SQL statement used to create the categories table.
     */
    private static final String schemaQuery = """
            CREATE TABLE IF NOT EXISTS categories (
                id INTEGER PRIMARY KEY,
                name VARCHAR NOT NULL UNIQUE
            );
        """;

    /**
     * Initial seed data inserted into the categories table.
     */
    private static final String seedDataQuery = """
            INSERT INTO categories (name) VALUES ('Mental Maths');
            INSERT INTO categories (name) VALUES ('Pattern Recognition');
        """;

    /**
     * Returns the SQL statement used to create the categories table.
     *
     * @return create table SQL statement
     */
    public String getSchemaQuery() {
        return schemaQuery;
    }

    /**
     * Returns the SQL statement used to seed initial category data.
     *
     * @return seed data SQL statement
     */
    public String getSeedDataQuery() {
        return seedDataQuery;
    }

    /**
     * Maps a database result set row to a Category object.
     */
    private static final IRowMapper<Category> CATEGORY_MAPPER = (resultSet) -> {
        Category category = new Category(resultSet.getString("name"));
        category.setId(resultSet.getInt("id"));
        return category;
    };

    /**
     * Adds a new category to the database.
     *
     * @param category the category to add
     */
    @Override
    public void add(Category category) {
        final String query = "INSERT INTO categories(name) VALUES(?)";

        int id = executeSqlWithGeneratedKeys(query, statement -> statement.setString(1, category.getName()));
        category.setId(id);
    }

    /**
     * Updates an existing category in the database.
     *
     * @param category the category to update
     */
    @Override
    public void update(Category category) {
        final String query = "UPDATE categories SET name = ? WHERE id = ?";

        executeSql(query,statement -> {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
        });
    }

    /**
     * Deletes a category from the database.
     *
     * @param category the category to delete
     */
    @Override
    public void delete(Category category) {
        final String query = "DELETE FROM categories WHERE id = ?";

        executeSql(query, statement -> statement.setInt(1, category.getId()));
    }


    /**
     * Retrieves all categories from the database.
     *
     * @return list of all categories
     */
    @Override
    public List<Category> getAll() {
        final String query = "SELECT * FROM categories";

        return executeQuery(query, CATEGORY_MAPPER);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the category ID
     * @return the matching category, or null if not found
     */
    @Override
    public Category get(int id) {
        final String query = "SELECT * FROM categories WHERE id = ? LIMIT 1";

        List<Category> categories = executeQuery(query, statement -> statement.setInt(1, id), CATEGORY_MAPPER);
        return firstOrNull(categories);
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name the category name
     * @return the matching category, or null if not found
     */
    @Override
    public Category getByName(String name) {
        final String query = "SELECT * FROM categories WHERE name = ? LIMIT 1";

        List<Category> categories = executeQuery(query, statement -> statement.setString(1, name), CATEGORY_MAPPER);
        return firstOrNull(categories);
    }
}
