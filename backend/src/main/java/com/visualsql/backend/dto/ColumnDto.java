package com.visualsql.backend.dto;

/**
 * Record to maintain data of a column in table
 * @param name name of the column
 * @param type datatype of the column
 * @param primaryKey boolean T/F if the column is the primary key of the table
 * @param foreignKey boolean T/F if the column is the foreign key of the table
 * @param referenced boolean T/F if the column is a UNIQUE column referred by another foreign key
 */
public record ColumnDto(
        String name,
        String type,
        boolean primaryKey,
        boolean foreignKey,
        boolean referenced
) {
}
