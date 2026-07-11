package com.visualsql.backend.dto;

public record ColumnDto(
        String name,
        String type,
        boolean primaryKey,
        boolean foreignKey
) {
}
