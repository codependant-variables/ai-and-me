package com.codependentvariables.aiandme.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A function that converts a ResultSet into T
 */
@FunctionalInterface
public interface IRowMapper<T> {
    T map(ResultSet rs) throws SQLException;
}