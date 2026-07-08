package com.visualsql.backend.parser.model;

public record ForeignKeyReference(
        String sourceColumn,
        String referencedTable,
        String referencedColumn
) {
}
