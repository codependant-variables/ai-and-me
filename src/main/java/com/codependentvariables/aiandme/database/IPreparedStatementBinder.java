package com.codependentvariables.aiandme.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A function with context of a PreparedStatement
 */
@FunctionalInterface
public interface IPreparedStatementBinder {
    void accept(PreparedStatement preparedStatement) throws SQLException;
}
