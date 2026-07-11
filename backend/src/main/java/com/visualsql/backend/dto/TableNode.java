package com.visualsql.backend.dto;

import java.util.List;

public record TableNode(
        String id,
        String tableName,
        List<ColumnDto> columns
) {
}
