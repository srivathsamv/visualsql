package com.visualsql.backend.dto;

import java.util.List;

/**
 * Graph objects to represent a table in the SQL code
 * @param id unique id of the table
 * @param tableName name of the table
 * @param columns list of all columns under the table
 */
public record TableNode(
        String id,
        String tableName,
        List<ColumnDto> columns
) {
}
