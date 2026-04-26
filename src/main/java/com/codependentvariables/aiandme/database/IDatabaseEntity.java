package com.codependentvariables.aiandme.database;

/**
 * For defining a class will be a database entity with a schema and seeding.
 */
public interface IDatabaseEntity {
    String getSchemaQuery();
    String getSeedDataQuery();
}
