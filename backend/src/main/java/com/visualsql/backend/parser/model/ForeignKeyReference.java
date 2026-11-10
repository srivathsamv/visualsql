package com.visualsql.backend.parser.model;

/**
 * Record class to store a clean reference of a foreign key
 * @param sourceColumn The foreign key column of a table
 * @param referencedTable the other table that the foreign key refers to
 * @param referencedColumn the specific column in the other table that the foreign key refers to
 */
public record ForeignKeyReference(
        String sourceColumn,
        String referencedTable,
        String referencedColumn
) {
}
