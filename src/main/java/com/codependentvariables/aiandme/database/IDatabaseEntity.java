package com.codependentvariables.aiandme.database;

/**
 * For defining a class will be a database entity with a schema and seeding.
 */
public interface IDatabaseEntity {
    String getSchemaQuery();
    String getSeedDataQuery();
/**
 * Optional hook for seeding data that cannot be expressed as SQL strings
 * (i.e. BLOB images) This method is called after getSeedDataQuery() on a fresh database.
 * Default implementation overrides only when needed, but default does nothing.
 */
    default void seedBinaryData() {}
}
